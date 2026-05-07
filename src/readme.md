# Paulo Motor's API 🚗💨

Esta é uma API robusta de gerenciamento de locação de veículos desenvolvida para consolidar conhecimentos em segurança avançada, arquitetura REST e boas práticas de desenvolvimento com o ecossistema Spring.

## 🎯 Foco do Projeto
O desenvolvimento desta API foi guiado pelo estudo aprofundado de quatro pilares fundamentais:

1. **Spring Security**: Implementação de controle de acesso baseado em funções (RBAC - Role Based Access Control), distinguindo permissões entre Gerentes (`MANAGER`) e Clientes (`CLIENT`).
2. **JWT (JSON Web Token)**: Autenticação stateless para garantir segurança e escalabilidade, utilizando tokens assinados com algoritmos HMAC256.
3. **Validação de Campos**: Uso de Bean Validation (Jakarta Validation) para garantir a integridade dos dados de entrada antes do processamento.
4. **Auditoria de Entradas**: Documentação detalhada e monitoramento dos endpoints para garantir rastreabilidade e facilidade de uso.

## 🛠️ Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3**
- **Spring Security**
- **Auth0 JWT**
- **Spring Data JPA** (PostgreSQL)
- **SpringDoc OpenAPI (Swagger)**

## 📑 Documentação da API (Swagger)
A API conta com uma documentação interativa completa. Abaixo, uma prévia da interface onde é possível testar os endpoints e visualizar a estrutura de segurança implementada:

![Documentação Swagger](./Swagger.jpg)

## 🔐 Segurança e Autenticação

### Fluxo de Acesso:
1. **Registro**: O usuário se cadastra via `/auth/register` definindo sua `UserRole`.
2. **Login**: O usuário autentica-se em `/auth/login` e recebe um Token JWT.
3. **Autorização**: O token deve ser enviado no Header de cada requisição:
    - `Authorization: Bearer <TOKEN_AQUI>`

### Hierarquia de Permissões:
- **MANAGER**: Acesso total (CRUD de carros, gestão de gerentes e aluguéis).
- **CLIENT**: Acesso limitado (Visualização de frota e realização de aluguéis).

## 🚀 Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/paulo-motors-api.git](https://github.com/seu-usuario/paulo-motors-api.git)
Configure as variáveis de ambiente no seu application.yml ou no sistema:

DB_URL, DB_USERNAME, DB_PASSWORD

JWT_SECRET

Execute a aplicação via IntelliJ ou terminal:

Bash
./mvnw spring-boot:run
Acesse o Swagger: http://localhost:8080/swagger-ui/index.html

Desenvolvido por Paulo Maggio durante os estudos de Spring Boot Avançado.