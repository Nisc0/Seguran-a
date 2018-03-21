package server;

import catalogs.CatalogoUser;
import domain.Photo;
import domain.PhotoData;
import domain.User;
import exceptions.*;
import handlers.FollowerHandler;
import handlers.OpinionHandler;
import handlers.PhotoHandler;
import message.*;

import java.io.IOException;
import java.util.Iterator;
se
public class ServerMessage {

    private CatalogoUser catUser;
    private ServerMessage instance;
    private FollowerHandler fllwrhdlr;
    private OpinionHandler opnhdlr;
    private PhotoHandler phthdlr;
    private SessionHandler sshdlr;

    public ServerMessage(){
        this.catUser = CatalogoUser.getCatalogo();
    }


    public MsgSession startSession(MsgSession msgS){
        sshdlr = new SessionHandler();

        //todo
        try {
            if (sshdlr.startSession(msgS.getUser(),msgS.getPass())) {

                String user = msgS.getUser();
                fllwrhdlr = new FollowerHandler(catUser.getUser(user));
                opnhdlr = new OpinionHandler(catUser.getUser(user));
                phthdlr = new PhotoHandler(catUser.getUser(user));
            }
        }
        catch (WrongUserPasswordException e){

        }
        return null;
    }

    //todo
    public MsgPhoto addPhoto(Photo photo) {
        try {
            phthdlr.addPhoto(photo);
        } catch (DuplicatePhotoException e) {
            e.printStackTrace();
        }
        return null;

    }

    //todo
    public Iterable<PhotoData> allPhotoData(String userID){
        try {
            Iterable<PhotoData> list = phthdlr.getPhotosData(userID);
        } catch (NotFollowingException e) {
            e.printStackTrace();
        }
        return null;

    }

    //todo
    public MsgPhoto photoOpinion(String userID, String photoID){
        MsgPhoto result;
        try {
            phthdlr.getPhotoOpinion(userID, photoID);
        }
        catch (NotFollowingException e){

        }
        catch (NoSuchPhotoException f){

        }
        return null;

    }

    //todo
    public Iterable<Photo> allPhotos(String userID) {
        Iterable<Photo> result = null;
        try {
            result = phthdlr.getAllUserPhotos(userID);
        } catch (NotFollowingException e) {

        }
        return result;

    }

    //todo
    public MsgOpinion commentPhoto(String comment, String userID, String photoID){
        try {
            opnhdlr.makeComment(comment, userID, photoID);
        } catch (NotFollowingException e) {
            e.printStackTrace();
        } catch (NoSuchPhotoException e) {
            e.printStackTrace();
        }
        return null;

    }

    //todo
    public MsgOpinion likePhoto(String userID, String photoID){
        try {
            opnhdlr.addLike(userID, photoID);
        } catch (NotFollowingException e) {
            e.printStackTrace();
        } catch (NoSuchPhotoException e) {
            e.printStackTrace();
        } catch (AlreadyLikedException e) {
            e.printStackTrace();
        }

        return null;

    }

    //todo
    public MsgOpinion dislikePhoto(String userID, String photoID){
        try {
            opnhdlr.addDisLike(userID, photoID);
        } catch (NotFollowingException e) {
            e.printStackTrace();
        } catch (NoSuchPhotoException e) {
            e.printStackTrace();
        } catch (AlreadyDisLikedException e) {
            e.printStackTrace();
        }

        return null;

    }

    //todo
    public MsgFollower followUser(String userID) {
        try {
            fllwrhdlr.addFollow(userID);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (AlreadyFollowingException e) {

        }
        return null;
    }

    //todo
    public MsgFollower unfollowUser(String userID){
        try {
            fllwrhdlr.removeFollow(userID);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        return null;

    }



    public Message unpackAndTreatMsg (Message m) throws DuplicatePhotoException {
        Message msg;
        MsgType tpMsg = m.getC_type();
        MsgFollower mFollower;
        MsgOpinion mOpinion;
        MsgPhoto mPhoto;
        MsgSession mSession;

        switch (tpMsg) {

            case STARTSESSION:
                break;

            case ADDPHOTO:
                mPhoto = (MsgPhoto) m;
                msg = addPhoto(mPhoto.getPhoto());
                break;

            case ALLPHOTOSDATA:
                mPhoto = (MsgPhoto) m;
                Iterable<PhotoData> list = allPhotoData(mPhoto.getUser());
                mPhoto.addPhotoDataList(list);
                msg = mPhoto;
                break;

            case PHOTOOPINION:
                mPhoto = (MsgPhoto) m;
                msg = photoOpinion(mPhoto.getfollowID(),mPhoto.getPhotoID());
                break;

            case ALLPHOTOS:
                mPhoto = (MsgPhoto) m;
                Iterable<Photo> listPhoto = allPhotos(mPhoto.getUser());
                mPhoto.addPhotoList(listPhoto);
                msg = mPhoto;
                break;

            case COMMENTPHOTO:
                mOpinion = (MsgOpinion) m;
                commentPhoto(mOpinion.getComment(),mOpinion.getfollowID(),mOpinion.getPhotoID());
                break;

            case LIKEPHOTO:
                mOpinion = (MsgOpinion) m;
                msg = likePhoto(mOpinion.getfollowID(),mOpinion.getPhotoID());
                break;

            case DISLIKEPHOTO:
                mOpinion = (MsgOpinion)m;
                msg = dislikePhoto(mOpinion.getfollowID(),mOpinion.getPhotoID());
                break;

            case FOLLOWUSER:
                mFollower = (MsgFollower) m;
                msg = followUser(mFollower.getfollowID());
                break;

            case UNFOLLOWUSER:
                mFollower= (MsgFollower) m;
                msg = unfollowUser(mFollower.getfollowID());
                break;

            default:
                break;
        }
        return msg;

    }
}
