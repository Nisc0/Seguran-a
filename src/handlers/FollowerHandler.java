package handlers;

import domain.*;
import catalogs.*;
import exceptions.*;

import java.io.IOException;

// alterar o nome das variaveis

public class FollowerHandler extends GodHandler{

    private static CatalogoUser catUser;

    public FollowerHandler() {
        catUser = CatalogoUser.getCatalogo();
    }

    public void addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException {

        User u = catUser.getUser(userID);

        if(u == null)
            throw new NoSuchUserException();

        if (currUser.isFollowing(userID))
            throw new AlreadyFollowingException();

        currUser.addFollow(u);
        catUser.updateUser(currUser);
    }

    public void removeFollow(String userID) throws NoSuchUserException, AlreadyNotFollowingException {

        User u = catUser.getUser(userID);

        if(u == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID)){
            throw new AlreadyNotFollowingException();
        }

        currUser.removeFollow(userID);
        catUser.updateUser(currUser);
    }

}
