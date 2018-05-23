package server;

import catalogs.CatalogUser;
import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import exceptions.*;
import handlers.*;
import message.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;


import static message.MsgError.*;
import static message.MsgType.*;


public class ServerMessage {

    private CatalogUser catUser;
    private FollowerHandler followerHandler;
    private OpinionHandler opinionHandler;
    private PhotoHandler photoHandler;
    private SessionHandler sessionHandler;

    /**
     * Create the ServerMessage to communicate.
     */
    public ServerMessage() throws IOException, ClassNotFoundException, GeneralSecurityException {
        this.catUser = CatalogUser.getCatalog();
    }

    /**
     * Logs client to server.
     * @param m Login Message
     * @return abstract Message with result of the login.
     */
    public MsgSession startSession(MsgSession m) throws IOException, ClassNotFoundException, GeneralSecurityException {
        String user = m.getUser();
        String pass = m.getPwd();
        sessionHandler = new SessionHandler(user);
        MsgSession result;

        try {
            if (sessionHandler.startSession(user, pass)) {
                followerHandler = new FollowerHandler(user);
                opinionHandler = new OpinionHandler(user);
                photoHandler = new PhotoHandler(user);
                result = new MsgSession(STARTSESSION, null, user, true);
            } else {
                result = new MsgSession(STARTSESSION, NOSUCHUSER, user, false);
            }
        } catch (WrongUserPasswordException e) {
            result = new MsgSession(STARTSESSION, WRONGPASSWORD, user, false);
        }
        System.out.println("startSession completed!");

        return result;
    }

