package handlers;

import domain.*;
import catalogs.*;
import exceptions.*;

import java.io.IOException;

public class FollowerHandler extends GodHandler{

    private static CatalogoUser catUser;

    public FollowerHandler(User curr) throws IOException {

        catUser = CatalogoUser.getCatalogo();

    }


    public boolean addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException {

        User u = catUser.getUser(userID);
        if(u == null)
            throw new NoSuchUserException();
        if (u == getCurr().getFollow(userID))
            throw new AlreadyFollowingException();

        getCurr().addFollow(u);
        return true;

    }


    public boolean removeFollow(String userID) throws  NoSuchUserException {

        User u = catUser.getUser(userID);
        if(u == null)
            throw new NoSuchUserException();

        getCurr().removeFollow(userID);
        return true;

    }

}
