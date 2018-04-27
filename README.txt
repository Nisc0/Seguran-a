-> Seguranca e Confiabilidade - Fase 2
Trabalho realizado por:
Grupo 001
47823 - Francisco Caeiro
47829 - Bruno Andrade
47840 - Diogo Caria Ferreira

//TODO:!!!!!!! porque valta ver como é com os ficheiros

Para executar o programa, apenas, quando na pasta de compilação, executar o comando:
java manUsers.manUsers

Depois da execução do comando, responder às perguntas que surgirão de modo a efetuar as operações de administrador.
Nota: A password do administrador é definida na primeira execução do programa!

De seguida
java client.PhotoShare <userID> <password> <serverAdress:port>

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

Não foi conseguido testar as politicas.