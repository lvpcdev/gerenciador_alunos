# Gerenciador de Alunos — Backend

API REST para gerenciamento de alunos de uma escola de informática. Desenvolvido como projeto real para um cliente, com funcionalidades de controle de matrículas, registro de aulas, geração de contratos em PDF e fichas de anotações em XLSX.

## Tecnologias

- Java 21
- Spring Boot 3.2
- Spring Security + JWT
- PostgreSQL
- Apache POI (geração de XLSX e DOCX)
- iText PDF
- Maven

## Funcionalidades

- Cadastro e gerenciamento de alunos com soft delete (inativação)
- Cadastro de cursos com categorias (Básico / Avançado)
- Contratos de matrícula com modalidades de curso configuráveis
- Registro de aulas com controle de presença
- Relatório de presenças por período em PDF
- Geração de contrato de matrícula em PDF a partir de template DOCX
- Geração de ficha de anotações do aluno em XLSX com preenchimento automático
- Módulo de biblioteca com controle de empréstimos e devolução automática agendada
- Autenticação com JWT e controle de acesso por perfil (ADMIN / USER)
- Tratamento global de erros com mensagens amigáveis

## Arquitetura

```
controller  →  service  →  repository  →  banco de dados
                ↓
           DTOs + validações (Bean Validation)
```

Separação em camadas com injeção de dependência por construtor em todos os componentes.

## Variáveis de Ambiente

O projeto usa variáveis de ambiente para todas as configurações sensíveis:

| Variável | Descrição |
|---|---|
| `DB_URL` | URL de conexão com o PostgreSQL |
| `DB_USERNAME` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Chave secreta para assinatura do JWT |

## Como rodar localmente

**Pré-requisitos:** Java 21, PostgreSQL, Maven

```bash
# Clone o repositório
git clone https://github.com/lvpcdev/gerenciador_alunos.git
cd gerenciador_alunos

# Configure as variáveis de ambiente
export DB_URL=jdbc:postgresql://localhost:5432/gerenciador_alunos
export DB_USERNAME=seu_usuario
export DB_PASSWORD=sua_senha
export JWT_SECRET=sua_chave_secreta

# Compile e rode
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints principais

| Método | Rota | Descrição |
|---|---|---|
| POST | `/auth/login` | Autenticação |
| GET/POST | `/alunos` | Listar e cadastrar alunos |
| PUT | `/alunos/{id}` | Atualizar aluno |
| DELETE | `/alunos/{id}` | Inativar aluno |
| GET/POST | `/cursos` | Listar e cadastrar cursos |
| POST | `/contratos` | Criar contrato de matrícula |
| GET | `/contratos/aluno/{id}` | Contratos de um aluno |
| POST | `/registros` | Registrar aula |
| GET | `/relatorios/presencas/{id}` | Relatório de presenças em PDF |
| GET | `/relatorios/contrato/{id}` | Contrato em PDF |
| GET | `/relatorios/ficha/{id}` | Ficha de anotações em XLSX |
| GET/POST | `/usuarios` | Gerenciar usuários (ADMIN) |

## Deploy

O projeto é empacotado como `.jar` via `mvn package` e executado como serviço systemd em um VPS com PostgreSQL e LibreOffice instalados (necessário para conversão de DOCX para PDF).