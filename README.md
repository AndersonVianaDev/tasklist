# Task List

# Sobre o projeto

A Task List é uma API para gerenciar listas de tarefas, onde os usuários podem criar,
atualizar e remover suas tarefas conforme suas necessidades. Para garantir a
segurança e autenticação dos usuários, a aplicação utiliza o Spring Security em
conjunto com JSON Web Tokens (JWT)

## Tecnologias usadas
- Java
- Spring Boot
- Spring Security
- PostgreSQL
- Gradle
- Docker
- JWT (JSON Web Token)
- JPA / Hibernate

# Instalação do Projeto
## Pré requisito: Java 17 e Docker Desktop
1. Clone o projeto
```bash
  git clone git@github.com:AndersonVianaDev/tasklist.git
```
2. Instalar as dependências com o gradle
3. Instalar o Docker
4. Iniciar o database com o docker
```bash
  docker compose up
```
#Documentação de Usuário
```bash
  # Criar usuário
  POST localhost:8080/api/v1/users/register
  {
    "name": "xxxxxxx",
    "email": "xxxxxx@xxxx.xxx",
    "password": "xxxxxxxx" 
  }

  # Logar usuário
  POST localhost:8080/api/v1/users/login
  {
    "email": "xxxxxx@xxxx.xxx",
    "password": "xxxxxxxx" 
  }
  return "token"

  # Atualizar senha
  PATCH localhost:8080/api/v1/users/update/id/{id}
  {
    "oldPassword": "xxxxxxxxx",
    "newPassword": "xxxxxxxxxxxx" 
  }
```

#Documentação de Tarefa
```bash
  # Criar Tarefa
  POST localhost:8080/api/v1/tasks/create/id/{idUser}
  {
    "name": "xxxxxxx",
    "concluded": true || false,
    "expirationDate": "yyyy-mm-dd" 
  }

  # Pegar uma tarefa por Id
  GET localhost:8080/api/v1/tasks/find/id/{id}
    return task

  # Atualizar a tarefa
  PATCH localhost:8080/api/v1/tasks/update/id/{id}
  {
    "concluded": false || true
  }
```

# Autor do projeto
Anderson Palmerim Viana
# Contato 
[![Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/anderson-palmerim-6a5a17262/)
