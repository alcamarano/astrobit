<h1 align="center">AstroBit</h1>

<p align="center">
  <strong>Pequenos passos, grandes jornadas.</strong><br>
  Aplicação web para organizar tarefas, acompanhar hábitos e ver sua constância crescer, um bit por dia.
</p>

<p align="center">
  <img alt="Status" src="https://img.shields.io/badge/status-em%20desenvolvimento-F4A51C">
  <img alt="Java" src="https://img.shields.io/badge/Java-25-4B3FD1">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-back--end-4B3FD1">
  <img alt="React" src="https://img.shields.io/badge/React-TypeScript-4B3FD1">
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-18-4B3FD1">
</p>

<p align="center">
  <a href="#sobre">Sobre</a> ·
  <a href="#funcionalidades">Funcionalidades</a> ·
  <a href="#telas">Telas</a> ·
  <a href="#tecnologias">Tecnologias</a> ·
  <a href="#documentação">Documentação</a> ·
  <a href="#roadmap">Roadmap</a>
</p>

## Sobre

O **AstroBit** junta num lugar só a lista de tarefas do dia e o acompanhamento de hábitos, mostrando o progresso de forma visual.

O nome vem de duas ideias:

- **Astro**, de astronauta: cada dia é uma pequena missão.
- **Bit**, a menor unidade de informação: cada dia cumprido de um hábito acende um bit na sua semana.

É um projeto de portfólio feito de ponta a ponta, do design no Figma ao deploy, para praticar front-end, back-end e banco de dados.

> 🔗 **App no ar:** _em breve_

## Funcionalidades

- [ ] Cadastro e login com autenticação por token (JWT)
- [ ] **Início:** painel "Hoje" com o resumo de tarefas e hábitos do dia
- [ ] **Tarefas:** criar, editar, concluir, filtrar e apagar tarefas com data
- [ ] **Hábitos:** escolher os dias da semana em que o hábito conta, marcar o dia e acompanhar a sequência de dias seguidos
- [ ] **Perfil:** ver e editar os dados da conta
- [ ] Tema claro e escuro
- [ ] Layout adaptado para desktop e celular

### Como funcionam os hábitos

Nem todo hábito é diário. Ao criar um hábito, você escolhe os dias em que ele conta (por exemplo, *Treinar* às segundas, quartas, quintas e sábados). Nos outros dias é **folga**: eles não quebram a sequência.

## Telas

_Prints em breve._

<!--
Quando as telas estiverem prontas, adicione os prints aqui. Exemplo:

| Claro | Escuro |
| --- | --- |
| ![Início claro](docs/img/inicio-claro.png) | ![Início escuro](docs/img/inicio-escuro.png) |
-->

## Tecnologias

| Camada | Tecnologias |
| --- | --- |
| Front-end | React, TypeScript, Vite, React Router, Context API, CSS Modules |
| Back-end | Java 25, Spring Boot, Spring Web, Spring Data JPA, Spring Security, JWT, Bean Validation, Flyway |
| Banco de dados | PostgreSQL |
| Design | Figma (variáveis de cor por tema e componentes) |
| Publicação | Vercel (front-end), Render (back-end), Neon (banco) |

## Arquitetura

```
Navegador → Front-end (React, na Vercel)
             ↓ requisições HTTP com JSON + token JWT
          Back-end (Spring Boot, no Render)
             ↓ JPA
          Banco de dados (PostgreSQL, no Neon)
```

### Estrutura do repositório

```
astrobit/
├── backend/    → API em Java com Spring Boot
├── frontend/   → aplicação React com TypeScript e Vite
├── docs/       → documentação do projeto
└── README.md
```

## Documentação

| Arquivo | Conteúdo |
| --- | --- |
| [Arquitetura](docs/arquitetura.md) | Como front-end, back-end e banco conversam |
| [Banco de dados](docs/banco-de-dados.md) | Tabelas, campos e relacionamentos |
| [API](docs/api.md) | Endpoints, o que recebem e o que devolvem |
| [Decisões técnicas](docs/decisoes.md) | O que foi escolhido, as alternativas e o porquê |

## Roadmap

- [x] Definição da stack e planejamento
- [x] Design das telas (desktop e celular, temas claro e escuro)
- [ ] Back-end: tarefas
- [ ] Back-end: usuários, login e JWT
- [ ] Back-end: hábitos com dias programados
- [ ] Front-end: telas e navegação
- [ ] Integração front-end e back-end
- [ ] Tema claro e escuro
- [ ] Publicação
- [ ] **Depois da versão 1:** rotina e recuperação de senha

## Autor

Feito por **Augusto Lima Camarano**.

[LinkedIn](https://www.linkedin.com/in/alcamarano/) · [Portfólio](https://github.com/alcamarano)

---

<p align="center">© 2026 AstroBit. Todos os direitos reservados.</p>
