package server;

import catalogs.CatalogoUser;
import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import exceptions.*;
import handlers.*;
import message.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


import static message.MsgError.*;
import static message.MsgType.*;


public class ServerMessage {

    private CatalogoUser catUser;
    private FollowerHandler followerHandler;
    private OpinionHandler opinionHandler;
    private PhotoHandler photoHandler;
    private SessionHandler sessionHandler;

    public ServerMessage() {
        this.catUser = CatalogoUser.getCatalogo();
    }

    public MsgSession startSession(MsgSession m) {
        String user = m.getUser();
        String pass = m.getPwd();
        sessionHandler = new SessionHandler();
        MsgSession result;


        try {
            if (sessionHandler.startSession(user, pass)) {
                followerHandler = new FollowerHandler();
                opinionHandler = new OpinionHandler();
                photoHandler = new PhotoHandler();
                result = new MsgSession(STARTSESSION, null, user, true);
            } else {
                followerHandler = new FollowerHandler();
                opinionHandler = new OpinionHandler();
                photoHandler = new PhotoHandler();
                result = new MsgSession(STARTSESSION, USERCREATED, user, true);
            }
        } catch (WrongUserPasswordException e) {
            result = new MsgSession(STARTSESSION, WRONGPASSWORD, user, false);
        }
        System.out.println("start session func!");

        return result;
    }


    private MsgSession endSession(String user) {
        MsgSession result;
        result = new MsgSession(ENDSESSION, null, user, true);
        photoHandler = null;
        followerHandler = null;
        opinionHandler = null;
        sessionHandler = null;
        System.out.println("end session func!");

        return result;
    }

    private MsgPhoto addPhoto(String user, String follower, Photo photo, BufferedImage img) {
        MsgPhoto result;
        try {
            photoHandler.addPhoto(photo, img);
            result = new MsgPhoto(ADDPHOTO, null, user, follower, true);
        } catch (DuplicatePhotoException e) {
            result = new MsgPhoto(ADDPHOTO, DUPLICATEPHOTO, user, follower, false);
        }
        System.out.println("addphoto session func!");

        return result;
    }

    private MsgPhotoData allPhotoData(String user, String follower) {
        MsgPhotoData result;
        try {
            Iterable<PhotoData> photoDataList = photoHandler.getPhotosData(user);
            result = new MsgPhotoData(ALLPHOTOSDATA, null, user, follower, true, photoDataList);
        } catch (NoSuchUserException e) {
            result = new MsgPhotoData(ALLPHOTOSDATA, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgPhotoData(ALLPHOTOSDATA, NOTFOLLOWING, user, follower, false);
        }

        return result;
    }

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

        return result;
    }

    private MsgPhoto allPhotos(String user, String follower) {
        MsgPhoto result;
        ArrayList<Photo> photoList;
        try {
            photoList = (ArrayList<Photo>) photoHandler.getAllUserPhotos(user);
            result = new MsgPhoto(ALLPHOTOS, null, user, follower, true, photoList);
        } catch (NoSuchUserException e) {
            result = new MsgPhoto(ALLPHOTOS, NOSUCHUSER, user, follower, false);
        } catch (NotFollowingException e) {
            result = new MsgPhoto(ALLPHOTOS, NOTFOLLOWING, user, follower, false);
        }

        return result;
    }

    private MsgOpinion commentPhoto(String comment, String user, String follower, String photoID) {
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

        return result;
    }

    private MsgOpinion likePhoto(String user, String follower, String photoID) {
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

        return result;
    }

    private MsgOpinion dislikePhoto(String user, String follower, String photoID) {
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

        return result;
    }

    private MsgFollower followUser(String user, String follower) {
        MsgFollower result;
        try {
            followerHandler.addFollow(follower);
            result = new MsgFollower(FOLLOWUSER, null, user, follower, true);
        } catch (NoSuchUserException e) {
            result = new MsgFollower(FOLLOWUSER, NOSUCHUSER, user, follower, false);
        } catch (AlreadyFollowingException e) {
            result = new MsgFollower(FOLLOWUSER, ALREADYFOLLOWING, user, follower, false);
        }

        return result;
    }

    private MsgFollower unfollowUser(String user, String follower) {
        MsgFollower result;
        try {
            followerHandler.removeFollow(follower);
            result = new MsgFollower(UNFOLLOWUSER, null, user, follower, true);
        } catch (NoSuchUserException e) {
            result = new MsgFollower(UNFOLLOWUSER, NOSUCHUSER, user, follower, false);
        } catch (AlreadyNotFollowingException e) {
            result = new MsgFollower(UNFOLLOWUSER, ALREADYNOTFOLLOWING, user, follower, false);
        }

        return result;
    }

    public Message unpackAndTreatMsg(Message m) throws IOException {
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
                //TODO: verificar ioexception getbufferedimage
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
