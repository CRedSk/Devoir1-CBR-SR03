package fr.utc.sr03;

import java.io.IOException;
import java.net.Socket;

public class Client {
    static public void main(String[] args){
        Socket commSocket; //socket de commnication
       // String pseudo ;
        ClientReceiveMessageThread receiveMessageThread;
        ClientSendMessageThread sendMessageThread;
        try {
            commSocket = new Socket("localhost", 10800);//creer une connexion

            sendMessageThread=new ClientSendMessageThread(commSocket);
            sendMessageThread.start();
            receiveMessageThread=new ClientReceiveMessageThread(commSocket);
            receiveMessageThread.start();

        }catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
