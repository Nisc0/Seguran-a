package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.*;

import java.io.IOException;

public class OpinionHandler extends  GodHandler {

    private static CatalogoUser catUser;

    public OpinionHandler(User curr) {
        this.currUser = curr;
        catUser = CatalogoUser.getCatalogo();
    }

    public void makeComment(String comment, String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException {

        User uID = currUser.getFollow(userID);

        if(catUser.getUser(userID) == null)
            throw new NoSuchUserException();

        if(uID == null)
            throw new NotFollowingException();

        if (!uID.makeComment(comment, userID, photoID))
            throw new NoSuchPhotoException();

        catUser.updateUser(currUser);
    }

    public void addLike(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException, AlreadyLikedException {

        User uID = currUser.getFollow(userID);

        if(catUser.getUser(userID) == null)
            throw new NoSuchUserException();

        if (uID == null)
            throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);

        if (pID == null)
            throw new NoSuchPhotoException();

        if (!pID.addOpinion(uID.getID(), true))
            throw new  AlreadyLikedException();

        catUser.updateUser(currUser);
    }

    public void addDisLike(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException, AlreadyDislikedException {

        User uID = currUser.getFollow(userID);

        if(catUser.getUser(userID) == null)
            throw new NoSuchUserException();

        if (uID == null)
            throw new NotFollowingException();

        Photo pID = uID.getPhoto(photoID);
        if (pID == null)
            throw new NoSuchPhotoException();

        if (!pID.addOpinion(uID.getID(), false))
            throw new AlreadyDislikedException();

        catUser.updateUser(currUser);
    }

}