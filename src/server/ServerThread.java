package server;

import message.Message;
import message.MsgSession;

import static message.MsgType.*;

import java.io.*;
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
            MsgSession logMsg = (MsgSession) recebeMsg();
            srvMsg = new ServerMessage();
            enviaMsg(srvMsg.startSession(logMsg));
        }
        catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        boolean online = true;

        while (online) {
            Message message;
            Message result;
            try {
                message = recebeMsg();
                result = srvMsg.unpackAndTreatMsg(message);

                if (result.getC_type() == ENDSESSION) {
                    online = false;
                    enviaMsg(result);
                    try {
                        srvMsg = null;
                        outStream.close();
                        inStream.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    enviaMsg(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    private void enviaMsg(Message msg) throws IOException {
        byte[] msgS = serialize(msg);
        int size = msgS.length;
        outStream.write(size);
        for(int i = 0; i < size/1024; i++){
            if(size-i*1024 < 1024)
                //quando o restante eh menor que 1024
                outStream.write(msgS,i*1024, size-i*1024);
            else
                outStream.write(msgS, i*2014, 1024);
        }
    }

    private Message recebeMsg() throws IOException, ClassNotFoundException {
        int size = inStream.read();
        byte[] msg = new byte[size];
        for(int i = 0; i < size/1024; i++){
            if(size-i*1024 < 1024)
                inStream.read(msg, i*1024, size-i*1024);
            else
                inStream.read(msg, i*1024, 1024);
        }
        return deserialize(msg);
    }

    private byte[] serialize(Message msg) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(msg);
        return out.toByteArray();
    }

    private Message deserialize(byte[] msg) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(msg);
        ObjectInputStream is = new ObjectInputStream(in);
        return (Message) is.readObject();
    }
}

