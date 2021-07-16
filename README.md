# Desafio Quality (Grupo 11) 🪢

Repositório contendo a solução do desáfio do modulo de qualidade, com foco em testes automatizados.

## 👨‍💻 Participantes

- [Carlos Feldmann 🦥](https://github.com/CarlosFeldmann)
- [Giovanna Polissici 👺](https://github.com/Giovannapls)
- [Luiz Mariz 👻](https://github.com/lmarizmeli)  
- [Victor Planas 💪](https://github.com/Victor-Planas) 


---
## 📝 Requisitos ténicos funcionais

Trabalho realizado de acordo com os requisitos técnicos encontrados que podem ser encontrados no seguinte documento [Requisitos técnicos funcionais.pdf](docs%2FRequisitos%20t%C3%A9cnicos%20funcionais.pdf), também foi levado em considerações os itens revisados pelo professor:
* Criar um endpoint para cada história de usuário;
* Efetuar o match entre os bairros cadastrados por meio do nome ou Id, fazendo as devidas adaptações nos testes;
* É necessário disponibilizar um endpoint ou arquivo data.sql para carga inicial dos bairros;
* Existe um atributo relacionado ao preço do m2 no payload dos imóveis, este preço já está presente no repositório de bairros, ou seja, o atributo existente no payload é dispensável.

---
## 🧰 Ferramentas Utilizadas

Utilizando as seguintes ferramentas/dependencias no projeto:  
* ***Java 11***
* Spring Boot
* Spring Data JPA com banco de dados em memória H2
* Springdoc
* JUnit 5
* JaCoCo

Carga inicial do banco de dados com o arquivo `data.sql`, inserindo os bairros que são aceitos na aplicação.

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

### Bairros aceitos no payload:
* `"Vila Pauliceia"`
* `"Bairro do limao"`


## Testes unitários
TODO: Falta escrever esta parte, testes já finalizados!

## 📃 Documentação
Documentação criada utilizando o springdoc, a interface gráfica pode ser acessada das seguintes formas:
- [Github pages](https://lmarizmeli.github.io/social-meli/index.html) - Somente funciona para visualização
- http://localhost:8080/v1/swagger-ui.html - Funciona para testar a aplicação localmente.