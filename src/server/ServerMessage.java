package server;

import catalogs.CatalogoUser;
import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import exceptions.*;
import handlers.*;
import message.*;

import static message.MsgError.*;
import static message.MsgType.FOLLOWUSER;

public class ServerMessage {

    private CatalogoUser catUser;
    private FollowerHandler followerHandler;
    private OpinionHandler opinionHandler;
    private PhotoHandler photoHandler;
    private SessionHandler sessionHandler;

    public ServerMessage(){
        this.catUser = CatalogoUser.getCatalogo();
    }


    public MsgSession startSession(MsgSession msgS){
        sessionHandler = new SessionHandler(catUser.getUser(msgS.getUser()));

        //todo
        try {
            if (sessionHandler.startSession(msgS.getUser(),msgS.getPass())) {

                String user = msgS.getUser();
                followerHandler = new FollowerHandler(catUser.getUser(user));
                opinionHandler = new OpinionHandler(catUser.getUser(user));
                photoHandler = new PhotoHandler(catUser.getUser(user));
            }
        }
        catch (WrongUserPasswordException e){

        }
        return null;
    }

    //todo
    public MsgPhoto addPhoto(Photo photo) {
        try {
            photoHandler.addPhoto(photo);
        } catch (DuplicatePhotoException e) {
            e.printStackTrace();
        }
        return null;

    }

    //todo
    public Iterable<PhotoData> allPhotoData(String userID){
        try {
            Iterable<PhotoData> list = photoHandler.getPhotosData(userID);
        } catch (NotFollowingException e) {
            e.printStackTrace();
        }
        return null;

    }

    //fixme
    public MsgPhoto photoOpinion(MsgPhoto m){
        MsgPhoto result;

            try {
                PhotoOpinion op = photoHandler.getPhotoOpinion(m.getFollowI(), m.getPhotoID());
                result = new MsgPhoto(m.getC_type(), null, m.getUser(), m.getFollowI(), m.getPhotoID(), m.getPhoto());
                result.addPhotoOpinion(op);
            }

            catch (NotFollowingException e) {
                result = new MsgPhoto(m.getC_type(), NOTFOLLOWING, m.getUser(), m.getFollowI(), m.getPhotoID(), m.getPhoto());
            }

            catch (NoSuchPhotoException e) {
                result = new MsgPhoto(m.getC_type(), NOSUCHPHOTO, m.getUser(), m.getFollowI(), m.getPhotoID(), m.getPhoto());
            }

        return result;

    }

    //todo
    public Iterable<Photo> allPhotos(String userID) {
        Iterable<Photo> result = null;

        try {
            result = photoHandler.getAllUserPhotos(userID);
        } catch (NotFollowingException e) {

        }
        return result;

    }

    //fixme
    public MsgOpinion commentPhoto(MsgOpinion m){
        MsgOpinion result;
        try {
            opinionHandler.makeComment(m.getComment(), m.getFollowI(), m.getPhotoID());
            result = new MsgOpinion(m.getC_type(), null, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(),m.getOpinion());
        }

        catch (NotFollowingException e) {
            result = new MsgOpinion(m.getC_type(), NOTFOLLOWING, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(),m.getOpinion());
        }

        catch (NoSuchPhotoException e) {
            result = new MsgOpinion(m.getC_type(), NOSUCHPHOTO, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(),m.getOpinion());
        }

        return result;

    }

    //fixme
    public MsgOpinion likePhoto(MsgOpinion m){
            MsgOpinion result;

        try {
            opinionHandler.addLike(m.getFollowI(), m.getPhotoID());
            result = new MsgOpinion(m.getC_type(), null, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(),m.getOpinion());
        }

        catch (NotFollowingException e) {
            result = new MsgOpinion(m.getC_type(), NOTFOLLOWING, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(),m.getOpinion());
        }

        catch (NoSuchPhotoException e) {
            result = new MsgOpinion(m.getC_type(), NOSUCHPHOTO, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(),m.getOpinion());
        }

        catch (AlreadyLikedException e) {
            result = new MsgOpinion(m.getC_type(), ALREADYLIKED, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(),m.getOpinion());
        }

        return result;
    }

