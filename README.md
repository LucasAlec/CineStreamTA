# Projeto CineStream

CineStream é uma aplicação web desenvolvida utilizando **Java Spring Boot**, projetada para gerenciar filmes e séries favoritos. O sistema oferece uma interface amigável para explorar e organizar conteúdos de entretenimento, com integração a serviços externos para enriquecer a experiência do usuário.

---

## Funcionalidades

### Gerenciamento de Filmes:
- Adicionar filmes favoritos.
- Buscar filmes por gênero, ano e avaliações.
- Exibir avaliações e notas do IMDb.

### Gerenciamento de Séries:
- Adicionar séries favoritas.
- Acompanhar séries por temporadas e episódios.
- Exibir gênero e ano de lançamento.

### Gerenciamento de Usuários:
- Registrar e gerenciar perfis de usuários.
- Configurar gêneros e preferências favoritas (em desenvolvimento).

### Integração:
- Conexão com a **API TMDB** para buscar informações detalhadas de filmes e séries.

---

## Tecnologias e Frameworks Utilizados

### Backend:
- **Spring Boot 3.3.5**:
  - **spring-boot-starter-web**: Para criação de APIs REST.
  - **spring-boot-starter-security**: Para autenticação e autorização.
  - **spring-boot-starter-data-jpa**: Para integração com banco de dados.
  - **spring-boot-starter-cache**: Para gerenciamento de cache.

### Banco de Dados:
- **PostgreSQL**: Para armazenamento em produção.
- **H2**: Banco de dados em memória para testes.

### Segurança:
- **Spring Security**: Implementação de autenticação e autorização.
- **Java JWT (com.auth0)**: Para geração e validação de tokens JWT.

### Testes:
- **JUnit**: Framework para testes automatizados.
- **Mockito**: Mocking de componentes para testes unitários.
- **WireMock**: Mocking de APIs externas durante os testes.
- **Spring Security Test**: Testes de autenticação e autorização.

### Gerenciamento de Configurações:
- **Spring Dotenv**: Gerenciamento de variáveis de ambiente.

### Cache:
- **Caffeine**: Biblioteca de caching de alto desempenho.

### Ferramentas de Qualidade:
- **JaCoCo**: Para análise de cobertura de testes com integração ao Maven.

### Ferramenta de Build:
- **Maven**

---

## Testes Automatizados

O projeto possui cobertura de testes para garantir a consistência das funcionalidades e a integridade do sistema. Os testes implementados cobrem as seguintes camadas:

- **Controller:** Validação dos endpoints REST para garantir a comunicação correta entre cliente e servidor.
- **DTO:** Verificação do mapeamento correto entre entidades e objetos de transferência de dados.
- **Service:** Testes das regras de negócio e validação de comportamentos esperados.
- **Repository:** Testes das operações de persistência no banco de dados.

Os testes utilizam os frameworks **JUnit**, **Mockito** e **WireMock**, e a cobertura é analisada com **JaCoCo**. O mínimo de cobertura exigido é de 60% para a camada de classes.

---

## Endpoints da API

### Usuários
- **POST** `/register`
  - Registra um novo usuário.
  - **Requisição:** Dados do usuário no corpo da requisição.
  - **Resposta:** Detalhes do usuário criado.

- **POST** `/login`
  - Realiza autenticação do usuário.
  - **Requisição:** Credenciais do usuário no corpo da requisição.
  - **Resposta:** Token de autenticação.

- **GET** `/user/favorites`
  - Retorna os filmes e séries favoritos do usuário autenticado.
  - **Requisição:** Necessita autenticação.
  - **Resposta:** Lista de favoritos do usuário.

### Filmes
- **GET** `/api/filmes`
  - Retorna uma lista de todos os filmes disponíveis.
  - **Requisição:** Não possui parâmetros.
  - **Resposta:** Lista de filmes.

- **GET** `/api/filmes/{id}`
  - Retorna os detalhes de um filme específico.
  - **Parâmetro:** `id` do filme.
  - **Resposta:** Informações detalhadas do filme.

- **POST** `/api/filmes/favorito`
  - Adiciona um filme aos favoritos do usuário autenticado.
  - **Requisição:** Necessita autenticação e dados do filme no corpo da requisição.
  - **Resposta:** Detalhes do filme adicionado como favorito.

### Séries
- **GET** `/api/series`
  - Retorna uma lista de todas as séries disponíveis.
  - **Requisição:** Não possui parâmetros.
  - **Resposta:** Lista de séries.

- **GET** `/api/series/{id}`
  - Retorna os detalhes de uma série específica.
  - **Parâmetro:** `id` da série.
  - **Resposta:** Informações detalhadas da série.

- **POST** `/api/series/favorita`
  - Adiciona uma série aos favoritos do usuário autenticado.
  - **Requisição:** Necessita autenticação e dados da série no corpo da requisição.
  - **Resposta:** Detalhes da série adicionada como favorita.

### Favoritos
- **GET** `/usuario/favorito/**`
  - Retorna os conteúdos favoritos do usuário autenticado.
  - **Requisição:** Necessita autenticação.
  - **Resposta:** Lista de favoritos.

- **GET** `/usuario/**`
  - Permite acesso a informações públicas relacionadas a usuários.
  - **Requisição:** Não necessita autenticação.

---

## Alunos

| <img src="https://avatars.githubusercontent.com/u/61765668?v=4" width="70"> | <img src="https://avatars.githubusercontent.com/u/89415462?v=4" width="70"> | <img src="https://avatars.githubusercontent.com/u/114600184?v=4" width="70"> |
|------------------------------------------------------------------------------|------------------------------------------------------------------------------|------------------------------------------------------------------------------|
| [**Allana Ávila**](https://github.com/allanaavila)                           | [**Lucas Alec**](https://github.com/LucasAlec)                              | [**Marina Guimarães**](https://github.com/marinagv95)                              |

