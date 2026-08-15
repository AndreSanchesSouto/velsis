# 📦 Sistema de Gerenciamento de Usuários e Endereços

**Este projeto foi desenvolvido como um case técnico para a empresa [Velsis](https://velsis.com.br/).**  
A aplicação consiste em uma solução *fullstack* para cadastro e gerenciamento de usuários e seus endereços, com integração automática à API pública do **ViaCEP** para preenchimento de dados postais, autenticação via **JWT**, controle de acesso baseado em perfis (**RBAC**) e uma interface moderna e responsiva.

---

## 🛠️ Stack Tecnológica

### Backend (API)
- **Java 25**  
- **Spring Boot 4.1.0** – framework principal (com suporte a Spring MVC)  
- **Spring Security** – autenticação e autorização  
- **JJWT (0.13.0)** – geração e validação de tokens JWT  
- **Spring Data JPA / Hibernate** – ORM e persistência  
- **PostgreSQL** – banco de dados em produção (com suporte a H2 para testes)  
- **Maven** – gerenciamento de dependências e build  
- **Bean Validation** – validação de dados  
- **Spring Boot DevTools** – desenvolvimento ágil com reload automático  

### Frontend (Apoio Visual)
- **Vue 3** – framework progressivo para a UI  
- **Vite** – bundler e servidor de desenvolvimento rápido  
- **Pinia** – gerenciamento de estado (store)  
- **Vue Router** – roteamento SPA  
- **Axios** – cliente HTTP para comunicação com a API  
- **Tailwind CSS** – estilização utilitária (via `@tailwindcss/vite`)  
- **TypeScript** – tipagem estática para maior segurança e manutenibilidade  

### Infraestrutura e Integrações
- **Docker & Docker Compose** – conteinerização e orquestração de todos os serviços  
- **ViaCEP API** – preenchimento automático de endereços a partir do CEP  
- **JWT** – autenticação stateless  
- **RBAC** – controle de acesso baseado em perfis (ADMIN / USER)  

---

## 🚀 Como Rodar o Projeto com Docker (Recomendado)

O projeto está totalmente conteinerizado. Com apenas um comando, toda a infraestrutura (Banco de Dados, API e Frontend) sobe automaticamente:

```bash
docker-compose up -d
```

### Portas dos Serviços

| Serviço          | Endereço                          |
|------------------|-----------------------------------|
| Frontend (Vue 3) | http://localhost:5173             |
| Backend API      | http://localhost:8080             |
| Banco de Dados   | `localhost:5432` (PostgreSQL)     |

---

## 🔄 Fluxo de Funcionamento da API e Autenticação

A arquitetura de comunicação do ecossistema segue este fluxo estruturado:

1. **Autenticação (JWT)**  
   O usuário realiza login/cadastro através do `authenticationService`. O backend (Spring Security + JJWT) valida as credenciais e devolve um token JWT juntamente com os dados de perfil (`role`).

2. **Persistência de Sessão**  
   O Frontend armazena o token e o perfil no `localStorage`. Ao recarregar a página, a store do Pinia (`authStore`) restaura essa sessão automaticamente.

3. **Interceptador HTTP (Axios)**  
   - **Injeção do Token**: Todas as requisições enviadas ao backend incluem automaticamente o cabeçalho `Authorization: Bearer <token>`.  
   - **Sessão Expirada**: Se a API responder com status `401 (Unauthorized)`, o Axios limpa o estado, desloga o usuário e o redireciona para a tela de `/login`.

4. **Gerenciamento de Erros Integrado**  
   Os serviços de API (`userService` e `addressService`) capturam falhas de validação ou de rede e as enviam para a `toastStore`, que exibe a mensagem amigável exata enviada pelo servidor.

5. **Regra de Acesso (RBAC)**  
   Os botões de criar, editar e excluir verificam se o usuário logado possui a role `ADMIN` ou se o ID do recurso corresponde ao ID do usuário autenticado no Pinia.

---

## 🛠️ Como Rodar na Máquina (Sem Docker)

Se preferir executar cada serviço individualmente, siga os passos abaixo.

### 1. 🗄️ Banco de Dados

- Certifique-se de ter o PostgreSQL instalado localmente.
- Crie uma base de dados dedicada para o projeto.
- Configure as credenciais de acesso (usuário, senha, porta e nome do banco) no arquivo `application.properties` do backend.

#### 1.1 H2 (em memória)
1. No arquivo `application.properties` (ou `application.yml`), comente as propriedades do PostgreSQL e descomente as do H2.
2. A configuração do H2 já está disponível nas dependências do Maven (veja o `pom.xml`).
3. O Spring Boot automaticamente criará e populará o esquema em memória ao iniciar a aplicação.

Exemplo de configuração para H2 (em memória):
```properties
spring.datasource.url=jdbc:h2:mem:case_tecnico
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

---

### 2. ⚡ Backend (API - Java/Spring Boot)

1. Abra o terminal na pasta raiz do backend.
2. Compile e instale as dependências com Maven:
   ```bash
   mvn clean install
   ```
3. Configure as variáveis de ambiente no `application.properties` (ex: porta, URL do banco, segredo JWT, etc.).
4. Execute as migrações do banco de dados (se houver scripts SQL).
5. Inicie o servidor Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
   A API estará disponível em `http://localhost:8080` (ou porta configurada).

### 3. 💻 Frontend (Vue 3 + Vite)

1. Abra o terminal na pasta raiz do frontend.
2. Instale as dependências:
   ```bash
   pnpm install
   ```
3. Crie um arquivo `.env` na raiz do frontend e defina a URL da API:
   ```env
   VITE_API_URL=http://localhost:8080
   ```
4. Execute o servidor de desenvolvimento do Vite:
   ```bash
   pnpm run dev
   ```
   O painel estará disponível em `http://localhost:5173`.

