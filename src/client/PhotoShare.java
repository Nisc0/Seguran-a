package client;
import domain.IPCheck;
import exceptions.NotValidAddressException;

import java.io.*;

public class PhotoShare {
	
//Cliente mais fortinho que cria uma clientMessage
	
	public static void main(String[] args) throws NotValidAddressException {

	    //TODO: falta o login
		//ClientMessage login = new ClientMessage(args[1], args[2], args[3]);

        if(args.length < 4){
            System.out.println("Por favor siga a forma: <localUserId> <password> <serverAddress>");
            return;
        }

        String[] serverPort;
        String serverAdd = args[3];
        int port;
        if(IPCheck.isIPValid(serverAdd)){
            serverPort = serverAdd.split(":");
            port = Integer.parseInt(serverPort[1]);
        }
        else{
            throw new NotValidAddressException();
        }


		ClientMessage cm = new ClientMessage(serverPort[0], port);

		int ola;
		if(args.length > 4){
		    char op = args[4].charAt(1);
		    switch (op){
                case 'a':
                    if(args.length > 5){
                        int i;
                        for(i = 5; i < args.length; i++){
                            cm.addPhoto(args[i]);
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -a " +
                                "<photos>");
                        return;
                    }
                    break;
                case 'l':
                    if(args.length == 6){
                        cm.getPhotoData(args[5]);
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -l " +
                                "<userID>");
                        return;
                    }
                    break;
                case 'i':
                    ola = 3;
                    break;
                case 'g':
                    ola = 4;
                    break;
                case 'c':
                    ola = 5;
                    break;
                case 'L':
                    ola = 6;
                    break;
                case 'D':
                    ola = 7;
                    break;
                case 'f':
                    ola = 8;
                    break;
                case 'r':
                    ola = 9;
                    break;
            }
        }

	}


		
}