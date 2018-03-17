package handlers;

import domain.*;
import exceptions.*;

public class OpinionHandler extends  GodHandler {


    public OpinionHandler(User curr) {
        this.curr = curr;
    }

    public boolean makeComment(String comment, String userID, String photoID) throws NotFollowingException{

        User uID = curr.getFollow(userID);
        if(uID == null)
            throw new NotFollowingException();

        return uID.makeComment(comment, userID, photoID);


    }

    public boolean addLike(String userID, String photoID) throws NotFollowingException, NoSuchPhotoException{

        User uID = curr.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);
        if (pID == null)
            throw new NoSuchPhotoException();

        return pID.addOpinion(uID.getID(), true);

    }

    public boolean addDisLike(String userID, String photoID) throws NotFollowingException, NoSuchPhotoException{

        User uID = curr.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);
        if (pID == null)
            throw new NoSuchPhotoException();

        return pID.addOpinion(uID.getID(), false);

    }
}