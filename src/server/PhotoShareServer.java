package server;

public class PhotoShareServer {
	
	public static void main(String[] args) {
		String port = args[1];
		System.out.println("Servidor inicializado no porto " + port + " !");
		ServerNetwork server = ServerNetwork.getInstance();
		server.startServer(port);
	}

}