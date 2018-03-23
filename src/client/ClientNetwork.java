package client;

import java.io.*;
import java.net.Socket;
import message.Message;

//Classe com o metodo send_receive, onde cria a Socket
public class ClientNetwork {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Creates a network to communicate with the server
     * @param ip Server ip
     * @param port server port
     * @throws IOException
     */
    public ClientNetwork(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        //TODO: verificar exception da socket
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        //TODO: verificar exception do buffer
    }

    /**
     * Cummunicates with the server
     * @param msg Message to send the server
     * @return Message recieve from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Message sendReceive(Message msg) throws IOException, ClassNotFoundException {
        send(msg);
        return receive();
    }

    /**
     *
     * @param msg
     * @throws IOException
     */
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

    /**
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Message receive() throws IOException, ClassNotFoundException {
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

    /**
     *
     * @param msg
     * @return
     * @throws IOException
     */
    private byte[] serialize(Message msg) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(msg);
        return out.toByteArray();
    }

    /**
     *
     * @param msg
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Message deserialize(byte[] msg) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(msg);
        ObjectInputStream is = new ObjectInputStream(in);
        return (Message) is.readObject();
    }
}