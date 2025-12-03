# 🔐 Auth Service -- Sistema de Autenticação com JWT

O **Auth Service** é um microserviço responsável por autenticação e
autorização usando **Spring Boot 3**, **Spring Security**, e **JWT (JSON
Web Token)**.\
Ele fornece endpoints para registro, login, validação de credenciais e
gerenciamento de usuários e roles.

------------------------------------------------------------------------

## 🚀 Funcionalidades

-   Registro de usuários com roles dinâmicas
-   Autenticação via username/password
-   Geração de token JWT
-   Validação automática do token em cada requisição
-   Segurança stateless
-   Exception Handler Global para erros de autenticação e regras de
    negócio
-   Endpoints públicos para login e registro
-   Integração com banco PostgreSQL

------------------------------------------------------------------------

## 📌 Endpoints

### **POST /auth/register**

Registra um novo usuário.

**Request:**

``` json
{
  "username": "daniel",
  "password": "123456",
  "roles": ["ADMIN", "USER"]
}
```

------------------------------------------------------------------------

### **POST /auth/login**

Autentica o usuário e retorna o JWT.

**Request:**

``` json
{
  "username": "daniel",
  "password": "123456"
}
```

**Response:**

``` json
{
  "token": "jwt-gerado-aqui"
}
```

------------------------------------------------------------------------

## 🧱 Arquitetura

-   **Controller** → Recebe requisições e retorna respostas.
-   **Service** → Regras de negócio (login, registro, validação).
-   **Repository** → Persistência via JPA/Hibernate.
-   **Entity** → User e Role.
-   **Security**
    -   AuthenticationProvider\
    -   SecurityFilterChain\
    -   JWT Util\
    -   Filtro JWT

------------------------------------------------------------------------

## 🛡️ Segurança

Implementado com:

-   **Spring Security 6**
-   **JWT Stateless**
-   **PasswordEncoder (BCrypt)**\
-   **AuthenticationManager**
-   **Filtros customizados**

Fluxo:

1.  Usuário envia credenciais.
2.  Credenciais são validadas.
3.  JWT é gerado e retornado.
4.  Nas próximas requisições, o token é validado.
5.  Acesso liberado ou negado dependendo das roles.

------------------------------------------------------------------------

## 🗄 Banco de Dados

Exemplo de configuração:

``` properties
spring.datasource.url=jdbc:postgresql://host:5432/auth_service
spring.datasource.username=auth_user
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
```

------------------------------------------------------------------------

## 📘 Documentação

Swagger disponível em:

    http://localhost:8081/swagger-ui.html

------------------------------------------------------------------------

## ▶️ Como Executar

``` bash
mvn clean install
mvn spring-boot:run
```

------------------------------------------------------------------------

## 📦 Tecnologias Utilizadas

-   Java 21\
-   Spring Boot 3\
-   Spring Security\
-   JWT\
-   PostgreSQL\
-   Maven\
-   JPA/Hibernate\
-   Swagger/OpenAPI

------------------------------------------------------------------------

## 📝 Sobre o Projeto

Este microserviço foi criado para fornecer autenticação segura em uma
arquitetura de microserviços, garantindo tokens JWT confiáveis e
controle de acesso baseado em roles.
