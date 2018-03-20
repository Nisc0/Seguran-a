package handlers;

import domain.*;
import catalogs.*;
import exceptions.*;

public class FollowerHandler extends GodHandler{

    private static CatalogoUser catUser;

    public FollowerHandler(User curr) {

        this.currUser = curr;
        catUser = CatalogoUser.getCatalogo();

    }


    public boolean addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException {

        User u = catUser.getUser(userID);
        if(u == null)
            throw new NoSuchUserException();
        if (u == currUser.getFollow(userID))
            throw new AlreadyFollowingException();

        currUser.addFollow(u);
        return true;

    }


    public boolean removeFollow(String userID) throws  NoSuchUserException {

        User u = catUser.getUser(userID);
        if(u == null)
            throw new NoSuchUserException();

        currUser.removeFollow(userID);
        return true;

    }

}
