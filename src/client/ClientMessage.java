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
import java.io.ObjectOutputStream;

public class ClientMessage {

    private ClientNetwork cl;
    private String server;
    private int port;

    /**
     * Client Message Construtor
     * @param server server
     * @param port server port
     * @throws IOException
     */
    public ClientMessage(String server, int port) throws IOException {
        this.server = server;
        this.port = port;
        this.cl = new ClientNetwork(server, port);
    }

    /**
     * Starts a session in the server
     * @param currUser name of the user
     * @param pwd user's password
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean startSession(String currUser, String pwd) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgSession msg = (MsgSession) cl.sendReceive(new MsgSession(MsgType.STARTSESSION, null, currUser, true, pwd));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     * Ends the session for the user.
     * @param currUser user to terminate session
     * @return true if success, false othewise
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean endSession(String currUser) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgSession msg = (MsgSession) cl.sendReceive(new MsgSession(MsgType.ENDSESSION, null, currUser, true));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     * Adds a photo to the client in the server
     * @param currUser user to add photo
     * @param photoID id (name) of the photo
     * @param photo photo to add
     * @param img bufferimage
     * @return true if success, false othewise
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean addPhoto(String currUser, String photoID, Photo photo, BufferedImage img) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {

        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ADDPHOTO, null, currUser, null, true, photoID, photo, img));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     * Retrives all users photo
     * @param currUser current user
     * @param userID client to retrive all photos
     * @return a list with all photodata from the user
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public Iterable<PhotoData> getAllPhotosData(String currUser, String userID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgPhotoData msg = (MsgPhotoData) cl.sendReceive(new MsgPhotoData(MsgType.ALLPHOTOSDATA, null, currUser, userID, true, null));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getPhotoDataList();
    }

    /**
     * Retrives all opinions from a photo
     * @param currUser current user
     * @param userID the user that the photo belongs
     * @param photoID name of the photo to get the opinion
     * @return the photo opinion
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public PhotoOpinion getPhotoOpinion(String currUser, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.PHOTOOPINION, null, currUser, userID, true, photoID));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getOpinion();
    }

    /**
     *
     * @param currUser
     * @param userID
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public Iterable<Photo> getAllPhotos(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgPhoto msg = (MsgPhoto) cl.sendReceive(new MsgPhoto(MsgType.ALLPHOTOS, null, currUser, userID, true));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getPhotoList();
    }

    /**
     *
     * @param currUser
     * @param comment
     * @param userID
     * @param photoID
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean commentPhoto(String currUser, String comment, String userID, String photoID) throws IOException,
            ClassNotFoundException, DuplicatePhotoException, AlreadyFollowingException, NotFollowingException,
            NoSuchUserException, AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException,
            WrongUserPasswordException, AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.COMMENTPHOTO, null, currUser, userID, true, photoID, comment));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     *
     * @param currUser
     * @param userID
     * @param photoID
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean likePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.LIKEPHOTO, null, currUser, userID, true, photoID, true));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     *
     * @param currUser
     * @param userID
     * @param photoID
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean dislikePhoto(String currUser, String userID, String photoID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgOpinion msg = (MsgOpinion) cl.sendReceive(new MsgOpinion(MsgType.DISLIKEPHOTO, null, currUser, userID, true, photoID, false));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     *
     * @param currUser
     * @param userID
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean followUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException,
            AlreadyNotFollowingException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.FOLLOWUSER, null, currUser, userID, true));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     *
     * @param currUser
     * @param userID
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DuplicatePhotoException
     * @throws AlreadyFollowingException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws AlreadyLikedException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     * @throws WrongUserPasswordException
     * @throws AlreadyNotFollowingException
     */
    public boolean unfollowUser(String currUser, String userID) throws IOException, ClassNotFoundException,
            DuplicatePhotoException, AlreadyFollowingException, NotFollowingException, NoSuchUserException,
            AlreadyLikedException, NoSuchPhotoException, AlreadyDislikedException, WrongUserPasswordException, AlreadyNotFollowingException {
        MsgFollower msg = (MsgFollower) cl.sendReceive(new MsgFollower(MsgType.UNFOLLOWUSER, null, currUser, userID, true));
        if (msg.getC_err() != null) findException(msg.getC_err());
        return msg.getSuccess();
    }

    /**
     *
     * @param err
     * @throws WrongUserPasswordException
     * @throws NotFollowingException
     * @throws NoSuchUserException
     * @throws NoSuchPhotoException
     * @throws AlreadyFollowingException
     * @throws DuplicatePhotoException
     * @throws AlreadyLikedException
     * @throws AlreadyDislikedException
     * @throws AlreadyNotFollowingException
     */
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
            case USERCREATED:
                System.out.println("New user created!");
            default:
                break;
        }
    }


}