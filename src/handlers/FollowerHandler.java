package handlers;

import domain.*;
import catalogs.*;
import exceptions.*;

public class FollowerHandler extends GodHandler{

    private static CatalogoUser catUser;

    public FollowerHandler(User curr) {

        this.curr = curr;
        catUser = CatalogoUser.getCatalogo();

    }


    public boolean addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException {

        User u = catUser.getUser(userID);
        if(u == null)
            throw new NoSuchUserException();
        if (u == curr.getFollow(userID))
            throw new AlreadyFollowingException();

        curr.addFollow(u);
        return true;

    }


    public boolean removeFollow(String userID) throws  NoSuchUserException{

        User u = catUser.getUser(userID);
        if(u == null)
            throw new NoSuchUserException();

        curr.removeFollow(userID);
        return true;

    }

}
