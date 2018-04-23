package handlers;

import catalogs.CatalogUser;
import domain.*;
import exceptions.WrongUserPasswordException;
import managers.AuthenticationManager;

import java.io.IOException;

public class SessionHandler extends GodHandler implements handlers.Interface.ISessionHandler {

    private static CatalogUser catUser;
    private static AuthenticationManager autMan;

    public SessionHandler(String userID) throws IOException, ClassNotFoundException, SecurityException {
        catUser = CatalogUser.getCatalog();
        autMan = AuthenticationManager.getAuthenticater();
        setCurrUser(catUser.getUser(userID));

    }

    @Override
    public boolean startSession(String userID, String pass) throws WrongUserPasswordException, IOException,
            SecurityException {

            User u = catUser.getUser(userID);

            if (u == null) {
                return false;
            }
            else {
                return autMan.authenticate(userID, pass);
            }

    }

}
