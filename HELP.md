## Para executar o banco de dados usando Docker (só para ficar mais simples)

### Crie o arquivo `.env` baseado no `.env.example`

**1.** Use o `.env.example` como molde e insira os dados necessários

**2.** Logo após execute o comando para rodar seu container Docker (Lembra de fechar ele depois)

```bash
docker run --name cinestream --env-file .env -d -p 5433:5432 -v cinestreamdata:/var/lib/postgresql/data postgres:latest
```

#### Se já tiver rodado alguma vez e o container tiver no seu PC, para iniciar bastar executar isso:

```bash
docker start cinestream
```

### Instale as dependências

**1. Se tiver no Mac/Linux:**

```bash
./mvnw clean install
```

**2. Se tiver no Ruindows:**
```shell
./mvnw.cmd clean install
```