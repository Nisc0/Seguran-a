package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.*;

import java.io.IOException;

public class OpinionHandler extends  GodHandler {

    private static CatalogoUser catUser;

    public OpinionHandler() {
        catUser = CatalogoUser.getCatalogo();
    }

    public void makeComment(String comment, String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID)) {
                throw new NotFollowingException();
        }

        if (!uID.makeComment(comment, currUser.getID(), photoID))
            throw new NoSuchPhotoException();

        catUser.updateUser(uID);
    }

    public void addLike(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException, AlreadyLikedException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
                throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);

        if (pID == null)
            throw new NoSuchPhotoException();

        if (!pID.addOpinion(currUser.getID(), true))
            throw new  AlreadyLikedException();

        catUser.updateUser(uID);
    }

    public void addDisLike(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException, AlreadyDislikedException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
                throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);
        if (pID == null)
            throw new NoSuchPhotoException();

        if (!pID.addOpinion(currUser.getID(), false))
            throw new AlreadyDislikedException();

        catUser.updateUser(uID);
    }

}