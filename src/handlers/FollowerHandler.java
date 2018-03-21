package handlers;

import domain.*;
import catalogs.*;
import exceptions.*;

// alterar o nome das variaveis

public class FollowerHandler extends GodHandler{

    private static CatalogoUser catUser;

    public FollowerHandler(User curr) {

        this.currUser = curr;
        catUser = CatalogoUser.getCatalogo();

    }


    public void addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException {

        User u = catUser.getUser(userID);

        if(u == null)
            throw new NoSuchUserException();

        if (u == currUser.getFollow(userID))
            throw new AlreadyFollowingException();

        currUser.addFollow(u);

    }


    public void removeFollow(String userID) throws NoSuchUserException, AlreadyNotFollowingException {

        User u = catUser.getUser(userID);

        if(u == null)
            throw new NoSuchUserException();

        if(null == currUser.getFollow(userID)){
            throw new AlreadyNotFollowingException();
        }

        currUser.removeFollow(userID);

    }

}
