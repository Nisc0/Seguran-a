package server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import message.Message;
import message.MsgSession;

import static message.MsgType.*;

import java.io.*;
import java.net.Socket;
import java.security.GeneralSecurityException;


//Threads utilizadas para comunicacao com os clientes
public class ServerThread extends Thread {

    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private ServerMessage srvMsg;

    /**
     * Creates the thread to serve the client
     * @param inSoc - communication socket
     */
    ServerThread(Socket inSoc) {
        this.socket = inSoc;
        System.out.println("Client thread created!");
    }

    /**
     * Runs the thread.
     */
    public void run(){
        System.out.println("Now running...");
        try {
             outStream = new ObjectOutputStream(socket.getOutputStream());
             inStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            MsgSession logMsg = (MsgSession) receiveMsg();
            srvMsg = new ServerMessage();
            sendMsg(srvMsg.startSession(logMsg));
            System.out.println("Login done!");
        }
        catch (ClassNotFoundException | IOException | GeneralSecurityException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        boolean online = true;

        while (online) {
            System.out.println("Awaiting orders...");

            Message message;
            Message result;
            try {
                message = receiveMsg();
                result = srvMsg.unpackAndTreatMsg(message);

                if (result.getC_type() == ENDSESSION) {
                    sendMsg(result);
                    System.out.println("Client thread closed!");
                    try {
                        srvMsg = null;
                        outStream.close();
                        inStream.close();
                        socket.close();
                        online = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    sendMsg(result);
                }
            } catch (ClassNotFoundException | IOException | GeneralSecurityException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     *
     * @param msg
     * @throws IOException
     */
    private void sendMsg(Message msg) throws IOException {
        outStream.writeObject(msg);
        outStream.flush();
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Message receiveMsg() throws IOException, ClassNotFoundException {
        return (Message) inStream.readObject();
    }
}

