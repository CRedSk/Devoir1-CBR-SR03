# SR03 Devoir 1

 Le but de ce devoir est de créer une application de chat entre différents clients via un serveur, des sockets et des threads. Les sockets permettent d'échanger des informations entre un serveur et un client, or, pour gérer plusieurs clients en même temps, il faut utiliser des threads. Un thread correspond à un client, il y a donc autant de threads créés que de clients dans le chat. Cette application permet à différents utilisateurs de se connecter à un serveur pour pouvoir discuter. 
L'application fonctionne comme suit : inserer schéma

Le projet est constitué de 5 classes : Serveur, Client, ClientHandlerThread, ClientReceiveMessageThread et ClientSendMessageThread. 

## Serveur

## ClientHandlerThread

## Client
La classe client possède les attributs privés commSocket, receiveMessageThread et sendMessageThread. Elle permet de créer la connexion avec le serveur via commSocket et instancie et "start" les deux threads.

## ClientReceiveMessageThread
Cette classe possède les attributs privés commSocket, input de type InputStream, dataInputStream et un booléen exit.
Le constructeur permet d'instancier la socket, input et dataInputStream. La méthode run effectue une boucle tant que exit est faux qui lit le message envoyé par le client via le thread ClientSendMessageThread et le retourne dans la console. Lorsqu'exit est vrai, on ferme input et dataInputStream.

## ClientSendMessageThread
Les attributs de cette classe sont les mêmes que ceux de la classe précédente à la différence près que ce sont outputStream et dataOutputStream. Le constructeur instancie les attributs.
run() lit d'abord le pseudo du client via l'entrée clavier puis effectue une boucle tant que exit est faux qui lit le message du client via l'entrée clavier et l'envoie à ClientReceiveMessageThread. Lorsqu'exit est vrai, on ferme output et dataOutputStream.
