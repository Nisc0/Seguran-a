package server;

import domain.Message;

import java.net.Socket;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


//Threads utilizadas para comunicacao com os clientes
public class ServerThread extends Thread {

    private Socket socket;
    private ObjectOutputStream outStream = null;
    private ObjectInputStream inStream = null;
    private ServerMessage srvMsg = null;

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
            Msg_Session logMsg = (Msg_Session) inStream.readObject();
            srvMsg = ServerMessage.getInstance();
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

    private void enviaMsg(Msg_Session msg) throws java.io.IOException{
        outStream.writeObject(msg);
    }
}

