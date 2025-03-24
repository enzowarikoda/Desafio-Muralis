**Visão Geral**

Este projeto é um sistema para gestão de clientes e seus respectivos contatos, desenvolvido utilizando as seguintes tecnologias:

-Frontend: Vue.js com Vuetify para interface gráfica.
-Backend: Java com Spring Boot.
-Banco de Dados: PostgreSQL.

O sistema permite:

-Gerenciar informações de clientes (nome, CPF, data de nascimento, endereço).
-Gerenciar informações de contatos associados a cada cliente (tipo, valor e observação).
-Buscar, adicionar, editar e deletar clientes e contatos.


**Estrutura do Projeto**

*Frontend*
O frontend está estruturado no framework Vue.js e utiliza Vuetify como biblioteca de componentes.

Principais Componentes:
-App.vue: Estrutura principal do aplicativo, incluindo navegação por abas e toolbar.
-ClientesComponent.vue: Gerencia as funcionalidades relacionadas a clientes, como:
    Listagem de clientes com filtro de pesquisa.
    Formulário para adicionar e editar clientes.
    Ações para gerenciar contatos de um cliente.

*Backend*
O backend foi desenvolvido com Spring Boot, utilizando as seguintes camadas:
-Controller: Gerencia as requisições HTTP e direciona para os serviços apropriados.
-Service: Contém a lógica de negócio.
-Repository: Interage diretamente com o banco de dados.

Principais Endpoints:
/api/clientes:
  GET: Retorna todos os clientes.
  POST: Adiciona um novo cliente.
  PUT: Atualiza um cliente existente.
  DELETE: Remove um cliente.

/api/contatos:
  GET /cliente/{id}: Retorna os contatos de um cliente.
  POST /cliente/{id}: Adiciona um contato a um cliente.
  PUT: Atualiza um contato existente.
  DELETE: Remove um contato.

*Banco de Dados*
Estrutura do banco de dados PostgreSQL:
-Tabela clientes:
  id: Identificador único (chave primária).
  nome: Nome do cliente.
  cpf: CPF do cliente (valor único).
  data_nasc: Data de nascimento.
  endereco: Endereço do cliente.

-Tabela contatos:
  id: Identificador único (chave primária).
  tipo_contato: Tipo do contato (Telefone ou Email).
  valor_contato: Valor do contato.
  observacao: Observações adicionais sobre o contato.
  cliente_id: Chave estrangeira referenciando a tabela clientes.

Script para criação do banco de dados e das tabelas:
  -- Criação do banco de dados 'agenda'
  CREATE DATABASE agenda
  
  -- Criação da tabela 'clientes'
  CREATE TABLE clientes (
      id SERIAL PRIMARY KEY,
      nome VARCHAR(100) NOT NULL,
      cpf VARCHAR(15) NOT NULL UNIQUE,
      data_nasc DATE,
      endereco TEXT
  );
  
  -- Criação da tabela 'contatos'
  CREATE TABLE contatos (
      id SERIAL PRIMARY KEY,
      tipo_contato VARCHAR(20) NOT NULL,
      valor_contato VARCHAR(100) NOT NULL,
      observacao TEXT,
      cliente_id INT,
      FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
  );

  **Configurações**

*Frontend*
Dependências Principais:
-vue: Framework principal.
-vuetify: Biblioteca de componentes UI.

Instruções para executar o frontend:
-Instale as dependências do projeto:
    npm install
-Inicie o servidor de desenvolvimento:
    npm run serve
Acesse o sistema em http://localhost:8080.

**Backend**

Dependências Principais:
-spring-boot-starter-data-jpa: Para interação com o banco de dados.
-spring-boot-starter-web: Para criação de APIs REST.
-postgresql: Driver para conexão com o PostgreSQL.

Configuração do application.properties:
    # Configurações do PostgreSQL
    spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

Instruções para executar o backend:
-Certifique-se de que o PostgreSQL está rodando e configurado corretamente.
-Compile o projeto com o Maven:
    mvn clean install
-Execute a aplicação:
    mvn clean install
-Acesse a API em http://localhost:8081.


**Instruções de Uso**

-Inicie o backend para que a API REST esteja funcionando.
-Inicie o frontend para acessar a interface gráfica.
-Use a aba "Clientes" para:
    Adicionar, editar e excluir clientes.
    Gerenciar contatos associados a cada cliente.
-Utilize o campo de busca para localizar clientes por nome, CPF, data de nascimento ou endereço.
-Na tela de contatos, adicione ou remova contatos para o cliente selecionado.
