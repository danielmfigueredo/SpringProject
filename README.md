# Sistema Eleitoral

Esta é uma aplicação que simula um sistema eleitoral. A aplicação permite gerenciar sessões eleitorais, candidatos e eleitores. Abaixo estão descritas as funcionalidades principais do sistema:

## Funcionalidades

- **CRUD de Eleitor**: Adicionar, atualizar, visualizar e remover eleitores.
- **CRUD de Candidato**: Adicionar, atualizar, visualizar e remover candidatos.
- **CRUD de Cargo**: Adicionar, atualizar, visualizar e remover cargos.
- **Criar Sessão**: Criar uma nova sessão eleitoral recebendo um cargo.
- **Adicionar Candidato à Sessão**: Adicionar candidatos a uma sessão, passando os IDs dos candidatos.
- **Computar Voto**: Permitir que um eleitor vote em um candidato em uma sessão, garantindo que o eleitor não vote mais de uma vez.
- **Fechar Sessão**: Fechar uma sessão eleitoral para impedir novos votos.
- **Gerar Relatório das Sessões Fechadas**: Gerar um relatório das sessões que foram fechadas.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.3**
- **Spring Framework**
- **Spring Data JPA**
- **Spring Web**
- **Springdoc 2.0.2**: Biblioteca utilizada para integração com Swagger para documentação da API.
- **Maven**: Gerenciador de build utilizado para gerenciamento de dependências e construção do projeto.
- **MySQL**: Banco de dados utilizado para o gerenciamento e armazenamento dos dados.

## Configuração do Projeto

1. **Importar Projeto**:
   - Certifique-se de que o Java 17 e o Maven estão instalados.
   **Clone o Repositório**
   ```bash
   git clone <URL-do-repositório>

2. **Configuração do Banco de Dados**:
   - Configure a conexão com o banco de dados no arquivo `application.properties` ou `application.yml`.
   - Neste projeto, foi utilizada uma instância do MySQL para o gerenciamento e armazenamento dos dados.

3. **Inicialização do Projeto**:
   - Execute a aplicação usando o comando `mvn spring-boot:run` ou `./gradlew bootRun`, dependendo do seu gerenciador de build.

4. **Acessar a Documentação da API**:
   - A documentação da API pode ser acessada em `http://localhost:8080/swagger-ui.html`.

## Exemplos de Uso

- **Criar Sessão**:
  - Enviar uma requisição POST para `/sessoes/abrir/{cargoId}` com o cargo no corpo da requisição.
  
- **Adicionar Candidato à Sessão**:
  - Enviar uma requisição POST para `/sessoes/{sessaoId}/candidatos/{candidatoId}` com os IDs do candidato e da sessão.

- **Computar Voto**:
  - Enviar uma requisição POST para `/sessoes/{id}/votar` com o ID do candidato e o ID do eleitor.

- **Fechar Sessão**:
  - Enviar uma requisição POST para `/sessoes/{id}/fechar`.

- **Gerar Relatório das Sessões Fechadas**:
  - Enviar uma requisição GET para `/sessoes/{sessaoId}/votos`.

---

Para mais detalhes sobre a implementação, consulte o código fonte e a documentação gerada pelo Swagger.

