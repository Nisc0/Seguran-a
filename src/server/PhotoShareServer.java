package server;

public class PhotoShareServer {


	public static void main(String[] args) {
		String port = args[0];
		ServerNetwork server = ServerNetwork.getInstance();
		server.startServer(port);
		System.out.println("Server initialized at port " + port + " !");
	}

}