package client;

import java.io.*;
import java.net.Socket;
import message.Message;

//Classe com o metodo send_receive, onde cria a Socket
public class ClientNetwork {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientNetwork(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        //TODO: verificar exception da socket
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        //TODO: verificar exception do buffer
    }

    public Message sendReceive(Message msg) throws IOException, ClassNotFoundException {
        System.out.println("vou pro send!");
        send(msg);
        System.out.println("vou pro receive!");
        return receive();
    }

    private void send(Message msg) throws IOException {
        /*byte[] msgS = serialize(msg);
        int size = msgS.length;
        out.writeInt(size);
        out.flush();
        for (int i = 0; i < size / 1024; i++) {
            if (size - i * 1024 < 1024) {
                //quando o restante eh menor que 1024
                out.write(msgS, i * 1024, size - i * 1024);
                out.flush();
            }
            else {
                out.write(msgS, i * 2014, 1024);
                out.flush();
            }
        }*/
        out.writeObject(msg);
        out.flush();
    }

    private Message receive() throws IOException, ClassNotFoundException {
        System.out.println("vou pro receive");
        /*int size = in.readInt();
        System.out.println("sai do read do receive!");
        byte[] msg = new byte[size];
        for (int i = 0; i < size / 1024; i++) {
            if (size - i * 1024 < 1024)
                in.read(msg, i * 1024, size - i * 1024);
            else
                in.read(msg, i * 1024, 1024);
        }
        return deserialize(msg);
        */
       return (Message) in.readObject();
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