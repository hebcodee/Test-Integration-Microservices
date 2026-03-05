# Sobre o Projeto

Esse é um projeto base para aplicacao de testes, ele possui testes de unidade, de integracao, e2e e workflow de
integração continua (CI/CD). O projeto realiza teste de integracao atraves do TestContainers no serviços de: Redis,
RabbitMQ, PostgreSQL, Flyway e ApiREST.

> Para melhor entendimento visualize o historico de commits

## 🗂️ Estrutura do Projeto

Abaixo está a estrutura principal do projeto:

```
api-market-place/
├── application/         # Código principal da aplicação
│   ├── src/main/java/   # Pacotes e classes Java
│   ├── src/main/resources/ # Arquivos de configuração (application.properties, etc.)
│   ├── target/          # Arquivos gerados pelo Maven
├── domain/              # Camada de domínio (entidades, serviços, etc.)
├── infrastructure/      # Configurações e integrações externas (banco, mensageria, etc.)
├── tests/               # Testes unitários e de integração
├── Dockerfile           # Configuração para container Docker
├── pom.xml              # Arquivo de configuração do Maven
```

## 🔧 Pré-requisitos

- **Java 21
  ** - [OpenJDK 21](https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_linux-x64_bin.tar.gz)
- **Maven** - Gerenciador de dependências
- **Docker** - Para executar os containers
- **IDE** - [IntelliJ IDEA Community](https://www.jetbrains.com/idea/download/)

# 🛒 API Marketplace -- Projeto base para os testes

API para gerenciamento de produtos em marketplace, desenvolvida com Spring Boot para demonstrar boas práticas de testes
de integração.

![OpenJDK 21](https://res.cloudinary.com/ddhvxva1e/image/upload/v1772685830/Sem_t%C3%ADtulo_1_sdagiz.jpg)

## 🐳 Dependências via Docker

Execute os seguintes comandos para iniciar os serviços necessários:

```bash
# Banco de dados
docker run -d -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=postgres postgres

# AWS Local (S3, etc)
docker run -d -p 4566:4566 -p 4510-4559:4510-4559 --name localstack localstack/localstack

# Message Broker
docker run -d -p 5672:5672 --name rabbitmq rabbitmq

# Cache
docker run -d -p 6379:6379 --name redis redis
```

## ⚙️ Configuração

### Variáveis de Ambiente

| Variável                 | Valor               |
|--------------------------|---------------------|
| `spring.profiles.active` | `local,infra_local` |

## 🚀 Execução

### Build

```bash
mvn clean install -DskipTests
```

### Aplicação

```bash
java -jar -Dspring.profiles.active=local,infra_local application/target/api-market-place.application-0.0.1-SNAPSHOT.jar
```

### Docker

```bash
docker run -d -p 8080:8080 --name=api-market-place rodsordi/api-market-place:master
```

## ✅ Testes

### Testes Unitários

```bash
mvn test
```

### Testes de Integração

```bash
mvn test -DintegrationTests
```

## 📌 Versionamento

Este projeto segue [Semantic Versioning](https://semver.org/lang/pt-BR/).

## License

MIT License