package handlers;

import domain.*;
import catalogs.*;
import exceptions.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class FollowerHandler extends GodHandler implements handlers.Interface.IFollowerHandler {

    private static CatalogUser catUser;

    public FollowerHandler(String userID) throws IOException, ClassNotFoundException, GeneralSecurityException {
        catUser = CatalogUser.getCatalog();
        setCurrUser(catUser.getUser(userID));
    }

    @Override
    public void addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException, IOException,
            GeneralSecurityException {

        User u = catUser.getUser(userID);

        if(u == null)
            throw new NoSuchUserException();

        if (currUser.isFollowing(userID))
            throw new AlreadyFollowingException();

        currUser.addFollow(u);
        catUser.updateUser(currUser);
    }

    @Override
    public void removeFollow(String userID) throws NoSuchUserException, AlreadyNotFollowingException, IOException,
            GeneralSecurityException {

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
