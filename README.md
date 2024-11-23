# Projeto CineStream

CineStream é uma aplicação web desenvolvida utilizando **Java Spring Boot**, projetada para gerenciar filmes e séries favoritos, proporcionando uma interface intuitiva e amigável para explorar e organizar conteúdos de entretenimento.

## Funcionalidades

- **Gerenciamento de Filmes**:
    - Adicionar filmes favoritos.
    - Buscar filmes por gênero, ano e avaliações.
    - Exibir avaliações e notas do IMDb.

- **Gerenciamento de Séries**:
    - Adicionar séries favoritas.
    - Acompanhar séries por temporadas e episódios.
    - Exibir gênero e ano de lançamento.

- **Gerenciamento de Usuários**:
    - Registrar e gerenciar perfis de usuários.
    - Salvar gêneros e preferências favoritas. (?)

- **Integração**:
    - Integração com a API TMDB para obter detalhes de filmes e séries.

## Tecnologias Utilizadas

- **Backend**:
    - Framework Java Spring Boot
    - JPA para ORM
    - APIs REST para comunicação
- **Banco de Dados**:
    - H2
- **Ferramenta de Build**:
    - Maven
- **Testes**: (?)
    - JUnit para testes automatizados (?)
- **Integração de API**:
    - API TMDB

## Estrutura do Projeto e Distribuição dos Pacotes

O projeto segue uma arquitetura em camadas e está organizado nos seguintes pacotes:

```
CineStreamTAI/
│
├── client/
│   └── tmdbapi/
│       ├── ApiClient.java          # Cliente HTTP para integrar com a API TMDB
│       └── dto/response/           # DTOs com as respostas da API TMDB
│
├── controller/
│   ├── FilmeController.java        # Controlador para endpoints REST de filmes
│   ├── SerieController.java        # Controlador para endpoints REST de séries
│   ├── UsuarioController.java      # Controlador para endpoints REST de usuários
│   └── AdviceController.java       # Tratamento global de exceções
│
├── dto/
│   ├── request/                    # DTOs para capturar dados das requisições
│   ├── response/                   # DTOs para formatar respostas
│   └── mapping/                    # Mapeadores entre entidades e DTOs
│
├── exception/
│   ├── AlreadyExistsException.java # Exceção para recursos duplicados
│   └── NotFoundException.java      # Exceção para recursos não encontrados
│
├── model/
│   ├── FilmeFavorito.java          # Entidade para filmes favoritos
│   ├── SerieFavorita.java          # Entidade para séries favoritas
│   ├── Usuario.java                # Entidade para usuários
│   └── GeneroFavorito.java         # Entidade para gêneros favoritos
│
├── repository/
│   ├── FilmeFavoritoRepository.java  # Repositório para persistência de filmes
│   ├── SerieFavoritaRepository.java  # Repositório para persistência de séries
│   ├── UsuarioRepository.java        # Repositório para persistência de usuários
│   └── GeneroFavoritoRepository.java # Repositório para persistência de gêneros
│
├── service/
│   ├── FilmeService.java           # Lógica de negócios para filmes
│   ├── SerieService.java           # Lógica de negócios para séries
│   └── UsuarioService.java         # Lógica de negócios para usuários
│
└── SpringCinestreamApplication.java  # Ponto de entrada principal da aplicação
```


## Endpoints da API

### Filmes
- **`POST /movies`**
    - **Descrição:** Adiciona um novo filme favorito.
    - **Requisição:** Dados do filme no corpo da requisição.
    - **Resposta:** Detalhes do filme criado.

- **`GET /movies`**
    - **Descrição:** Retorna uma lista de todos os filmes favoritos.
    - **Requisição:** Não possui parâmetros.
    - **Resposta:** Lista de filmes favoritos.

- **`GET /movies/{id}`**
    - **Descrição:** Retorna os detalhes de um filme específico.
    - **Parâmetro:** `id` do filme.
    - **Resposta:** Informações detalhadas do filme.

### Séries
- **`POST /series`**
    - **Descrição:** Adiciona uma nova série favorita.
    - **Requisição:** Dados da série no corpo da requisição.
    - **Resposta:** Detalhes da série criada.

- **`GET /series`**
    - **Descrição:** Retorna uma lista de todas as séries favoritas.
    - **Requisição:** Não possui parâmetros.
    - **Resposta:** Lista de séries favoritas.

- **`GET /series/{id}`**
    - **Descrição:** Retorna os detalhes de uma série específica.
    - **Parâmetro:** `id` da série.
    - **Resposta:** Informações detalhadas da série.

### Usuários
- **`POST /users`**
    - **Descrição:** Registra um novo usuário.
    - **Requisição:** Dados do usuário no corpo da requisição.
    - **Resposta:** Detalhes do usuário criado.

- **`GET /users`**
    - **Descrição:** Retorna uma lista de perfis de usuários.
    - **Requisição:** Não possui parâmetros.
    - **Resposta:** Lista de perfis de usuários registrados.

### Gêneros (Integrados à API TMDB)
- **`GET /genres`**
    - **Descrição:** Retorna uma lista de gêneros de filmes e séries disponíveis.
    - **Requisição:** Não possui parâmetros.
    - **Resposta:** Lista de gêneros disponíveis.
