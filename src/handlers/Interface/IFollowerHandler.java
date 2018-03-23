package handlers.Interface;

import exceptions.AlreadyFollowingException;
import exceptions.AlreadyNotFollowingException;
import exceptions.NoSuchUserException;

public interface IFollowerHandler extends IGodHandler {

    /**
     * Add a User to list of people the current User is following
     * @param userID - the User to add to the list
     * @throws NoSuchUserException
     * @throws AlreadyFollowingException
     */
    void addFollow(String userID) throws NoSuchUserException, AlreadyFollowingException;

    void removeFollow(String userID) throws NoSuchUserException, AlreadyNotFollowingException;
}
