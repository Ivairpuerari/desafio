# Resolução do Desafio Técnico: API Transaction Manager

O projeto **API Transaction Manager** foi desenvolvido como parte do desafio técnico para o processo seletivo da Caju. A API disponibiliza um recurso de autorização de transação através de uma interface RESTful.

## Tecnologias e Ferramentas Utilizadas

- **Linguagem e Framework:** Java com Spring Boot
- **Banco de Dados:** MySQL
- **Gestão de Banco de Dados:** Flyway para versionamento e gerenciamento das migrações de banco de dados
- **Arquitetura e Design:** 
  - **Clean Architecture:** Para garantir a separação clara entre domínios e pastas, promovendo uma estrutura de código bem organizada e de fácil manutenção.
  - **SOLID:** Aplicado para o desenvolvimento dos casos de uso, garantindo que a aplicação seja robusta e mantenha boas práticas de design orientado a objetos.
- **Testes:** 
  - **TDD (Test-Driven Development):** Para garantir a qualidade e funcionalidade do código, com a utilização das bibliotecas JUnit e Mockito para testes unitários.
- **Documentação:** 
  - **OpenAPI e Swagger:** Para fornecer uma documentação clara e acessível da API, facilitando a integração e o entendimento das funcionalidades disponíveis.
- **Gerenciamento de Conflitos de Transação:** 
  - **Optimistic Locking:** Abordagem utilizada para lidar com transações simultâneas e evitar conflitos na atualização dos dados.

## Documentação da Aplicação

Para acessar a documentação da API, visite: [http://localhost:8080/api-docs.html](http://localhost:8080/api-docs.html)

## Desafios Resolvidos

Todos os desafios descritos na especificação do projeto foram resolvidos, incluindo os níveis L1, L2, L3 e L4, garantindo a entrega de uma solução completa e funcional.

# Como Executar a API Transaction Manager

Para executar a API Transaction Manager, siga os passos abaixo:

## Pré-requisitos
1. **Java Development Kit (JDK) 21**: Certifique-se de que o JDK 21 está instalado em seu sistema. Você pode verificar a instalação com o comando:
   ```bash
   java -version

2. **Configure o Banco de Dados**: Edite o arquivo de configuração application.properties ou application.yml (localizado em src/main/resources) para definir as configurações de conexão com o banco de dados MySQL. 
   ```bash
    spring.datasource.url=jdbc:mysql://localhost:3306/transaction_db
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha

    <plugin>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-maven-plugin</artifactId>
      <version>6.5.5</version>
      <configuration>
        <url>jdbc:mysql://localhost:3306/transaction_db</url>
        <user>root</user>
        <password>admin</password>
      </configuration>
	</plugin>

2. **Execute a Aplicação**. 
   ```bash
    ./mvnw spring-boot:run