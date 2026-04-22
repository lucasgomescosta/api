# API de Tarefas - Spring Boot

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15.7-blue)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-green)](https://swagger.io/)

Uma API REST completa para gerenciamento de tarefas, desenvolvida com Spring Boot, PostgreSQL, Flyway e documentação Swagger/OpenAPI.

## 📋 Índice

- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Executando a Aplicação](#-executando-a-aplicação)
- [Documentação da API](#-documentação-da-api)
- [Endpoints](#-endpoints)
- [Exemplos de Uso](#-exemplos-de-uso)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Banco de Dados](#-banco-de-dados)
- [Configurações](#-configurações)
- [Testes](#-testes)
- [Deploy](#-deploy)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

## ✨ Funcionalidades

- ✅ **CRUD Completo**: Criar, listar, buscar, atualizar e deletar tarefas
- ✅ **Validação de Dados**: Validações automáticas com Jakarta Validation
- ✅ **Documentação Interativa**: Swagger UI para testar a API
- ✅ **Mapeamento DTO**: Conversão automática entre entidades e DTOs
- ✅ **Connection Pool**: HikariCP para gerenciamento de conexões
- ✅ **Migrations**: Flyway para versionamento do banco de dados
- ✅ **Buscas Avançadas**: Pesquisa por título e local
- ✅ **Arquitetura Limpa**: Separação de responsabilidades (Controller/Service/Repository)

## 🛠️ Tecnologias

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Hibernate 7.2.7** - ORM
- **PostgreSQL 15.7** - Banco de dados
- **HikariCP 7.0.2** - Connection pool
- **Flyway 10.4.1** - Migrations de banco

### Validação e Documentação
- **Jakarta Validation** - Validação de dados
- **Hibernate Validator 9.0.1** - Implementação da validação
- **Springdoc OpenAPI 2.6.0** - Documentação Swagger
- **Swagger UI 5.17.14** - Interface interativa

### Utilitários
- **ModelMapper 3.2.4** - Mapeamento DTO ↔ Entidade
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências

## 📋 Pré-requisitos

Antes de começar, você precisa ter instalado:

- **Java 21** ou superior
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git** (opcional, para clonar o repositório)

### Verificar Instalação

```bash
# Verificar Java
java -version
# Deve mostrar: Java 21.x.x

# Verificar Maven
mvn -version
# Deve mostrar: Apache Maven 3.x.x

# Verificar PostgreSQL
psql --version
# Deve mostrar: PostgreSQL 15.x
```

## 🚀 Instalação e Configuração

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/api-tarefas.git
cd api-tarefas
```

### 2. Configurar o Banco de Dados

```bash
# Criar banco de dados
createdb tarefas_db

# Ou via psql
psql -U postgres
CREATE DATABASE tarefas_db;
\q
```

### 3. Configurar Credenciais

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Atualize as credenciais do PostgreSQL
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 4. Compilar o Projeto

```bash
mvn clean compile
```

## ▶️ Executando a Aplicação

### Desenvolvimento

```bash
# Executar com Maven
mvn spring-boot:run

# Ou executar o JAR
mvn clean package
java -jar target/tarefas-0.0.1-SNAPSHOT.jar
```

### Produção

```bash
# Build otimizado
mvn clean package -Dspring.profiles.active=prod

# Executar
java -jar target/tarefas-0.0.1-SNAPSHOT.jar
```

### Verificar Inicialização

A aplicação estará disponível em:
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Documentação JSON**: http://localhost:8080/v3/api-docs

## 📚 Documentação da API

### Swagger UI

Acesse http://localhost:8080/swagger-ui.html para:

- 📖 **Visualizar documentação interativa**
- 🧪 **Testar endpoints diretamente**
- 📋 **Ver modelos de dados**
- 🔍 **Explorar parâmetros e respostas**

### OpenAPI Specification

- **JSON**: http://localhost:8080/v3/api-docs
- **YAML**: http://localhost:8080/v3/api-docs.yaml

## 🔗 Endpoints

### Base URL
```
http://localhost:8080/tarefas
```

### Endpoints Disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/tarefas` | Criar nova tarefa |
| `GET` | `/tarefas` | Listar todas as tarefas |
| `GET` | `/tarefas/{id}` | Obter tarefa por ID |
| `GET` | `/tarefas/buscar/titulo?titulo={texto}` | Buscar por título |
| `GET` | `/tarefas/buscar/local?local={texto}` | Buscar por local |
| `PUT` | `/tarefas/{id}` | Atualizar tarefa |
| `DELETE` | `/tarefas/{id}` | Deletar tarefa |

### Códigos de Status HTTP

- **200 OK**: Sucesso
- **201 Created**: Recurso criado
- **204 No Content**: Recurso deletado
- **400 Bad Request**: Dados inválidos
- **404 Not Found**: Recurso não encontrado
- **500 Internal Server Error**: Erro interno

## 💡 Exemplos de Uso

### Criar uma Tarefa

```bash
POST /tarefas
Content-Type: application/json

{
  "titulo": "Implementar API REST",
  "descricao": "Criar endpoints completos para gerenciamento de tarefas",
  "local": "Escritório",
  "dataHora": "2026-04-06T14:00:00"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "titulo": "Implementar API REST",
  "descricao": "Criar endpoints completos para gerenciamento de tarefas",
  "local": "Escritório",
  "dataHora": "2026-04-06T14:00:00"
}
```

### Listar Todas as Tarefas

```bash
GET /tarefas
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Implementar API REST",
    "descricao": "Criar endpoints completos",
    "local": "Escritório",
    "dataHora": "2026-04-06T14:00:00"
  }
]
```

### Buscar por Título

```bash
GET /tarefas/buscar/titulo?titulo=API
```

### Atualizar Tarefa

```bash
PUT /tarefas/1
Content-Type: application/json

{
  "titulo": "Implementar API REST - v2",
  "descricao": "Criar endpoints completos com validações",
  "local": "Casa",
  "dataHora": "2026-04-07T10:00:00"
}
```

### Deletar Tarefa

```bash
DELETE /tarefas/1
```

## 📁 Estrutura do Projeto

```
api-tarefas/
├── src/
│   ├── main/
│   │   ├── java/br/o/tarefas/
│   │   │   ├── TarefasApplication.java          # Classe principal
│   │   │   ├── config/                          # Configurações
│   │   │   │   ├── DataSourceConfig.java        # Configuração HikariCP
│   │   │   │   ├── ModelMapperConfig.java       # Configuração ModelMapper
│   │   │   │   └── SwaggerConfig.java           # Configuração Swagger
│   │   │   ├── controller/                      # Controllers REST
│   │   │   │   └── TarefasController.java       # Endpoints da API
│   │   │   ├── dto/                             # Data Transfer Objects
│   │   │   │   └── TarefaDTO.java               # DTO da entidade Tarefa
│   │   │   ├── entidade/                        # Entidades JPA
│   │   │   │   └── Tarefa.java                  # Entidade Tarefa
│   │   │   ├── repository/                      # Repositórios
│   │   │   │   └── TarefaRepository.java        # Interface JPA
│   │   │   └── service/                         # Serviços
│   │   │       └── TarefaService.java           # Lógica de negócio
│   │   └── resources/
│   │       ├── application.properties           # Configurações
│   │       └── db/migration/                    # Migrations Flyway
│   │           └── V1__Create_Tarefa_Table.sql  # Migration inicial
│   └── test/                                    # Testes
├── target/                                       # Arquivos compilados
├── pom.xml                                       # Dependências Maven
└── README.md                                     # Este arquivo
```

## 🗄️ Banco de Dados

### Tabela: `tarefa`

```sql
CREATE TABLE tarefa (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    local VARCHAR(255),
    data_hora TIMESTAMP
);

-- Índice para otimizar buscas por título
CREATE INDEX idx_tarefa_titulo ON tarefa(titulo);
```

### Migration Flyway

Localizada em: `src/main/resources/db/migration/V1__Create_Tarefa_Table.sql`

## ⚙️ Configurações

### application.properties

```properties
# Aplicação
spring.application.name=tarefas

# DataSource PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/tarefas_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# HikariCP Connection Pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.auto-commit=true

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# Swagger/OpenAPI
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.use-root-path=true
springdoc.swagger-ui.operations-sorter=method
springdoc.swagger-ui.tags-sorter=alpha
```

## 🧪 Testes

### Executar Testes

```bash
# Todos os testes
mvn test

# Testes com cobertura
mvn test jacoco:report

# Testes de integração
mvn verify
```

### Estrutura de Testes

```
src/test/java/br/o/tarefas/
├── TarefasApplicationTests.java          # Teste da aplicação
├── controller/TarefasControllerTest.java # Testes dos endpoints
├── service/TarefaServiceTest.java        # Testes da lógica
└── repository/TarefaRepositoryTest.java  # Testes do repositório
```

## 🚀 Deploy

### Docker

```dockerfile
# Dockerfile
FROM openjdk:21-jdk-slim
COPY target/tarefas-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
# Build e execução
docker build -t api-tarefas .
docker run -p 8080:8080 api-tarefas
```

### Docker Compose

```yaml
# docker-compose.yml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/tarefas_db

  db:
    image: postgres:15
    environment:
      - POSTGRES_DB=tarefas_db
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    ports:
      - "5432:5432"
```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Padrões de Código

- Use Java 21
- Siga os princípios SOLID
- Mantenha a documentação atualizada
- Escreva testes para novas funcionalidades

## 📝 Licença

Este projeto está sob a licença Apache 2.0. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Suporte

Para suporte, entre em contato:

- **Email**: dev@tarefas.com
- **GitHub Issues**: [Issues](https://github.com/seu-usuario/api-tarefas/issues)
- **Documentação**: http://localhost:8080/swagger-ui.html

---

## 🎯 Roadmap

- [ ] Autenticação JWT
- [ ] Paginação nos endpoints
- [ ] Cache Redis
- [ ] Testes de carga
- [ ] CI/CD Pipeline
- [ ] Monitoramento com Actuator
- [ ] API Gateway
- [ ] Microserviços

---

**Desenvolvido com ❤️ usando Spring Boot**
