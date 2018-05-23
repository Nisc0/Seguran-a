-> Seguranca e Confiabilidade - Fase 2
Trabalho realizado por:
Grupo 001
47823 - Francisco Caeiro
47829 - Bruno Andrade
47840 - Diogo Caria Ferreira

Para executar o programa, quando na pasta do projeto (PhotoShare_001), executar o comando:
./gradlew manUsers
Depois da execução do comando, responder às perguntas que surgirão de modo a efetuar as operações de administrador.
Nota: A password do administrador é definida na primeira execução do programa!

Para, de seguida, ligar o servidor, usar o comando:
./gradlew server
Nota: Como pretendido, a porta do servidor é a 23232!

Em seguida, alterar o ficheiro build.gradle e substituir:
<tasknameClient> - pelo nome de execução
<user> - pelo nome de cliente
<pass> - pela password do cliente
<ip:port> - pelo ip e a porta respetiva
Nota: Para a execução de mais do que cliente, copiar a task e fornecer nomes diferentes dos selecionados na task anterior.

Para efeitos de exemplo já se encontra um cliente preparado a exectar, na mesma máquina que o servidor, através do comando:
./gradlew client
Nota: Este cliente tem de username user, e de password pass, como tal, tem de ser adicionado no manUsers, se se desejar utilizar-lo.


Após este comando, o utilizador executou o seu login.
Pode agora utilizar o programa PhotoShare.
Tendo a possibilidade de executar múltiplos comandos:

-a <photos> - Adiciona fotos dadas para o servidor;

-l <userID> - Se o utilizador dado fizer parte dos seguidores do utilizador atual, lista as fotografias do utilizador
dado, com o nome da foto e a sua data de publicacao;

-i <userID> <photo> - Se o utilizador dado fizer parte dos seguidores do utilizador atual, devolve os comentarios e o
numero de likes e dislikes da foto dada;

-g <userID> - Se o utilizador dado fizer parte dos seguidores do utilizador atual, copia do servidor para o cliente
todas as fotos do utilizador dado;

-c <comment> <userID> <photo> - Se o utilizador dado fizer parte dos seguidores do utilizador atual, adiciona um
comentario ah foto dada do utilizador dado;

-L <userID> <photo> - Se o utilizador dado fizer parte dos seguidores do utilizador atual,
adiciona um like à fotografia photo do utilizador userId; caso contrário, devolve um erro;

-D <userId> <photo> - se o utilizador local fizer parte dos seguidores de userId, adiciona
um dislike à fotografia photo do utilizador userId; caso contrário, devolve um erro;

-f <followUserIds> - adiciona os utilizadores followUserIds como seguidores do utilizador
local. Se algum dos utilizadores já fizer parte da lista de seguidores deve ser devolvido
um erro.

-r <followUserIds> - remove os utilizadores followUserIds como seguidores do utilizador
local. Se algum dos utilizadores não fizer parte da lista de seguidores deve ser devolvido
um erro.
