package client;

import domain.Message;

//Classe que empacota uma Message e envia-a para o clientNetwork
public class ClientMessage {
	
	private ClientNetwork cl;
	
	public ClientMessage(String user, String pwd, String server) {
		
		//verificar qual a mensagem a ser criada - criar varias messages
		
		String[] serverPort = server.split(":");
		int port;
		try {
			port = Integer.parseInt(serverPort[1]);
		} catch (NumberFormatException e) {
			//TODO: verificar se deu bosta e nao eh com um SOP!
			System.out.println("Porta dada errada!");
		}
		Message msg = new Message(user, pwd, serverPort[0], port);
		cl.sendReceive(msg);
		
	}
	
	
}
