package server;

import domain.Photo;
import domain.User;
import exceptions.DuplicatePhotoException;
import exceptions.NoSuchPhotoException;
import exceptions.NoSuchUserException;
import exceptions.NotFollowingException;
import handlers.FollowerHandler;
import handlers.OpinionHandler;
import handlers.PhotoHandler;
import message.*;

import java.io.IOException;

public class ServerMessage {

    private  ServerMessage instance;
    private  FollowerHandler fllwrhdlr;
    private  OpinionHandler opnhdlr;
    private  PhotoHandler phthdlr;

    public ServerMessage(){
        sshdlr = new SessionHandler();
    }


    public MsgSession startSession(MsgSession msgS){
        try {
            String user = msgS.getUser();
            fllwrhdlr = new FollowerHandler(user);
            opnhdlr = new OpinionHandler(user);
            phthdlr = new PhotoHandler(user);
        }
        catch (){

        }
        return null;
    }


    public MsgPhoto addPhoto(Photo photo) throws DuplicatePhotoException {
        phthdlr.addPhoto(photo);
        return null;

    }

    public MsgPhoto allPhotoData(MsgPhoto m){
        return null;

    }

    public MsgPhoto photoOpinion(String userID, String photoID){
        try {
            phthdlr.getPhotoOpinion(userID, photoID);
        }
        catch (NotFollowingException e){

        }
        catch (NoSuchPhotoException f){

        }


        return null;

    }

    public MsgPhoto allPhotos(MsgPhoto m){
        return null;

    }

    public MsgOpinion commentPhoto(MsgOpinion m){
        return null;

    }

    public MsgOpinion likePhoto(MsgOpinion m){
        return null;

    }

    public MsgOpinion dislikePhoto(MsgOpinion m){
        return null;

    }

    public MsgFollower followUser(MsgFollower m) {
        return null;
    }

    public MsgFollower unfollowUser(MsgFollower m){
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

            case ADDPHOTO:
                mPhoto = (MsgPhoto) m;
                msg = addPhoto(mPhoto.getPhoto());
                break;
            case ALLPHOTOSDATA:
                mPhoto = (MsgPhoto) m;
                msg = allPhotoData(mPhoto);
                break;
            case PHOTOOPINION:
                mPhoto = (MsgPhoto) m;
                msg = photoOpinion(mPhoto);
                break;
            case ALLPHOTOS:
                mPhoto = (MsgPhoto) m;
                msg = allPhotos(mPhoto);
                break;
            case COMMENTPHOTO:
                mOpinion = (MsgOpinion) m;
                commentPhoto(mOpinion);
                break;
            case LIKEPHOTO:
                mOpinion = (MsgOpinion) m;
                msg = likePhoto(mOpinion);
                break;
            case DISLIKEPHOTO:
                mOpinion = (MsgOpinion)m;
                msg = dislikePhoto(mOpinion);
                break;
            case FOLLOWUSER:
                mFollower = (MsgFollower) m;
                msg = followUser(mFollower);
                break;
            case UNFOLLOWUSER:
                mFollower= (MsgFollower) m;
                msg = unfollowUser(mFollower);
                break;

            default:
                break;
        }
        return msg;

    }
}
