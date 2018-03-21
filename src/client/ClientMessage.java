package client;

import domain.Message;
import exceptions.*;
import message.*;

import java.io.IOException;

//Classe que empacota uma Message e envia-a para o clientNetwork
public class ClientMessage{

    private ClientNetwork cl;
    //private String currUser;
    private String server;
    private int port;

    public ClientMessage(String server, int port) throws IOException {
        this.server = server;
        this.port = port;
        this.cl = new ClientNetwork(server, port);
        //TODO: verificar exception do clientnetwork
    }

    public boolean addPhoto(String currUser, String photoID) throws IOException, ClassNotFoundException {
        MsgPhoto res = cl.sendReceive(new MsgPhoto(MsgType.ADDPHOTO, null, currUser, null, photoID));
        findException(res.getC_err());
    }

    public <T extends Message> Message getAllPhotosData(String currUser, String userID) throws IOException {
        MsgPhoto msg = new MsgPhoto(MsgType.ALLPHOTOSDATA, null, currUser, userID, null);
        return cl.sendReceive(msg);
    }

    public Message getPhotoOpinion(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException {
        MsgOpinion msg = new MsgOpinion(MsgType.PHOTOOPINION, null, currUser, userID, photoID, null);
        return cl.sendReceive(msg);
    }

    public Message copyPhotos(String currUser, String userID) throws IOException, ClassNotFoundException {
        MsgPhoto msg = new MsgPhoto(MsgType.ALLPHOTOS, null, currUser, userID, null);
        return cl.sendReceive(msg);
    }

    public Message commentPhoto(String currUser, String comment, String userID, String photoID) throws IOException {
        MsgOpinion msg = new MsgOpinion(MsgType.COMMENTPHOTO, null, currUser, userID, photoID, comment);
        return cl.sendReceive(msg);
    }

    public Message likePhoto(String currUser, String userID, String photoID) throws IOException {
        MsgOpinion msg = new MsgOpinion(MsgType.LIKEPHOTO, null, currUser, userID, photoID, null);
        return cl.sendReceive(msg);
    }

    public Message dislikePhoto(String currUser, String userID, String photoID) throws IOException {
        MsgOpinion msg = new MsgOpinion(MsgType.DISLIKEPHOTO, null, currUser, userID, photoID, null);
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

    private void findException(MsgError err) throws WrongUserPasswordException, NotFollowingException,
            NoSuchUserException, NoSuchPhotoException, AlreadyFollowingException, DuplicatePhotoException,
            AlreadyLikedException, AlreadyDislikedException {
        switch (err) {
            case WRONGPASSWORD:
                throw new WrongUserPasswordException();
            case NOTFOLLOWING:
                throw new NotFollowingException();
            case NOSUCHUSER:
                throw new NoSuchUserException();
            case NOSUCHPHOTO:
                throw new NoSuchPhotoException();
            case ALREADYFOLLOWING:
                throw new AlreadyFollowingException();
            case DUPLICATEPHOTO:
                throw new DuplicatePhotoException();
            case ALREADYLIKED:
                throw new AlreadyLikedException();
            case ALREADYDISLIKED:
                throw new AlreadyDislikedException();
            default:
                break;
        }
    }

}