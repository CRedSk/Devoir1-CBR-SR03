package fr.utc.sr03;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Serveur {
    public static void main(String[] args) {

        ArrayList<String> pseudoList = new ArrayList<>();

        HashMap<String,ClientHandlerThread> clientsPseudoThreadMap=new HashMap<>();
        try {
            ServerSocket serveurSocket = new ServerSocket(10800);
            System.out.println("attendre les connexions");
            while (true) { //continuer à accepter les demandes entrantes et pour chaque client,un objet de classe ClientHandlerThread est crée pour stocker le socket de commnunication avec ce client et lire les messagers envoyés par ce client
                Socket commSocket = serveurSocket.accept();//attendre les connexions
                //Vérifier que tous les threads dans map ne s'arrêtent pas
                for (Map.Entry<String,ClientHandlerThread> clientpseudoThread:clientsPseudoThreadMap.entrySet()){
                    if (!clientpseudoThread.getValue().isAlive()){
                        clientsPseudoThreadMap.remove(clientpseudoThread.getKey());
                        pseudoList.remove(clientpseudoThread.getKey());
                    }
                }


                ClientHandlerThread clientHandlerThread = new ClientHandlerThread(commSocket);
                clientHandlerThread.start();
                System.out.println("Nouvelle connexion: " + commSocket.getInetAddress());

                while (pseudoList.contains(clientHandlerThread.getPseudo())) { //Pour garantir que les pseudos sont uniques
                    DataOutputStream outputStream = clientHandlerThread.getOutputStream();
                    outputStream.writeUTF(clientHandlerThread.getPseudo() + " existe déjà, entrez un autre pseudo svp:");
                    DataInputStream inputStream = clientHandlerThread.getInputStream();
                    String newPseudo = inputStream.readUTF();
                    clientHandlerThread.setPseudo(newPseudo);
                    outputStream.close();
                    inputStream.close();
                }

                pseudoList.add(clientHandlerThread.getPseudo());

                clientsPseudoThreadMap.put(clientHandlerThread.getPseudo(),clientHandlerThread);

                for (Map.Entry<String,ClientHandlerThread> entry: clientsPseudoThreadMap.entrySet()){ //Nouveller le map contenant les pseudo et les threads
                    entry.getValue().setClientsPseudoThreadMap(clientsPseudoThreadMap);
                }
                //clientHandlerThread.setClientThreadList(threadList);

            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
