package server;

import java.io.IOException;
import java.net.ServerSocket;


import javax.net.ssl.*;


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
        boolean serverOnline = true;

        SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = null;

        try{
            // Initialize the Server Socket
            sslServerSocket = (SSLServerSocket) sslServerSocketfactory.createServerSocket(Integer.parseInt(port));
            sslServerSocket.setNeedClientAuth(true);

        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Error creating server socket!");
            System.exit(-1);
        }


            while(serverOnline) {
                try {
                    SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                    ServerThread newServerThread = new ServerThread(sslSocket);
                    System.out.println("SSL ServerSocker created!");
                    newServerThread.start();
                    System.out.println("Thread Criada!");
                }
                catch(IOException e) {
                    System.out.println("Couldn't connect to client!!");
                }

            }

        try {
            sslServerSocket.close();
        }
        catch (IOException e) {
            System.out.println("Couldn't close server!");
        }
    }


}
