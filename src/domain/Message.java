package domain;

public class Message {

	private String userID;
	private String pwd;
	private String server;
	private int port;
	//o estriga tem 13 atributos para fazer multiplos construtores
	
	public Message(String user, String pwd, String server, int port){
		this.userID = user;
		this.pwd = pwd;
		this.server = server;
		this.port = port;
	}
	
}