    //fixme
    public MsgOpinion dislikePhoto(MsgOpinion m){
        MsgOpinion result;
        try {
            opinionHandler.addDisLike(m.getFollowI(), m.getPhotoID());
            result = new MsgOpinion(m.getC_type(), null, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(), m.getOpinion());
        }

        catch (NotFollowingException e) {
            result = new MsgOpinion(m.getC_type(),NOTFOLLOWING, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(), m.getOpinion());
        }

        catch (NoSuchPhotoException e) {
            result = new MsgOpinion(m.getC_type(),NOSUCHPHOTO, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(), m.getOpinion());
        }

        catch (AlreadyDislikedException e) {
            result = new MsgOpinion(m.getC_type(),ALREADYDISLIKED, m.getUser(),m.getFollowI(),m.getPhotoID(),m.getComment(), m.getOpinion());
        }

        return result;
    }

    //fixme
    public MsgFollower followUser(String user, String follower) {
        MsgFollower result;
        try {
            followerHandler.addFollow(follower);
            result= new MsgFollower(FOLLOWUSER,null, user, follower,true);
        }

        catch (NoSuchUserException e) {
            result = new MsgFollower(FOLLOWUSER, NOSUCHUSER, user, follower,false);
        }

        catch (AlreadyFollowingException e) {
            result = new MsgFollower(FOLLOWUSER, ALREADYFOLLOWING, user, follower,true);
        }
        return result;
    }


    //fixme
    public MsgFollower unfollowUser(String user, String follower){
        MsgFollower result;
        try {
            followerHandler.removeFollow(follower);
            result = new MsgFollower(FOLLOWUSER,null, user, follower,true);
        }

        catch (NoSuchUserException e) {
            result = new MsgFollower(FOLLOWUSER, NOSUCHUSER, user, follower, false);
        }

        catch (AlreadyNotFollowingException e) {
            result = new MsgFollower(FOLLOWUSER, ALREADYNOTFOLLING, user, follower, true);
            e.printStackTrace();
        }
        return result;
    }



    public Message unpackAndTreatMsg (Message m) {
        Message msgresult = null;
        MsgType tpMsg = m.getC_type();
        MsgFollower mFollower;
        MsgOpinion mOpinion;
        MsgPhoto mPhoto;

        switch (tpMsg) {

            case STARTSESSION:
                break;

            case ADDPHOTO:
                mPhoto = (MsgPhoto) m;
                msgresult = addPhoto(mPhoto.getPhoto());
                break;

            case ALLPHOTOSDATA:
                mPhoto = (MsgPhoto) m;
                Iterable<PhotoData> list = allPhotoData(mPhoto.getUser());
                mPhoto.addPhotoDataList(list);
                msgresult = mPhoto;
                break;

            case PHOTOOPINION:
                mPhoto = (MsgPhoto) m;
                msgresult = photoOpinion(mPhoto.getFollowI(),mPhoto.getPhotoID());
                break;

            case ALLPHOTOS:
                mPhoto = (MsgPhoto) m;
                Iterable<Photo> listPhoto = allPhotos(mPhoto.getUser());
                mPhoto.addPhotoList(listPhoto);
                msgresult = mPhoto;
                break;

            case COMMENTPHOTO:
                mOpinion = (MsgOpinion) m;
                commentPhoto(mOpinion.getComment(),mOpinion.getFollowI(),mOpinion.getPhotoID());
                break;

            case LIKEPHOTO:
                mOpinion = (MsgOpinion) m;
                msgresult = likePhoto(mOpinion.getFollowI(),mOpinion.getPhotoID());
                break;

            case DISLIKEPHOTO:
                mOpinion = (MsgOpinion)m;
                msgresult = dislikePhoto(mOpinion);
                break;

            case FOLLOWUSER:
                mFollower = (MsgFollower) m;
                msgresult = followUser(mFollower.getUser(),mFollower.getFollowID());
                break;

            case UNFOLLOWUSER:
                mFollower= (MsgFollower) m;
                msgresult = unfollowUser(mFollower.getUser(),mFollower.getFollowID());
                break;

            default:
                break;
        }
        return msgresult;

    }
}
