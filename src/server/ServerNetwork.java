package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetwork{

    private static ServerNetwork instance;

    private ServerNetwork(){
    }

    public static ServerNetwork getInstance(){
        return (instance == null)? instance = new ServerNetwork() : instance;
    }


    public void startServer (String port){
        ServerSocket sSoc = null;

        try {
            sSoc = new ServerSocket(Integer.parseInt(port));
            System.err.println("thread criada starserver!");

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        boolean serverOnline = true;

        while(serverOnline) {
            try {
                Socket inSoc = sSoc.accept();
                ServerThread newServerThread = new ServerThread(inSoc);
                newServerThread.start();
                System.err.println("Thread Criada!");

            }
            catch (IOException e) {
                System.err.println("Couldn't connect to client!");
            }

        }

        try {
            sSoc.close();
            serverOnline = false;
        }
        catch (IOException e){
            System.err.println("Couldn't close server!");
        }

    }

}
