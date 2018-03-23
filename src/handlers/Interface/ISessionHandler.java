package handlers.Interface;

import exceptions.WrongUserPasswordException;

public interface ISessionHandler extends IGodHandler {

    /**
     * Starts a session for the client
     * @param userID - the ID of the client
     * @param pass - the passod of the client
     * @return true if the was a login, false if it was a register
     * @throws WrongUserPasswordException
     */
    boolean startSession(String userID, String pass) throws WrongUserPasswordException;
}
