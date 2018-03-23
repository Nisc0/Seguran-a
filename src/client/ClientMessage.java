package client;

import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import exceptions.*;
import message.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    public boolean startSession(String currUser, String pwd) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException{
        MsgSession msg = (MsgSession) cl.sendReceive(new MsgSession(MsgType.STARTSESSION, null, currUser, true, pwd));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    public boolean endSession(String currUser) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException{
        MsgSession msg = (MsgSession) cl.sendReceive(new MsgSession(MsgType.ENDSESSION, null, currUser, true));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    public boolean addPhoto(String currUser, String photoID, Photo photo, BufferedImage img) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {

        byte[] imageToSend = null;
        try {
            imageToSend = serializeImage(img, photo);
        } catch (IOException e) {
            System.out.println("Serialization of photo failed!");
        }
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ADDPHOTO, null, currUser, null, true, photoID, photo, imageToSend));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    public Iterable<PhotoData> getAllPhotosData(String currUser, String userID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgPhotoData msg = (MsgPhotoData) cl.sendReceive(new MsgPhotoData(MsgType.ALLPHOTOSDATA, null, currUser, userID, true, null));
        findException(msg.getC_err());
        return msg.getPhotoDataList();
    }

    public PhotoOpinion getPhotoOpinion(String currUser, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.PHOTOOPINION, null, currUser, userID, true, photoID));
        findException(msg.getC_err());
        return msg.getOpinion();
    }

    public Iterable<Photo> getAllPhotos(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ALLPHOTOS, null, currUser, userID, true));
        findException(msg.getC_err());
        return msg.getPhotoList();
    }

    public boolean commentPhoto(String currUser, String comment, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.COMMENTPHOTO, null, currUser, userID, true, photoID, comment));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    public boolean likePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.LIKEPHOTO, null, currUser, userID, true, photoID, true));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    public boolean dislikePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.DISLIKEPHOTO, null, currUser, userID, true, photoID, false));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    public boolean followUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.FOLLOWUSER, null, currUser, userID, true));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    public boolean unfollowUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException, AlreadyNotFollowingException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.UNFOLLOWUSER, null, currUser, userID, true));
        findException(msg.getC_err());
        return msg.getSuccess();
    }

    private void findException(MsgError err) throws WrongUserPasswordException, NotFollowingException,
            NoSuchUserException, NoSuchPhotoException, AlreadyFollowingException, DuplicatePhotoException,
            AlreadyLikedException, AlreadyDislikedException, AlreadyNotFollowingException {

        switch (err) {
            case ALREADYLIKED:
                throw new AlreadyLikedException("Photo already liked!");
            case ALREADYDISLIKED:
                throw new AlreadyDislikedException("Photo already disliked!");
            case ALREADYFOLLOWING:
                throw new AlreadyFollowingException("Already following given user!");
            case ALREADYNOTFOLLOWING:
                throw new AlreadyNotFollowingException("Already not following given user!");
            case WRONGPASSWORD:
                throw new WrongUserPasswordException("Wrong username/password combination!");
            case NOTFOLLOWING:
                throw new NotFollowingException("Not allowed to do that! You must be following that user!");
            case NOSUCHUSER:
                throw new NoSuchUserException("Given username doesn't exist!");
            case NOSUCHPHOTO:
                throw new NoSuchPhotoException("Given photo tag doesn't exist!");
            case DUPLICATEPHOTO:
                throw new DuplicatePhotoException("Given photo already exists!");
            default:
                break;
        }
    }

    private byte[] serializeImage(BufferedImage img, Photo photo) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, photo.getExtension(), baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    private BufferedImage deserializeImage(byte[] img) throws IOException{
        ByteArrayInputStream baos = new ByteArrayInputStream(img);
        BufferedImage image = ImageIO.read(baos);
        return image;
    }

}