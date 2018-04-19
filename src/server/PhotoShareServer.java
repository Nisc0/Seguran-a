package server;

public class PhotoShareServer {


	public static void main(String[] args) {
		String port = args[0];
		System.setProperty("javax.net.ssl.keyStore", "./Autentication/ServerKeyStore.keyStore");
		System.setProperty("javax.net.ssl.keyStorePassword", "dibrunis");
		System.setProperty("javax.net.ssl.trustStore", "./Autentication/ServerTrustStore.keyStore");


		ServerNetwork server = ServerNetwork.getInstance();
		server.startServer(port);
		System.out.println("Server initialized at port " + port + " !");
	}

}