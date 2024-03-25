# SR03 Devoir 1

 Le but de ce devoir est de créer une application de chat entre différents clients via un serveur, des sockets et des threads. Les sockets permettent d'échanger des informations entre un serveur et un client, or, pour gérer plusieurs clients en même temps, il faut utiliser des threads. Un thread correspond à un client, il y a donc autant de threads créés que de clients dans le chat. Cette application permet à différents utilisateurs de se connecter à un serveur pour pouvoir discuter. 
L'application fonctionne comme suit : inserer schéma

Le projet est constitué de 5 classes : Serveur, Client, ClientHandlerThread, ClientReceiveMessageThread et ClientSendMessageThread. 

## ClientHandlerThread

La classe ClientHandlerThread est utilisée pour gérer la communication entre le serveur et un client.Dans cette classe, elle stocke les valeurs des attributs "pseudo":le pseudo d'un client ,"clientSocket":le socket de communication ,"inputStream":un flux utilisé pour lire les données,"outputStream":un flux utilisé pour envoyer les messages à client et clientsPseudoThreadMap: un hashMape qui content les coupes de pseudo et thread de tous les clients.
Dans le constructeur de cette classe, elle affiche "Entrez votre pseudo,svp :" à l'écran et sauvegarde l'entrée du client comme la valeur du pseudo.
Dans la méthode 'run',une boucle est utilisée pour continuer à lire les messages provenant du client après avoir avoir vérifié que ce client n’a pas encore quitt.À Chaque fois que le inputStream lit un message, il vérifie si ce message est 'exit'.Si c’est le cas, il envoie un message "L'utilisateur"+pseudo+"quitte" à tous les autres clients en parcourant la HashMap qui contient tous les objets de ClientHandlerThread et en appelant leurs methodes getOutputStream.Sinon, il envoie un message pseudo+"dit"+message à tous les autres utilisateurs.

## Serveur
La classe Serveur crée un socket de connexion avce le numéro de port 10800, et attend les connexions des clients avec accept(). À Chaque fois qu'une connexions est créée, un socket de communication est également crée.Et un objet de ClientHandlerThread est instancié avec le socket de commnication en tant que paramètre.
Dans cette classe, pendnat chaque itération, on parcourt une fois la HashMap qui contient les pseudo et les threads de tous les clients pour supprimer les threads arrêtés(correspondant aux clients qui ont quitté).De plus,chaque fois qu'un client fournit son pseudo, on parcourt la liste de pseudo pour vérifier si ce pseudo existe déjà.Enfin, on met à jour la HashMap de chaque childHandlerthread dans chaque itération pour que ils puissent correctement connaître les autres clients auxquels ils doivent envoyer des messages.

## Client
La classe client possède les attributs privés commSocket, receiveMessageThread et sendMessageThread. Elle permet de créer la connexion avec le serveur via commSocket et instancie et "start" les deux threads.

## ClientReceiveMessageThread
Cette classe possède les attributs privés commSocket, input de type InputStream, dataInputStream et un booléen exit.
Le constructeur permet d'instancier la socket, input et dataInputStream. La méthode run effectue une boucle tant que exit est faux qui lit le message envoyé par le client via le thread ClientSendMessageThread et le retourne dans la console. Lorsqu'exit est vrai, on ferme input et dataInputStream.

## ClientSendMessageThread
Les attributs de cette classe sont les mêmes que ceux de la classe précédente à la différence près que ce sont outputStream et dataOutputStream. Le constructeur instancie les attributs.
run() lit d'abord le pseudo du client via l'entrée clavier puis effectue une boucle tant que exit est faux qui lit le message du client via l'entrée clavier et l'envoie à ClientReceiveMessageThread. Lorsqu'exit est vrai, on ferme output et dataOutputStream.

##Conclusion
Pour mettre en place des chats entre différents utilisateurs, nous créons deux classes principales: Serveur et Client. Le serveur continue d'attendre les connexions des clients, et à chque fois qu'une connexion avec un client est établie, un objet de la classe ClientHandlerThread est utilisé pour lire les messages provenent de ce client et envoyer les messages provent autres clients à ce client. De plus, deux autres Threads sont dédiés à la lecture des messages provenant du serveur et à l’envoi des messages saisis par le client au serveur. À chaque fois qu'un nouveau client crée une connexion avec le serveur, la liste de pseudos et la HashMap contenant les coupes de pseudo et childHandlerThread actifs sont mises à jour pour éviter les erreurs. 

