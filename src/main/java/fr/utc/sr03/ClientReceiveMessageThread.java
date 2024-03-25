package fr.utc.sr03;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

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
    boolean exit=false;
//    int bytesRead = input.read();
//                if(bytesRead==-1){
//        exit=true;
//        return;
//    }
    String message;

    public void run(){
        try {
            while(!exit){
                if(!commSocket.isConnected()){
                    input.close();
                    dataInputStream.close();
                    exit = true;
                    commSocket.close();
                }else {
                    try {
                        message = dataInputStream.readUTF();//attendre les messages envoyés par serveur
                    }catch(SocketException ex){
                        commSocket.close();
                        dataInputStream.close();
                        message="";
                        System.out.println("Exit avec succes");

                        exit=true;
                    }
                    if (message.contentEquals("exit")) {
                        input.close();
                        dataInputStream.close();
                        exit = true;
                        commSocket.close();
                    } else {
                        if(commSocket.isConnected()) {
                            System.out.println(message);
                        }
                    }
                }
            }

        }catch (IOException ex) {
               ex.printStackTrace();
        }
    }

}
