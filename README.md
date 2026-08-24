# API REST de Clientes e Vendas

API RESTful desenvolvida em **Kotlin** com **Spring Boot**, para gerenciamento de cadastro de clientes e registro de vendas, com persistência em **PostgreSQL**.

Projeto desenvolvido como desafio prático de um programa de estágio (Trilha Técnica), com foco em arquitetura em camadas, validação de dados, tratamento global de exceções e consultas customizadas.

## 🚀 Tecnologias

- **Kotlin**
- **Spring Boot**
- **Spring Data JPA** (Hibernate)
- **Bean Validation** (Jakarta Validation)
- **PostgreSQL**
- **Gradle**

## 📁 Estrutura do projeto

```
com.example.demo/
├── controllers/
│   ├── ClienteController.kt
│   └── VendasController.kt
├── entities/
│   ├── Cliente.kt
│   └── Vendas.kt
├── exceptions/
│   └── GlobalException.kt
├── repositories/
│   ├── ClienteRepository.kt
│   └── VendasRepository.kt
├── services/
│   ├── ClienteService.kt
│   └── VendasService.kt
└── DemoApplication.kt
```

A aplicação segue uma arquitetura em camadas:

- **Controller** → recebe as requisições HTTP e devolve as respostas
- **Service** → contém as regras de negócio (validações, verificações, orquestração)
- **Repository** → interface com o banco de dados (via Spring Data JPA)
- **Entity** → representa as tabelas do banco

## ⚙️ Configuração

### Pré-requisitos

- JDK 21+
- PostgreSQL instalado e rodando
- Um banco de dados criado (ex: `CREATE DATABASE postgres;`)

### Configurando o banco de dados

1. Copie o arquivo de exemplo:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
2. Edite `application.properties` com as credenciais do seu banco local:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/SEU_BANCO
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   ```

O Hibernate cria/atualiza as tabelas automaticamente (`ddl-auto=update`) na primeira execução.

### Rodando a aplicação

```bash
./gradlew bootRun
```

A API sobe em `http://localhost:8080`.

## 📌 Endpoints

### Clientes

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/clientes` | Lista todos os clientes |
| `GET` | `/clientes/{id}` | Busca cliente por id |
| `POST` | `/clientes` | Cria um novo cliente |
| `PUT` | `/clientes/{id}` | Atualiza um cliente existente |
| `DELETE` | `/clientes/{id}` | Remove um cliente |
| `GET` | `/clientes/sem-compra` | Lista clientes que nunca realizaram uma compra finalizada |

**Exemplo de corpo para criação (POST/PUT):**
```json
{
    "nome": "Maria Souza",
    "cpfCnpj": "98765432100",
    "tipo": "PF",
    "email": "maria@email.com",
    "cidade": "Vitória",
    "uf": "ES",
    "limiteCredito": 5000.00,
    "ativo": true
}
```

### Vendas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/vendas` | Lista todas as vendas |
| `GET` | `/vendas/{id}` | Busca venda por id |
| `POST` | `/vendas` | Cria uma nova venda |
| `PUT` | `/vendas/{id}` | Atualiza uma venda existente |
| `DELETE` | `/vendas/{id}` | Remove uma venda |

**Exemplo de corpo para criação (POST/PUT):**
```json
{
    "clienteId": 1,
    "dataVenda": "2026-08-23T14:30:00",
    "status": "FINALIZADA",
    "valorTotal": 1500.50
}
```

## ✅ Validações

- Campos obrigatórios validados via Bean Validation (`@NotBlank`, `@NotNull`, `@Email`, `@Pattern`)
- CPF/CNPJ validado por formato (11 ou 14 dígitos numéricos) e checado quanto a duplicidade antes de salvar
- Venda validada quanto à existência do `clienteId` informado antes de ser criada

## ⚠️ Tratamento de erros

A API possui um tratamento global de exceções (`@RestControllerAdvice`), retornando respostas de erro padronizadas em JSON:

```json
{
    "timestamp": "2026-08-23T20:00:00",
    "status": 400,
    "erro": "Erro de validação",
    "mensagem": "Um ou mais campos estão inválidos",
    "detalhes": {
        "nome": "Nome é obrigatório",
        "email": "E-mail inválido"
    }
}
```

| Situação | Status HTTP |
|----------|-------------|
| Erro de validação de campos | `400 Bad Request` |
| Recurso não encontrado (cliente/venda) | `404 Not Found` |
| Conflito de regra de negócio (ex: CPF duplicado) | `409 Conflict` |
| Erro inesperado | `500 Internal Server Error` |

## 📄 Licença

Projeto de estudo, desenvolvido para fins educacionais.
