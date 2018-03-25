package handlers;

import domain.*;
import catalogs.*;
import exceptions.*;

public class FollowerHandler extends GodHandler implements handlers.Interface.IFollowerHandler {

    private static CatalogUser catUser;

    public FollowerHandler() {
        catUser = CatalogUser.getCatalog();
    }

    @Override
    public void addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException {

        User u = catUser.getUser(userID);

        if(u == null)
            throw new NoSuchUserException();

        if (currUser.isFollowing(userID))
            throw new AlreadyFollowingException();

        currUser.addFollow(u);
        catUser.updateUser(currUser);
    }

    @Override
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
