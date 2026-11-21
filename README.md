# Login System API

API REST desenvolvida em **Java com Spring Boot** para prática e aprofundamento nos estudos de desenvolvimento backend, autenticação, segurança e boas práticas de arquitetura.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot** (WebMVC, Data JPA, Security, Validation)
- **PostgreSQL**
- **Lombok**
- **Maven**

## Funcionalidades Implementadas

- Cadastro de usuários com criptografia de senha (`BCryptPasswordEncoder`)
- Atualização completa de dados cadastrais (`PUT`)
- Atualização parcial de dados cadastrais (`PATCH`)
- Alteração de senha com validação da senha atual
- Ativação e desativação de usuários
- Validação de entrada com Jakarta Validation (`@Valid`) e DTOs em `record`
- Exceções personalizadas de domínio (`UsuarioNaoEncontradoException`, `SenhaIncorretaException`)

## Endpoints

Base URL: `/api/v1/usuarios`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/v1/usuarios` | Registra um novo usuário |
| `PUT` | `/api/v1/usuarios/{id}/atualizar` | Atualiza todos os dados cadastrais do usuário |
| `PATCH` | `/api/v1/usuarios/{id}` | Atualiza parcialmente os dados do usuário |
| `PATCH` | `/api/v1/usuarios/{id}/senha` | Altera a senha do usuário |
| `PATCH` | `/api/v1/usuarios/{id}/ativar` | Ativa o usuário |
| `PATCH` | `/api/v1/usuarios/{id}/desativar` | Desativa o usuário |

## Como Executar o Projeto

### Pré-requisitos
- Java 21 instalado
- PostgreSQL em execução

### 1. Configurar variáveis de ambiente
Crie um arquivo `.env` na raiz do projeto com base no `.env_example`:

```env
URL_DB=jdbc:postgresql://localhost:5432/nome_do_banco
USERNAME_DB=seu_usuario
PASSWORD_DB=sua_senha
```

Carregue as variáveis no terminal (Linux/macOS):

```bash
set -a && source .env && set +a
```

### 2. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

## Fluxo da Aplicação

### Arquitetura e Fluxo Geral de uma Requisição

```mermaid
flowchart LR
    C["Cliente (HTTP)"] --> CTRL["UsuarioController (@Valid + DTO)"]
    CTRL --> SVC["UsuarioService (@Transactional)"]
    SVC --> ENC["PasswordEncoder (BCrypt)"]
    SVC --> ENT["Usuario (Regras de Dominio)"]
    SVC --> REPO["UsuarioRepository (JPA)"]
    REPO --> DB[("PostgreSQL")]
```

### Fluxo de Alteração de Senha (`PATCH /api/v1/usuarios/{id}/senha`)

```mermaid
sequenceDiagram
    actor Cliente
    participant Controller as UsuarioController
    participant Service as UsuarioService
    participant Repo as UsuarioRepository
    participant Encoder as PasswordEncoder

    Cliente->>Controller: PATCH /api/v1/usuarios/{id}/senha
    Controller->>Service: alterarSenha(id, request)
    Service->>Repo: findById(id)
    alt Usuário não encontrado
        Repo-->>Service: Optional.empty()
        Service-->>Cliente: UsuarioNaoEncontradoException
    else Usuário encontrado
        Repo-->>Service: Usuario
        Service->>Encoder: matches(senhaAtual, usuario.getSenha())
        alt Senha atual incorreta
            Encoder-->>Service: false
            Service-->>Cliente: SenhaIncorretaException
        else Senha atual válida
            Encoder-->>Service: true
            Service->>Encoder: encode(senhaNova)
            Encoder-->>Service: novaSenhaCriptografada
            Service->>Service: usuario.alterarSenha(novaSenhaCriptografada)
            Service-->>Controller: void (commit automático @Transactional)
            Controller-->>Cliente: 204 No Content
        end
    end
```

## Estrutura do Projeto

```text
src/main/java/br/com/victorzendem/login/
├── controller/   # Endpoints REST
├── dto/          # Records de entrada (Request) e saída (Response)
├── enums/        # StatusUsuario e TipoUsuario
├── exceptions/   # Exceções personalizadas
├── model/        # Entidades JPA
├── repository/   # Interfaces Spring Data JPA
├── security/     # Configurações de segurança e PasswordEncoder
└── service/      # Regras de negócio
```
