package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetwork{

    private static ServerNetwork instance;

    private ServerNetwork(){
    }

    /**
     * ServerNetwork's Singleton
     * @return the ServerNetwork instance
     */
    public static ServerNetwork getInstance(){
        return (instance == null)? instance = new ServerNetwork() : instance;
    }

    /**
     * Starts the Server
     * @param port number (in String) on which the the port is created
     */
    public void startServer (String port){
        ServerSocket sSoc = null;
        boolean serverOnline = true;

        try {
            sSoc = new ServerSocket(Integer.parseInt(port));
            System.out.println("serversocket criada starserver!");

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }


        while(serverOnline) {
            try {
                Socket inSoc = sSoc.accept();
                ServerThread newServerThread = new ServerThread(inSoc);
                newServerThread.start();
                System.out.println("Thread Criada!");

            }
            catch (IOException e) {
                System.out.println("Couldn't connect to client!");
            }
        }

        try {
            sSoc.close();
            serverOnline = false;
        }
        catch (IOException e){
            System.out.println("Couldn't close server!");
        }

    }

}
