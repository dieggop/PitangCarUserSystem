# API RESTful para Sistema de Usuários de Carros

Criar aplicação que exponha uma API RESTful de criação de usuários e carros com login.

### ESTÓRIAS DE USUÁRIO
* CRIAÇÃO DE USUÁRIOS COM OU SEM CARROS
* LISTAR USUÁRIOS REGISTRADOS NO SISTEMA
* BUSCAR USUÁRIO POR ID
* REMOVER USUÁRIO POR ID
* ATUALIZAR USUÁRIO POR ID


* RETORNAR DADOS DE USUÁRIO LOGADO


* REALIZAÇÃO DE LOGIN
* LISTAR TODOS OS CARROS DO USUÁRIO LOGADO
* CADASTRAR UM NOVO CARRO PARA O USUÁRIO LOGADO
* REMOVER UM CARRO DO USUÁRIO LOGADO PELO ID
* BUSCAR UM CARRO DO USUÁRIO LOGADO PELO ID 
* ATUALIZAR UM CARRO DO USUÁRIO LOGADO PELO ID

# SOLUÇÃO
** Aplicação em spring boot, usando jpa e hibernate tendo como banco em memória H2(SqLIte), seguindo abordagem de "contract-first" com Swagger definido e apontado no POM. O código é gerado a cada build e a implementação da API usa a interface e o modelo gerado automaticamente a cada Build da aplicação.
- Na pasta do projeto, digite: mvn clean install, para instalação das dependências e criação das classes de interface e modelos
  
- Para executar, basta digitar java -jar  CarUserSystem-0.0.1-SNAPSHOT.jar, na pasta target.
- Documentação SWAGGER para uso da API encontra-se no LINK: https://app.swaggerhub.com/apis/dieggop/CarUserSystem/1.0.0

## Sistema criado em Spring Boot, utilizando das seguintes tecnologias

* SpringBoot
* Spring Security
* GSON
* Swagger
* JPA
* Hibernate
* H2

As Dependências são instaladas utilizando o gerenciador MAVEN

Para este projeto foi utilizado o framwork spring boot, pois já deixar tudo configurado, para que o foco ficasse na regra de negócio.
Para os contratos, utilizei o Swagger, para modelar a API, gerar uma documentação mais rica e gerar os contratos para os controllers.
Para que não ficasse poluido o projeto, no POM foi inserido algumas tags para excluírem os controllers pre-gerados, de modo que permanecessem 
apenas as interfaces.

Para banco de dados, utilizei o ORM natural do spring Hibernate juntamente com o JPA para mapeamento das classes e por fim
as tabelas foram criadas baseando no mapeamento configurado nas entidades.

