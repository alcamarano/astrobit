# Decisões técnicas

Registro das principais escolhas do AstroBit: **o que foi decidido, quais eram as alternativas e por quê**.

## Sumário

| Área | Decisão |
| --- | --- |
| Produto | [Escopo da primeira versão](#escopo-da-primeira-versão) |
| Produto | [Dias programados nos hábitos](#dias-programados-nos-hábitos) |
| Front-end | [Vite + React Router em vez de Next.js](#vite--react-router-em-vez-de-nextjs) |
| Front-end | [Variáveis CSS em vez de Tailwind](#variáveis-css-em-vez-de-tailwind) |
| Front-end | [Modais para criar e editar](#modais-para-criar-e-editar) |
| Back-end | [Java e Spring Boot](#java-e-spring-boot) |
| Back-end | [Oracle JDK 25](#oracle-jdk-25) |
| Back-end | [DTOs em vez de devolver entidades](#dtos-em-vez-de-devolver-entidades) |
| Segurança | [JWT para autenticação](#jwt-para-autenticação) |
| Segurança | [BCrypt para senhas](#bcrypt-para-senhas) |
| API | [REST no nível 2 de Richardson](#rest-no-nível-2-de-richardson) |
| API | [Concluir tarefa com PATCH no recurso](#concluir-tarefa-com-patch-no-recurso) |
| Banco de dados | [PostgreSQL como banco de dados](#postgresql-como-banco-de-dados) |
| Banco de dados | [Flyway para migrations](#flyway-para-migrations) |
| Banco de dados | [UUIDv7 como chave primária](#uuidv7-como-chave-primária) |
| Banco de dados | [Nomes em inglês e com padrão](#nomes-em-inglês-e-com-padrão) |
| Banco de dados | [Exclusão em cascata](#exclusão-em-cascata) |
| Banco de dados | [Descrição com limite de 300 caracteres](#descrição-com-limite-de-300-caracteres) |
| Banco de dados | [Modelagem no ERD Tool do pgAdmin](#modelagem-no-erd-tool-do-pgadmin) |
| Publicação | [Neon, Render e Vercel](#neon-render-e-vercel) |

---

## Produto

### Escopo da primeira versão

- **Escolha:** a versão 1 entrega cadastro e login, tarefas, hábitos, painel Início, perfil, tema claro e escuro e layout para desktop e celular, tudo publicado.
- **Fica para depois:** rotina (blocos de horário por dia da semana), recuperação de senha e testes automáticos.
- **Por quê:** priorizar um núcleo completo e publicado em vez de muitas funcionalidades pela metade. A rotina segue o mesmo padrão técnico dos hábitos (tabela, API e tela), então entra como evolução sem retrabalho.

### Dias programados nos hábitos

- **Escolha:** cada hábito tem os dias da semana em que conta. Os outros dias são folga e não quebram a sequência.
- **Alternativa:** hábitos sempre diários.
- **Por quê:** nem todo hábito é diário (ex.: treinar 4 vezes por semana). Sem dias programados, a sequência quebraria nos dias de descanso.

## Front-end

### Vite + React Router em vez de Next.js

- **Escolha:** aplicação de página única (SPA) com Vite e React Router.
- **Alternativa:** Next.js.
- **Por quê:** o back-end já é Java. O Next.js se destaca quando ele mesmo é o servidor, o que duplicaria papéis. O app é usado depois do login, então SEO não é um requisito.

### Variáveis CSS em vez de Tailwind

- **Escolha:** CSS Modules + variáveis CSS.
- **Alternativa:** Tailwind CSS.
- **Por quê:** o tema claro e escuro se resolve com variáveis e um atributo `data-theme` no `<html>`. Tailwind só compensaria se fosse usado no projeto inteiro, e o objetivo é praticar CSS.

### Modais para criar e editar

- **Escolha:** toda criação e edição acontece em modal (gaveta inferior no celular).
- **Por quê:** a pessoa não sai da tela em que está, e o padrão fica igual em todo o app.

## Back-end

### Java e Spring Boot

- **Escolha:** Java com Spring Boot.
- **Alternativa:** Node.js com TypeScript.
- **Por quê:** aprofundar em Java e ter um back-end robusto, com um ecossistema muito usado no mercado. Um único back-end evita trabalho duplicado.

### Oracle JDK 25

- **Escolha:** Oracle JDK 25 (LTS).
- **Alternativas:** Eclipse Temurin, Oracle JDK 21.
- **Por quê:** versão LTS mais recente, gratuita sob a licença atual da Oracle até setembro de 2028. Em produção, o Java vem da imagem Docker, e o código roda igual.

### DTOs em vez de devolver entidades

- **Escolha:** a API recebe e devolve objetos próprios (DTOs).
- **Alternativa:** expor as entidades JPA diretamente.
- **Por quê:** evita expor campos internos, como o hash da senha, e separa o formato da API do formato do banco.

## Segurança

### JWT para autenticação

- **Escolha:** o login devolve um token JWT, enviado no cabeçalho `Authorization: Bearer`.
- **Alternativa:** sessão no servidor com cookie.
- **Por quê:** front-end e back-end ficam em domínios diferentes (Vercel e Render), e o JWT funciona bem nesse cenário sem guardar sessão no servidor (stateless).

### BCrypt para senhas

- **Escolha:** senhas guardadas como hash BCrypt.
- **Por quê:** a senha nunca é armazenada nem criptografada de forma reversível. O hash não pode ser desfeito, e o BCrypt é lento de propósito e usa salt, o que dificulta ataques.

## API

### REST no nível 2 de Richardson

- **Escolha:** recursos identificados por URL, verbos HTTP e códigos de status corretos, e comunicação stateless com JWT.
- **Fora do escopo:** HATEOAS (nível 3), pouco usado na prática.

### Concluir tarefa com PATCH no recurso

- **Escolha:** `PATCH /api/tasks/{id}` com `{ "completed": true }` no corpo.
- **Alternativa:** `PATCH /api/tasks/{id}/complete`, com a ação na URL.
- **Por quê:** no REST, a URL nomeia o recurso e o verbo HTTP indica a ação. Alterar um campo do recurso é exatamente o papel do PATCH.

## Banco de dados

### PostgreSQL como banco de dados

- **Escolha:** PostgreSQL 18, instalado localmente no desenvolvimento e no Neon em produção.
- **Alternativas:** MySQL (relacional) e MongoDB (não relacional).
- **Por quê:** os dados do AstroBit são relacionais por natureza (um usuário tem tarefas e hábitos; um hábito tem dias programados e registros), então um banco relacional garante essas ligações com chaves estrangeiras e constraints. Um banco não relacional como o MongoDB deixaria essas garantias para o código. Entre os relacionais, o PostgreSQL é gratuito, de código aberto, robusto e muito usado no mercado; a versão 18 gera UUIDv7 nativamente; e o Neon oferece um plano gratuito permanente, o que permite usar o mesmo banco no desenvolvimento e em produção.

### Flyway para migrations

- **Escolha:** tabelas criadas e alteradas por migrations SQL versionadas.
- **Alternativa:** deixar o Hibernate criar as tabelas automaticamente.
- **Por quê:** o banco local e o de produção ficam sempre com a mesma estrutura, e o histórico de mudanças fica no Git, junto com o código.

### UUIDv7 como chave primária

- **Escolha:** todas as tabelas com `id` do tipo `uuid`, gerado pelo PostgreSQL com `uuidv7()`.
- **Alternativa:** `BIGINT` sequencial.
- **Por quê:** os ids aparecem nas URLs da API, e um número sequencial é fácil de adivinhar. O UUIDv7 é ordenado pelo tempo, então mantém os índices eficientes, ao contrário do UUID v4, que é aleatório. O PostgreSQL 18 gera esse tipo nativamente.
- **Custo:** 16 bytes em vez de 8 e ids menos legíveis na depuração. Irrelevante na escala do projeto.

### Nomes em inglês e com padrão

- **Escolha:** tabelas no plural, colunas em `snake_case`, chave estrangeira como `tabela_id` e constraints com prefixo (`pk_`, `fk_`, `uq_`, `ck_`, `idx_`).
- **Por quê:** é o padrão mais comum no mercado e combina com o código em inglês. Com prefixo, uma mensagem de erro já indica qual regra foi violada (ex.: `uq_users_email`).

### Exclusão em cascata

- **Escolha:** todas as chaves estrangeiras com `ON DELETE CASCADE`.
- **Alternativa:** `RESTRICT`, impedindo a exclusão enquanto houver registros ligados.
- **Por quê:** apagar a conta remove todos os dados da pessoa, e apagar um hábito remove os dias programados e o histórico. Não sobram dados órfãos.

### Descrição com limite de 300 caracteres

- **Escolha:** `description` de tarefas e hábitos como `varchar(300)`, com o mesmo limite no front-end e na validação do back-end.
- **Alternativa:** `text` sem limite.
- **Por quê:** sem limite, uma chamada direta à API poderia gravar textos enormes. 300 caracteres comportam com folga uma descrição detalhada. O limite existe nas três camadas: o front orienta o usuário, o back é a proteção principal e o banco é a última barreira.

### Modelagem no ERD Tool do pgAdmin

- **Escolha:** diagrama feito no ERD Tool e salvo em [`banco-de-dados.pgerd`](./banco-de-dados.pgerd). O [`banco-de-dados.md`](./banco-de-dados.md) mostra o modelo em Mermaid.
- **Alternativas:** dbdiagram.io, draw.io.
- **Por quê:** já vem com o PostgreSQL, usa os tipos reais do banco e gera o SQL de referência.

## Publicação

### Neon, Render e Vercel

- **Escolha:** Neon (banco), Render (back-end) e Vercel (front-end).
- **Por quê:** planos gratuitos e deploy a partir do GitHub. O Neon tem plano gratuito permanente, enquanto o PostgreSQL gratuito do Render expira em 30 dias.
- **Limitação:** o servidor gratuito do Render hiberna após 15 minutos sem uso e leva cerca de 1 minuto para voltar. O front-end trata isso com a tela "Acordando a base...".