# RestTree

Exemplo de uma aplicação em árvore com interface Rest, utilizando Spring Boot e Java 8.
<br><br>

<b>Configuração:</b>

O banco de dados usado é o MySql. Dados já configurados:<br>
Username: root<br>
Password: root<br>
Url: jdbc:mysql://localhost/restree<br>

Para gerar o <code>.war</code> é necessário ter o Maven instalado e executar o comando: <code>mvn package</code>.
<br><br>
<b>Utilização:</b>

- Adicionar nó principal<br>
<code>curl -H "Content-Type: application/json" -X POST -d '{"code":"A","description":"A","detail":"A"}' http://localhost:8080/RestTree/node</code><br>

- Adicionar nó filho<br>
<code>curl -H "Content-Type: application/json" -X POST -d '{"code":"B","description":"B","detail":"B", "parentId": 1}' http://localhost:8080/RestTree/node</code><br>

- Atualizar nó<br>
<code>curl -H "Content-Type: application/json" -X PUT -d '{"code":"BBB","description":"BBB","detail":"BBB", "parentId": 1, "id": 2}' http://localhost:8080/RestTree/node</code><br>
    
- Consultar toda a estrutura<br>
<code>curl -X GET http://localhost:8080/RestTree/node</code><br>

- Consultar somente os nós filhos de um nó<br>
<code>curl -X GET http://localhost:8080/RestTree/node/1</code><br>

- Deletar um nó(todos os nós abaixo dele serão deletados também)<br>
<code>curl -X DELETE http://localhost:8080/RestTree/node/1</code><br>

<br><br><br><br>
