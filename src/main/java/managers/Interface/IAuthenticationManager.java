package managers.Interface;

import exceptions.WrongUserPasswordException;

public interface IAuthenticationManager {

    /**
     * Authenticate a User
     * @param name - the username of the user
     * @param pass - the password of the user
     * @return true if the user was successefully authenticate, false if the user was not found
     * @throws WrongUserPasswordException
     */
    boolean authenticate(String name, String pass) throws WrongUserPasswordException;
}
