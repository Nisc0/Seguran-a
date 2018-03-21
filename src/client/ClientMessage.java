package client;

import com.sun.org.apache.xml.internal.serialize.Serializer;
import domain.IPCheck;
import domain.Message;
import domain.Photo;
import domain.User;
import exceptions.NotValidAddressException;
import handlers.FollowerHandler;
import handlers.PhotoHandler;

import java.io.IOException;

//Classe que empacota uma Message e envia-a para o clientNetwork
public class ClientMessage{

    private ClientNetwork cl;
    //private String currUser;
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

    public Message addPhoto(String currUser, String photoID) throws IOException {
        Message msg = new MsgPhoto(MsgType.ADDPHOTO, null, currUser, null, photoID);
        return cl.sendReceive(msg);
    }

    public Message getAllPhotosData(String currUser, String userID) throws IOException {
        Message msg = new MsgPhoto(MsgType.ALLPHOTOSDATA, null, currUser, userID, null);
        return cl.sendReceive(msg);
    }

    public Message getPhotoOpinion(String currUser, String userID, String photoID) throws IOException {
        Message msg = new MsgOpinion(MsgType.PHOTOOPINION, null, currUser, userID, photoID);
        return cl.sendReceive(msg);
    }

    public Message copyPhotos(String currUser, String userID) throws IOException {
        Message msg = new MsgPhoto(MsgType.ALLPHOTOS, null, currUser, userID, null);
        return cl.sendReceive(msg);
    }

    public Message commentPhoto(String currUser, String comment, String userID, String photoID) throws IOException {
        Message msg = new MsgOpinion(MsgType.COMMENTPHOTO, null, currUser, userID, photoID, comment);
        return cl.sendReceive(msg);
    }

    public Message likePhoto(String currUser, String userID, String photoID) throws IOException {
        Message msg = new MsgOpinion(MsgType.LIKEPHOTO, null, currUser, userID, photoID, null);
        return cl.sendReceive(msg);
    }


    public Message dislikePhoto(String currUser, String userID, String photoID) throws IOException {
        Message msg = new MsgOpinion(MsgType.DISLIKEPHOTO, null, currUser, userID, photoID, null);
        return cl.sendReceive(msg);
    }

    public Message followUser(String currUser, String userID) throws IOException {
        Message msg = new MsgFollower(MsgType.FOLLOWUSER, null, currUser, userID);
        return cl.sendReceive(msg);
    }

    public Message unfollowUser(String currUser, String userID) throws IOException {
        Message msg = new MsgFollower(MsgType.UNFOLLOWUSER, null, currUser, userID);
        return cl.sendReceive(msg);
    }

}