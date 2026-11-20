# API

API REST do AstroBit, construída com Java e Spring Boot. Todas as rotas começam com `/api` e trocam dados em **JSON**.

> ⚠️ Os exemplos de requisição e resposta de cada endpoint serão adicionados conforme forem implementados.

## Sumário

- [Visão geral](#visão-geral)
- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
  - [Saúde](#saúde)
  - [Autenticação](#autenticação-1)
  - [Usuário](#usuário)
  - [Tarefas](#tarefas)
  - [Hábitos](#hábitos)
  - [Painel Início](#painel-início)
- [Códigos de resposta](#códigos-de-resposta)
- [Tratamento de erros](#tratamento-de-erros)

## Visão geral

| Item | Valor |
| --- | --- |
| Estilo | REST (nível 2 de Richardson) |
| Formato | JSON |
| Autenticação | JWT no cabeçalho `Authorization` |
| URL local | `http://localhost:8080/api` |
| URL em produção | *a definir* |

## Autenticação

1. Crie uma conta em `POST /api/auth/register`.
2. Faça login em `POST /api/auth/login` e receba um token JWT.
3. Envie o token em todas as rotas protegidas:

```http
Authorization: Bearer <token>
```

Sem token, ou com token inválido ou expirado, a API responde `401 Unauthorized`.

Cada usuário só acessa os **próprios** dados. O usuário é identificado pelo token, nunca por um dado enviado na requisição.

## Endpoints

🔒 = exige token

### Saúde

| Método | Rota | Descrição | |
| --- | --- | --- | :---: |
| `GET` | `/api/health` | Confirma que o servidor está no ar | |

### Autenticação

| Método | Rota | Descrição | |
| --- | --- | --- | :---: |
| `POST` | `/api/auth/register` | Cria uma conta | |
| `POST` | `/api/auth/login` | Faz login e devolve o token JWT | |

### Usuário

| Método | Rota | Descrição | |
| --- | --- | --- | :---: |
| `GET` | `/api/me` | Dados do usuário logado | 🔒 |
| `PUT` | `/api/me` | Edita nome, e-mail e data de nascimento | 🔒 |

### Tarefas

| Método | Rota | Descrição | |
| --- | --- | --- | :---: |
| `GET` | `/api/tasks?status=all\|pending\|done` | Lista as tarefas | 🔒 |
| `POST` | `/api/tasks` | Cria uma tarefa | 🔒 |
| `PUT` | `/api/tasks/{id}` | Edita uma tarefa | 🔒 |
| `PATCH` | `/api/tasks/{id}` | Marca ou desmarca como concluída | 🔒 |
| `DELETE` | `/api/tasks/{id}` | Apaga uma tarefa | 🔒 |

### Hábitos

| Método | Rota | Descrição | |
| --- | --- | --- | :---: |
| `GET` | `/api/habits?week=2026-11-16` | Lista os hábitos com a semana, os dias programados e a sequência | 🔒 |
| `POST` | `/api/habits` | Cria um hábito com os dias programados | 🔒 |
| `PUT` | `/api/habits/{id}` | Edita nome, descrição e dias | 🔒 |
| `DELETE` | `/api/habits/{id}` | Apaga o hábito e o histórico | 🔒 |
| `POST` | `/api/habits/{id}/checks` | Marca um dia como cumprido | 🔒 |
| `DELETE` | `/api/habits/{id}/checks/{date}` | Desmarca um dia | 🔒 |

### Painel Início

| Método | Rota | Descrição | |
| --- | --- | --- | :---: |
| `GET` | `/api/summary/today` | Resumo de hoje: tarefas, hábitos e maior sequência | 🔒 |

## Códigos de resposta

### Sucesso

| Código | Quando |
| --- | --- |
| `200 OK` | Deu certo |
| `201 Created` | Recurso criado |
| `204 No Content` | Recurso apagado (sem corpo) |

### Erros do cliente (4xx)

| Código | Quando |
| --- | --- |
| `400 Bad Request` | Dado inválido (campo vazio, senha curta, dia não programado) |
| `401 Unauthorized` | Sem token ou token inválido |
| `403 Forbidden` | Tentando acessar dado de outro usuário |
| `404 Not Found` | Recurso não encontrado |
| `409 Conflict` | Conflito (e-mail já cadastrado, dia já marcado) |

### Erros do servidor (5xx)

| Código | Quando | Origem |
| --- | --- | --- |
| `500 Internal Server Error` | Erro inesperado no servidor | API |
| `502 Bad Gateway` | O servidor não conseguiu falar com a aplicação | Hospedagem (Render) |
| `503 Service Unavailable` | Serviço indisponível, por exemplo banco fora do ar | API |
| `504 Gateway Timeout` | A aplicação demorou demais para responder | Hospedagem (Render) |

## Tratamento de erros

- **4xx** é erro do cliente: o pedido veio errado e pode ser corrigido.
- **5xx** é erro do servidor: o pedido estava certo, mas algo falhou no back-end.
- Um tratador global de erros transforma cada falha conhecida no código 4xx adequado, com uma mensagem clara.
- Erros inesperados viram `500` com uma mensagem genérica. Detalhes internos (stack trace, SQL) nunca são enviados ao cliente: ficam apenas nos logs do servidor.
- O servidor gratuito hiberna após 15 minutos sem uso. Enquanto ele acorda, a primeira requisição pode responder `502`, `503` ou `504`, e o front-end tenta novamente automaticamente.