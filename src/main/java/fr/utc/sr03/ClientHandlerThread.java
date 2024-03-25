package fr.utc.sr03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//On crée une classe hérité de classe Thread pour stocker un socket de commnication ,accepter les messages envoyés par un utilisateur et l'envoyer les messages envoyés par autres utlisateurs
public class ClientHandlerThread extends Thread{
    private String pseudo;
    private Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private HashMap<String,ClientHandlerThread> clientsPseudoThreadMap ;
    public ClientHandlerThread(Socket socket) { //initialiser les valeurs de pseudo,inputStream et outputStream
        this.clientSocket=socket;
        try {

            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF("Entrez votre pseudo,svp : ");
            String inPseudo=inputStream.readUTF();
            pseudo=inPseudo;
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }
    //Les accesseurs et mutateurs
    public String getPseudo(){
        return pseudo;
    }
    public void setPseudo(String pseudo){
        this.pseudo=pseudo;
    }
    public DataInputStream getInputStream(){
        return inputStream;
    }
    public void setInputStream(DataInputStream input){
        this.inputStream=input;
    }
    public DataOutputStream getOutputStream(){
        return outputStream;
    }
    public void setOutputStream(DataOutputStream output){
        this.outputStream=output;

    }

    public void setClientsPseudoThreadMap(HashMap<String,ClientHandlerThread> clientsPseudoThreadMap ){
        this.clientsPseudoThreadMap=clientsPseudoThreadMap;
    }
    boolean exit=false;
    String message="";
    @Override
    public void run(){

        while(!exit){//attendre les messages et si qqn envoie un message ,envoyer ce message à tous les utilisateurs
            try {
                message = inputStream.readUTF();
                if (message.equals("exit")){
                    message="L'utilisateur "+this.pseudo+" a quitté la conversation ";
                    exit=true;
                    outputStream.writeUTF("exit");
                    clientSocket.close();
                    outputStream.close();
                    inputStream.close();

                }
                if(!clientsPseudoThreadMap.isEmpty()) {
                    for (Map.Entry<String, ClientHandlerThread> clientPseudoThreadMap : clientsPseudoThreadMap.entrySet()) {
                        if (clientPseudoThreadMap.getValue() != Thread.currentThread()) {
                            clientPseudoThreadMap.getValue().getOutputStream().writeUTF(this.pseudo + ":" + message);//envoyer le message à autres utilisateurs
                        }
                    }
                }

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }


}
