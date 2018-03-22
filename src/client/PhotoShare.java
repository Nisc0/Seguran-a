package client;
import domain.IPCheck;
import exceptions.NotValidAddressException;

import java.io.*;

public class PhotoShare {
	
//Cliente mais fortinho que cria uma clientMessage
	
	public static void main(String[] args) throws NotValidAddressException, IOException {

        //TODO: falta o login - e meter numa variavel o userID
        String currUser;
        //ClientMessage login = new ClientMessage(args[1], args[2], args[3]);

        if (args.length < 4) {
            System.out.println("Por favor siga a forma: <localUserId> <password> <serverAddress>");
            return;
        }

        String[] serverPort;
        String serverAdd = args[3];
        int port;
        if (IPCheck.isIPValid(serverAdd)) {
            serverPort = serverAdd.split(":");
            port = Integer.parseInt(serverPort[1]);
        } else {
            throw new NotValidAddressException();
        }


        ClientMessage cm = new ClientMessage(serverPort[0], port);
        //TODO: verificar exception do clientmessage

        if (args.length > 4) {
            char op = args[4].charAt(1);
            switch (op) {
                case 'a':
                    if (args.length > 5) {
                        int i;
                        for (i = 5; i < args.length; i++) {
                            if (cm.addPhoto(currUser, args[i])) {
                                //TODO: Limpar fotos que devem ter dado bem
                                //TODO: Retornar alguma excepcao?!?!?!
                            }
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -a " +
                                "<photos>");
                        return;
                    }
                    break;
                case 'l':
                    if (args.length == 6) {
                        if (!cm.getAllPhotosData(currUser, args[5])) {
                            //TODO: Retornar alguma excepcao ou soh texto?!!?
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -l " +
                                "<userID>");
                        return;
                    }
                    break;
                case 'i':
                    if (args.length == 7) {
                        if (!cm.getPhotoOpinion(currUser, args[5], args[6])) {
                            //TODO: Retornar alguma excepcao ou soh texto!?!?!
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -i " +
                                "<userID> <photo>");
                        return;
                    }
                    break;
                case 'g':
                    if (args.length == 6) {
                        if (!cm.getAllPhotos(currUser, args[5])) {
                            //TODO: Retornar alguma excepcao ou soh texto!?!?!
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -g " +
                                "<userID>");
                        return;
                    }
                    break;
                case 'c':
                    if (args.length == 8) {
                        if (!cm.commentPhoto(currUser, args[5], args[6], args[7])) {
                            //TODO: Retornar alguma excepcao ou soh texto!?!?!
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -c " +
                                "<comment> <userID> <photo>");
                        return;
                    }
                    break;
                case 'L':
                    if (args.length == 7) {
                        if (!cm.likePhoto(currUser, args[5], args[6])) {
                            //TODO: Retornar alguma excepcao ou soh texto!?!?!
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -L " +
                                "<userID> <photo>");
                        return;
                    }
                    break;
                case 'D':
                    if (args.length == 7) {
                        if (!cm.dislikePhoto(currUser, args[5], args[6])) {
                            //TODO: Retornar alguma excepcao ou soh texto!?!?!
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -D " +
                                "<userID> <photo>");
                        return;
                    }
                    break;
                case 'f':
                    if (args.length > 5) {
                        int i;
                        for (i = 5; i < args.length; i++) {
                            if (!cm.followUser(currUser, args[i])) {
                                //TODO: Limpar fotos que devem ter dado bem
                                //TODO: Retornar alguma excepcao?!?!?!
                            }
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -f " +
                                "<followUserIds>");
                        return;
                    }
                    break;
                case 'r':
                    if (args.length > 5) {
                        int i;
                        for (i = 5; i < args.length; i++) {
                            if (!cm.unfollowUser(currUser, args[i])) {
                                //TODO: Limpar fotos que devem ter dado bem
                                //TODO: Retornar alguma excepcao?!?!?!
                            }
                        }
                    } else {
                        System.out.println("Por favor, siga a forma: <localUserId> <password> <serverAddress> -r " +
                                "<followUserIds>");
                        return;
                    }
                    break;
            }
        }
    }
}