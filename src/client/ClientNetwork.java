package client;
import java.io.*;
import java.net.Socket;

import domain.Message;

//Classe com o metodo send_receive, onde cria a Socket
public class ClientNetwork {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

	public ClientNetwork(String ip, int port) throws IOException {
	    socket = new Socket(ip, port);
	    BufferedInputStream bufIn = new BufferedInputStream(socket.getInputStream());
	    BufferedOutputStream bufOut = new BufferedOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(bufIn);
        out = new ObjectOutputStream(bufOut);
	}

	public Message sendReceive(Message msg) throws IOException, ClassNotFoundException {
        send(msg);
		return receive();
	}

    private void send(Message msg) throws IOException {
	    byte[] msgS = serialize(msg);
	    int size = msgS.length;
	    out.write(size);
	    for(int i = 0; i < size/1024; i++){
	        if(size-i*1024 < 1024)
	            //quando o restante eh menor que 1024
	            out.write(msgS,i*1024, size-i*1024);
	        else
	            out.write(msgS, i*2014, 1024);
        }
    }

    private Message receive() throws IOException, ClassNotFoundException {
	    int size = in.read();
        byte[] msg = new byte[size];
        for(int i = 0; i < size/1024; i++){
            if(size-i*1024 < 1024)
                in.read(msg, i*1024, size-i*1024);
            else
                in.read(msg, i*1024, 1024);
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