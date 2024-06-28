# Card Generator
Projeto por Guilherme Macedo Gara Tavares\
Criado para a conclusão da sistematização (Projeto A) da matéria de Programação Orientada a Objetos do segundo semestre do curso de Análise e Desenvolvimento de Sistemas EAD - CEUB.

## Requisitos
- Banco de dados MYSQL instalado, com acesso privilégios de administrador do mesmo.
- Java 17

## Como instalar e executar
- Clone o projeto localmente
- Execute o banco de dados MYSQL
- Abra a pasta do projeto e edite o arquivo src\main\resources\application.properties
- Altere os seguintes campos:
  - spring.datasource.username=<nome_do_usuario> (insira o nome do usuário do banco)
  - spring.datasource.password=<senha_do_usuario> (insira a senha do usuário do banco)
- Opcionalmente, é possível alterar o campo spring.datasource.url para utilizar um schema já existente (Ex: jdbc:mysql://localhost:3306/<nome_schema>), mas ele automaticamente deverá criar um schema por padrão
- Acesse a pasta do projeto utilizando um terminal (como o Prompt de Comando do Windows ou shell do Linux)
- Digite os seguintes comandos:
  - Para LINUX:
    - ./mvnw install
    - ./mvnw spring-boot:run
  - Para WINDOWS:
    - mvnw.cmd install
    - mvnw.cmd spring-boot:run
- Com isso feito, o projeto estará em execução em http://localhost:8080

## O que tem no projeto
O projeto conta com endpoints para realizar o CRUD completo de funcionários, cargos, telefones, alergias e problemas de saúde.\
Para acessar a documentação completa dos endpoints, execute o projeto e acesse http://localhost:8080
