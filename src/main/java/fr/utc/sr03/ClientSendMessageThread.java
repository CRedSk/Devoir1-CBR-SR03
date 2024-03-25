package fr.utc.sr03;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

//un thread qui intercepte les messages saisis par l’utilisateur et les envoient au serveur à travers le socket de communication.
public class ClientSendMessageThread extends Thread {
    private Socket commSocket ;
    private OutputStream output;
    private DataOutputStream dataOutputStream;

    public ClientSendMessageThread(Socket commSocket) {
        this.commSocket=commSocket;
        try{
            output=commSocket.getOutputStream();
            dataOutputStream=new DataOutputStream(output);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    boolean exit=false;
    public void run(){
        try{
            Scanner sc=new Scanner(System.in);
            while(!exit){
                boolean hasEnter=sc.hasNextLine();
                if(hasEnter){
                    String message=sc.nextLine();// Lire le message envoyé par utilisateur
                    dataOutputStream.writeUTF(message);//envoyer ce message à serveur
                    if(message.equals("exit")){
                        output.close();
                        dataOutputStream.close();
                        exit=true;
                        //commSocket.close();
                    }

                }
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
