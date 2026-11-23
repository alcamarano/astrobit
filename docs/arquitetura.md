# Arquitetura

Como as partes do AstroBit se organizam e conversam entre si.

## Visão geral

```
Navegador → Front-end (React)
              ↓  requisições HTTP com JSON
           Back-end (Spring Boot)
              ↓  JPA
           Banco de dados (PostgreSQL)
```

| Parte | Desenvolvimento | Produção |
| --- | --- | --- |
| Front-end | `localhost:5173` | Vercel |
| Back-end | `localhost:8080` | Render |
| Banco de dados | PostgreSQL local, porta `5432` | Neon |

O back-end **devolve apenas dados** (JSON). Quem desenha as telas é o front-end.

## Estrutura do repositório

```
astrobit/
  backend/    → API em Java com Spring Boot
  frontend/   → aplicação React
  docs/       → documentação
  README.md
```

## Back-end

### Camadas

O back-end é organizado em camadas, e cada uma tem uma única responsabilidade:

| Camada | Responsabilidade |
| --- | --- |
| **Controller** | Recebe a requisição HTTP, chama o service e devolve a resposta |
| **Service** | Aplica as regras de negócio |
| **Repository** | Lê e grava no banco de dados por meio do JPA |
| **Entity** | Representa uma tabela do banco em Java |
| **DTO** | Define o formato dos dados que a API recebe e devolve |

Uma requisição sempre percorre o mesmo caminho:

```
Requisição → Controller → Service → Repository → Banco de dados
Resposta   ← Controller ← Service ← Repository ←
```

Separar as responsabilidades deixa o código mais fácil de entender e de manter. Uma mudança de regra fica no service, e uma mudança no banco fica no repository, sem afetar as outras camadas.

### Pacotes

O código fica no pacote `br.com.astrobit.api`, com um subpacote por camada:

```
br.com.astrobit.api
  controller/   → endpoints da API
  service/      → regras de negócio
  repository/   → acesso ao banco
  entity/       → tabelas mapeadas
  dto/          → entrada e saída da API
```

### Configuração

A conexão com o banco é configurada no `application.properties`, que lê os valores de **variáveis de ambiente**. Nenhuma senha fica no código.

| Variável | Para que serve |
| --- | --- |
| `DB_URL` | Endereço do banco (JDBC) |
| `DB_USERNAME` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |