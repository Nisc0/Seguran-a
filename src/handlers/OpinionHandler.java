package handlers;

import domain.*;
import exceptions.*;

public class OpinionHandler extends  GodHandler {


    public OpinionHandler(User curr) {
        this.curr = curr;
    }

    public boolean makeComment(String comment, String userID, String photoID) throws NotFollowingException, NoSuchPhotoException {

        User uID = curr.getFollow(userID);
        if(uID == null)
            throw new NotFollowingException();

        if (!uID.makeComment(comment, userID, photoID))
            throw new NoSuchPhotoException();

        return true;


    }

    public boolean addLike(String userID, String photoID) throws NotFollowingException, NoSuchPhotoException, AlreadyLikedException{

        User uID = curr.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);
        if (pID == null)
            throw new NoSuchPhotoException();

        if (!pID.addOpinion(uID.getID(), true))
            throw new  AlreadyLikedException();

        return true;

    }

    public boolean addDisLike(String userID, String photoID) throws NotFollowingException, NoSuchPhotoException, AlreadyDislikedException {

        User uID = curr.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);
        if (pID == null)
            throw new NoSuchPhotoException();

        if (!pID.addOpinion(uID.getID(), false))
            throw new AlreadyDislikedException();

        return true;


    }
}