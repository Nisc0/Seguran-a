package server;

public class ServerMessage {

    private static ServerMessage instance;

    private ServerMessage(){
    }

    public static ServerMessage getInstance(){
        return (instance == null)? instance = new ServerMessage() : instance;
    }


}
