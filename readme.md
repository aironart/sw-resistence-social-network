# Star Wars Resistence Social Network

## Sumario
* [Requisitos](#requisitos)
* [Tecnologias Utilizadas](#tecnologias-utilizadas)
* [Camadas de Desenvolvimento](#camadas-de-desenvolvimento)
* [Swagger](#swagger)
* [Executando o projeto](#executando-o-projeto)

### Requisitos

Este projeto foi desenvolvido em cima dos requisitos contidos no arquivo [Prova.pdf](file://docs/prova1_Java.pdf)

### Tecnologias Utilizadas

* Java
* Spring Boot
* Spring Data
* Hibernate e JPA
* Flyway (Gestão de modificações do Banco de dados)
* Maven
* Swagger
* jUnit
* Banco de dados H2 em memória

### Camadas de desenvolvimento

* config - configurações para o Spring
* controller - disponibilização dos endpoints para consumo
* domain - entidades que representam tabelas no banco de dados
* enums - enumerações utilizadas no projeto
* exceptions - exceções customizadas referente a negócio
* factory - classes auxiliares de conversão das propriedades vindas dos endpoints para entidades
* report - classes auxiliares para os relatórios
* repository - comunicação com o banco de dados
* request - classes que representam os corpos das requisições
* service - classes de serviço, responsáveis pela regra de negócio no processamento das requisições
* validator - classes responsáveis para fazer as validações das requisições

### Swagger

Para visualizar as documentações dos endpoints criados basta acessar o swagger na seguinte url após executar o projeto:

[http://localhost:8080/swagger-ui/]()

### Executando o projeto

1 - Compilar o projeto execute o seguinte comando:

```shell
mvn clean package 
```

2 - Executar os comandos abaixo na pasta **target**:

```shell
java -jar target/sw-resistence-social-network-0.0.1-SNAPSHOT.jar 
```