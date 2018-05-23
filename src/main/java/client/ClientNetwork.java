package client;

import java.io.*;
import java.net.Socket;

import message.Message;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

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
        SSLSocketFactory sslSocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketfactory.createSocket(ip,port);

        out = new ObjectOutputStream(sslSocket.getOutputStream());
        in = new ObjectInputStream(sslSocket.getInputStream());
    }

    /**
     * Communicates with the server
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
        return (Message) in.readObject();
    }

}