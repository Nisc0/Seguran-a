package client;

import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import exceptions.*;
import message.*;

import java.io.IOException;

public class ClientMessage {

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

    public boolean addPhoto(String currUser, String photoID, Photo photo) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ADDPHOTO, null, currUser, null, true, photoID, photo));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getSuccess();
    }

    public Iterable<PhotoData> getAllPhotosData(String currUser, String userID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgPhotoData msg = (MsgPhotoData) cl.sendReceive(new MsgPhotoData(MsgType.ALLPHOTOSDATA, null, currUser, userID, true, null));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getPhotoDataList();
    }

    public PhotoOpinion getPhotoOpinion(String currUser, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.PHOTOOPINION, null, currUser, userID, true, photoID));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getOpinion();
    }

    public Iterable<Photo> getAllPhotos(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ALLPHOTOS, null, currUser, userID, true));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getPhotoList();
    }

    public boolean commentPhoto(String currUser, String comment, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.COMMENTPHOTO, null, currUser, userID, true, photoID, comment));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getSuccess();
    }

    public boolean likePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.LIKEPHOTO, null, currUser, userID, true, photoID, true));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getSuccess();
    }

    public boolean dislikePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.DISLIKEPHOTO, null, currUser, userID, true, photoID, false));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getSuccess();
    }

    public boolean followUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.FOLLOWUSER, null, currUser, userID, true));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getSuccess();
    }

    public boolean unfollowUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException, AlreadyNotFollowingException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.UNFOLLOWUSER, null, currUser, userID, true));
        findException(msg.getC_err(), msg.getSuccess());
        return msg.getSuccess();
    }

    private void findException(MsgError err, boolean success) throws WrongUserPasswordException, NotFollowingException,
            NoSuchUserException, NoSuchPhotoException, AlreadyFollowingException, DuplicatePhotoException,
            AlreadyLikedException, AlreadyDislikedException, AlreadyNotFollowingException {

        if (success)
            switch (err) {
                case ALREADYLIKED:
                    throw new AlreadyLikedException();
                case ALREADYDISLIKED:
                    throw new AlreadyDislikedException();
                case ALREADYFOLLOWING:
                    throw new AlreadyFollowingException();
                case ALREADYNOTFOLLOWING:
                    throw new AlreadyNotFollowingException();
                default:
                    break;
            }
        else
            switch (err) {
                case WRONGPASSWORD:
                    throw new WrongUserPasswordException();
                case NOTFOLLOWING:
                    throw new NotFollowingException();
                case NOSUCHUSER:
                    throw new NoSuchUserException();
                case NOSUCHPHOTO:
                    throw new NoSuchPhotoException();
                case DUPLICATEPHOTO:
                    throw new DuplicatePhotoException();
                default:
                    break;
            }
    }
}