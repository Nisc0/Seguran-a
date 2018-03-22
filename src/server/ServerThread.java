package server;

import message.MsgSession;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


//Threads utilizadas para comunicacao com os clientes
public class ServerThread extends Thread {

    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private ServerMessage srvMsg;

    ServerThread(Socket inSoc) {
        this.socket = inSoc;
        System.out.println("Thread do cliente criada!");
    }

    public void run(){
        try {
             outStream = new ObjectOutputStream(socket.getOutputStream());
             inStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {

            //todo - serializar a mensagem

            MsgSession logMsg = (MsgSession) inStream.readObject();
            srvMsg = new ServerMessage();
            enviaMsg(srvMsg.startSession(logMsg));
        }
        catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outStream.close();
            inStream.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void enviaMsg(MsgSession msg) throws java.io.IOException{
        outStream.writeObject(msg);
    }
}