    /**
     *
     * @param user
     * @return
     */
    private MsgSession endSession(String user) {
        MsgSession result;
        result = new MsgSession(ENDSESSION, null, user, true);
        photoHandler = null;
        followerHandler = null;
        opinionHandler = null;
        sessionHandler = null;
        System.out.println("endSession completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @param photo
     * @param img
     * @return
     */
    private MsgPhoto addPhoto(String user, String follower, Photo photo, BufferedImage img) throws IOException,
            GeneralSecurityException {
        MsgPhoto result;
        try {
            photoHandler.addPhoto(photo, img);
            result = new MsgPhoto(ADDPHOTO, null, user, follower, true);
        } catch (DuplicatePhotoException e) {
            result = new MsgPhoto(ADDPHOTO, DUPLICATEPHOTO, user, follower, false);
        }
        System.out.println("addPhoto completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @return
     */
    private MsgPhotoData allPhotoData(String user, String follower) {
        MsgPhotoData result;
        try {
            Iterable<PhotoData> photoDataList = photoHandler.getPhotosData(follower);
            result = new MsgPhotoData(ALLPHOTOSDATA, null, user, follower, true, photoDataList);
        } catch (NoSuchUserException e) {
            result = new MsgPhotoData(ALLPHOTOSDATA, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgPhotoData(ALLPHOTOSDATA, NOTFOLLOWING, user, follower, false);
        }
        System.out.println("getAllPhotoData completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @param photoID
     * @return
     */
    private MsgPhoto photoOpinion(String user, String follower, String photoID) {
        MsgPhoto result;
        try {
            PhotoOpinion opinion = photoHandler.getPhotoOpinion(follower, photoID);
            result = new MsgPhoto(PHOTOOPINION, null, user, follower, true, photoID, opinion);
        } catch (NoSuchUserException e) {
            result = new MsgPhoto(PHOTOOPINION, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgPhoto(PHOTOOPINION, NOTFOLLOWING, user, follower, false);
        } catch (NoSuchPhotoException e) {
            result = new MsgPhoto(PHOTOOPINION, NOSUCHPHOTO, user, follower, false);
        }
        System.out.println("getPhotoOpinion completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @return
     */
    private MsgPhoto allPhotos(String user, String follower) throws IOException, ClassNotFoundException,
            GeneralSecurityException {
        MsgPhoto result;
        ArrayList<Photo> photoList;
        try {
            photoList = (ArrayList<Photo>) photoHandler.getAllUserPhotos(follower);
            result = new MsgPhoto(ALLPHOTOS, null, user, follower, true, photoList);
        } catch (NoSuchUserException e) {
            result = new MsgPhoto(ALLPHOTOS, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgPhoto(ALLPHOTOS, NOTFOLLOWING, user, follower, false);
        }
        System.out.println("getAllPhotos completed!");

        return result;
    }

    /**
     *
     * @param comment
     * @param user
     * @param follower
     * @param photoID
     * @return
     */
    private MsgOpinion commentPhoto(String comment, String user, String follower, String photoID) throws IOException,
            GeneralSecurityException {
        MsgOpinion result;
        try {
            opinionHandler.makeComment(comment, follower, photoID);
            result = new MsgOpinion(COMMENTPHOTO, null, user, follower, true);
        } catch (NoSuchUserException e) {
            result = new MsgOpinion(COMMENTPHOTO, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgOpinion(COMMENTPHOTO, NOTFOLLOWING, user, follower, false);
        } catch (NoSuchPhotoException e) {
            result = new MsgOpinion(COMMENTPHOTO, NOSUCHPHOTO, user, follower, false);
        }
        System.out.println("commentPhoto completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @param photoID
     * @return
     */
    private MsgOpinion likePhoto(String user, String follower, String photoID) throws IOException,
            GeneralSecurityException {
        MsgOpinion result;
        try {
            opinionHandler.addLike(follower, photoID);
            result = new MsgOpinion(LIKEPHOTO, null, user, follower, true);
        } catch (NoSuchUserException e) {
            result = new MsgOpinion(LIKEPHOTO, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgOpinion(LIKEPHOTO, NOTFOLLOWING, user, follower, false);
        } catch (NoSuchPhotoException e) {
            result = new MsgOpinion(LIKEPHOTO, NOSUCHPHOTO, user, follower, false);
        } catch (AlreadyLikedException e) {
            result = new MsgOpinion(LIKEPHOTO, ALREADYLIKED, user, follower, false);
        }
        System.out.println("likePhoto completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @param photoID
     * @return
     */
    private MsgOpinion dislikePhoto(String user, String follower, String photoID) throws IOException,
            GeneralSecurityException {
        MsgOpinion result;
        try {
            opinionHandler.addDisLike(follower, photoID);
            result = new MsgOpinion(DISLIKEPHOTO, null, user, follower, true);
        } catch (NoSuchUserException e) {
            result = new MsgOpinion(DISLIKEPHOTO, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgOpinion(DISLIKEPHOTO, NOTFOLLOWING, user, follower, false);
        } catch (NoSuchPhotoException e) {
            result = new MsgOpinion(DISLIKEPHOTO, NOSUCHPHOTO, user, follower, false);
        } catch (AlreadyDislikedException e) {
            result = new MsgOpinion(DISLIKEPHOTO, ALREADYDISLIKED, user, follower, false);
        }
        System.out.println("dislikePhoto completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @return
     */
    private MsgFollower followUser(String user, String follower) throws IOException, GeneralSecurityException {
        MsgFollower result;
        try {
            followerHandler.addFollow(follower);
            result = new MsgFollower(FOLLOWUSER, null, user, follower, true);
        } catch (NoSuchUserException e) {
            result = new MsgFollower(FOLLOWUSER, NOSUCHUSER, user, follower, false);
        } catch (AlreadyFollowingException e) {
            result = new MsgFollower(FOLLOWUSER, ALREADYFOLLOWING, user, follower, false);
        }
        System.out.println("followUser completed!");

        return result;
    }

    /**
     *
     * @param user
     * @param follower
     * @return
     */
    private MsgFollower unfollowUser(String user, String follower) throws IOException, GeneralSecurityException {
        MsgFollower result;
        try {
            followerHandler.removeFollow(follower);
            result = new MsgFollower(UNFOLLOWUSER, null, user, follower, true);
        } catch (NoSuchUserException e) {
            result = new MsgFollower(UNFOLLOWUSER, NOSUCHUSER, user, follower, false);
        } catch (AlreadyNotFollowingException e) {
            result = new MsgFollower(UNFOLLOWUSER, ALREADYNOTFOLLOWING, user, follower, false);
        }
        System.out.println("unfollowUser completed!");

        return result;
    }

    /**
     * This method recives the command message, unpack's it and treats it.
     * @param m The command Message for the server to realize.
     * @return The reply message for the client.
     * @throws IOException
     */
    public Message unpackAndTreatMsg(Message m) throws IOException, ClassNotFoundException, GeneralSecurityException {
        Message msgResult = null;
        MsgType tpMsg = m.getC_type();
        MsgFollower mFollower;
        MsgOpinion mOpinion;
        MsgPhoto mPhoto;
        MsgPhotoData msgPhotoData;
        MsgSession mSession;

        switch (tpMsg) {

            case STARTSESSION:
                break;

            case ENDSESSION:
                mSession = (MsgSession) m;
                msgResult = endSession(mSession.getUser());
                break;

            case ADDPHOTO:
                mPhoto = (MsgPhoto) m;
                msgResult = addPhoto(mPhoto.getUser(), mPhoto.getFollowID(), mPhoto.getPhoto(), mPhoto.getBufferedImage());
                break;

            case ALLPHOTOSDATA:
                msgPhotoData = (MsgPhotoData) m;
                msgResult = allPhotoData(msgPhotoData.getUser(), msgPhotoData.getFollowID());
                break;

            case PHOTOOPINION:
                mPhoto = (MsgPhoto) m;
                msgResult = photoOpinion(mPhoto.getUser(), mPhoto.getFollowID(), mPhoto.getPhotoID());
                break;

            case ALLPHOTOS:
                mPhoto = (MsgPhoto) m;
                msgResult = allPhotos(mPhoto.getUser(), mPhoto.getFollowID());
                break;

            case COMMENTPHOTO:
                mOpinion = (MsgOpinion) m;
                msgResult = commentPhoto(mOpinion.getComment(), mOpinion.getUser(), mOpinion.getFollowID(), mOpinion.getPhotoID());
                break;

            case LIKEPHOTO:
                mOpinion = (MsgOpinion) m;
                msgResult = likePhoto(mOpinion.getUser(), mOpinion.getFollowID(), mOpinion.getPhotoID());
                break;

            case DISLIKEPHOTO:
                mOpinion = (MsgOpinion) m;
                msgResult = dislikePhoto(mOpinion.getUser(), mOpinion.getFollowID(), mOpinion.getPhotoID());
                break;

            case FOLLOWUSER:
                mFollower = (MsgFollower) m;
                msgResult = followUser(mFollower.getUser(), mFollower.getFollowID());
                break;

            case UNFOLLOWUSER:
                mFollower = (MsgFollower) m;
                msgResult = unfollowUser(mFollower.getUser(), mFollower.getFollowID());
                break;
            default:
                break;
        }
        return msgResult;

    }
}
