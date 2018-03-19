package client;

import domain.IPCheck;
import domain.Message;
import domain.Photo;
import domain.User;
import exceptions.NotValidAddressException;

//Classe que empacota uma Message e envia-a para o clientNetwork
public class ClientMessage {

    private ClientNetwork cl;
    private String server;
    private int port;
    private User curr;

    public ClientMessage(String server, int port) {
        this.server = server;
        this.port = port;
/**
        Message msg = new Message(user, pwd, serverPort[0], port);
        cl.sendReceive(msg);
*/
    }

    public void addPhoto(String photoID){
        Photo photo = new Photo(photoID);
        curr.addPhoto(photo);
    }

    public void getPhotoData(String photoID){
        System.out.println(curr.getAllPhotosData());
    }


}
