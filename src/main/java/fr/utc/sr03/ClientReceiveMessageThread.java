package fr.utc.sr03;

import java.io.*;
import java.net.Socket;

//un thread qui va intercepter les messages envoyés par le serveur puis les afficher dans la console.
public class ClientReceiveMessageThread extends Thread{
    private Socket commSocket;
    private InputStream input;
    private DataInputStream dataInputStream;

    public ClientReceiveMessageThread(Socket commSocket){
        this.commSocket=commSocket;
        try {
            input = commSocket.getInputStream();
            dataInputStream=new DataInputStream(input);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private boolean exit=false;
    public void run(){
        try {
            while(!exit){
                String message = dataInputStream.readUTF();//attendre les messages envoyés par serveur
                //System.out.println(message);
                if (message.contentEquals("exit")){
                    input.close();
                    dataInputStream.close();
                    exit=true;
                    commSocket.close();
                }else{
                    System.out.println(message);
                }
            }

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
