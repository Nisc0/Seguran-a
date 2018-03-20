package client;

import com.sun.org.apache.xml.internal.serialize.Serializer;
import domain.IPCheck;
import domain.Message;
import domain.Photo;
import domain.User;
import exceptions.NotValidAddressException;
import handlers.FollowerHandler;
import handlers.PhotoHandler;

//Classe que empacota uma Message e envia-a para o clientNetwork
public class ClientMessage{

    private ClientNetwork cl;
    private String currUser;
    private String server;
    private int port;

    public ClientMessage( String server, int port){
        this.server = server;
        this.port = port;
/**
        Message msg = new Message(user, pwd, serverPort[0], port);
        cl.sendReceive(msg);
*/
    }

    public boolean addPhoto(String currUser, String photoID){
        Message msg = new MsgPhoto(MsgType.ADDPHOTO, null, currUser, null, photoID);
        return cl.sendReceive(msg);
    }

    public boolean getAllPhotosData(String currUser){
        Message msg = new MsgPhoto(MsgType.ALLPHOTOSDATA, null, currUser, null, null);
        return cl.sendReceive(msg);
    }

    public void getPhotoOpinion(String userID, String photoID){

    }

    public void copyPhotos(String userID){

    }



}
