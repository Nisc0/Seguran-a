package client;

import exceptions.*;
import message.*;

import java.io.IOException;

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

    public void addPhoto(String currUser, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ADDPHOTO, null, currUser, null, photoID));
        findException(msg.getC_err());
    }

    public void getAllPhotosData(String currUser, String userID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ALLPHOTOSDATA, null, currUser, userID, null));
        findException(msg.getC_err());
    }
    //TODO:
    public void getPhotoOpinion(String currUser, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.PHOTOOPINION, null, currUser, userID, photoID, null));
        findException(msg.getC_err());
    }

    public void copyPhotos(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ALLPHOTOS, null, currUser, userID, null));
        findException(msg.getC_err());
    }

    public void commentPhoto(String currUser, String comment, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.COMMENTPHOTO, null, currUser, userID, photoID, comment));
        findException(msg.getC_err());
    }

    public void likePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.LIKEPHOTO, null, currUser, userID, photoID, null));
        findException(msg.getC_err());
    }

    public void dislikePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.DISLIKEPHOTO, null, currUser, userID, photoID, null));
        findException(msg.getC_err());
    }

    public void followUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.FOLLOWUSER, null, currUser, userID));
        findException(msg.getC_err());
    }

    public void unfollowUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.UNFOLLOWUSER, null, currUser, userID));
        findException(msg.getC_err());
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