# Desafio Quality (Grupo 11) 🪢

Repositório contendo a solução do desafio do modulo de qualidade, com foco em testes automatizados.

## 👨‍💻 Participantes

- [Carlos Feldmann 🦥](https://github.com/CarlosFeldmann)
- [Giovanna Polissici 👺](https://github.com/Giovannapls)
- [Luiz Mariz 👻](https://github.com/lmarizmeli)  
- [Victor Planas 💪](https://github.com/Victor-Planas) 

---
## 📝 Requisitos técnicos funcionais

Trabalho realizado de acordo com os requisitos técnicos encontrados que podem ser encontrados no seguinte documento [Requisitos técnicos funcionais.pdf](docs%2FRequisitos%20t%C3%A9cnicos%20funcionais.pdf), também foi levado em considerações os itens revisados pelo professor:

>* Criar um endpoint para cada história de usuário;
>* Efetuar o match entre os bairros cadastrados por meio do nome ou Id, fazendo as devidas adaptações nos testes;
>* É necessário disponibilizar um endpoint ou arquivo data.sql para carga inicial dos bairros;
>* Existe um atributo relacionado ao preço do m2 no payload dos imóveis, este preço já está presente no repositório de bairros, ou seja, o atributo existente no payload é dispensável.

---
## 🧰 Ferramentas Utilizadas

Utilizando as seguintes ferramentas/dependências no projeto:  
* ***Java 11***
* Spring Boot
* Spring Data JPA com banco de dados em memória H2
* [springdoc-openapi](https://springdoc.org/)
* JUnit 5
* JaCoCo

Carga inicial do banco de dados com o arquivo `data.sql`, inserindo os bairros aceitos na aplicação.

---
## 🗄️ Organização de arquivos

* `entities` - Entidades do banco de dados JPA;
* `services` - Serviços relacionados à aplicação;
* `controllers` - Controladores relacionados à aplicação;
* `exceptions` - Exceções relacionadas à aplicação;
* `payloads` - Objetos associados às entradas de dados;
* `responses` - Objetos associados às saídas de dados;
* `repositories` - Serviços para abstração do acesso aos dados;
* `config` - Classes de configuração relacionadas ao framework;---
* `test/unit` - Testes unitários;
* `test/integration` - Testes de integração;

---
## 💥 Rodando a aplicação
> ***É necessário o uso do Java 11***

 ```bash
 # Clone este repositório
 git clone https://github.com/Victor-Planas/desafio_quality.git

 # Acesse a pasta do projeto no terminal/IDE de sua preferencia (bash/cmd):
 $ cd desafio_quality

# Rode a API
$ mvn spring-boot:run
```
Após rodar a aplicação é possível acessar a documentação através do link: http://localhost:8080/v1/swagger-ui.html

### 🏘️ Bairros aceitos no payload:
* `"Vila Pauliceia"`
* `"Bairro do limao"`

---

## 🧪 Testes
* Unitários [`src/test/java/br/com/meli/bootcamp/wave2/quality/unit`](src/test/java/br/com/meli/bootcamp/wave2/quality/unit)
* Integração [`src/test/java/br/com/meli/bootcamp/wave2/quality/integration`](src/test/java/br/com/meli/bootcamp/wave2/quality/integration)

Para rodar os testes unitários basta rodar o seguinte comando `mvn clean test`
### 📊 Coverage
Para gerar a cobertura dos testes basta rodar o comando `mvn clean verify`, com ele será gerado uma pagina web em `target/site/jacoco/index.html` com o coverage do projeto.  
De modo a facilitar, também pode ser visualizada em: [Github pages](https://victor-planas.github.io/desafio_quality/jacoco/index.html)


## 📃 Documentação
Documentação criada utilizando o springdoc-openapi, a interface gráfica pode ser acessada das seguintes formas:
* [Github pages](https://victor-planas.github.io/desafio_quality/swagger/index.html) - Somente funciona para visualização
* http://localhost:8080/v1/swagger-ui.html - Funciona para testar a aplicação localmente.