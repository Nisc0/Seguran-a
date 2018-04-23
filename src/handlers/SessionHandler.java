package handlers;

import catalogs.CatalogUser;
import domain.*;
import exceptions.WrongUserPasswordException;

import java.io.IOException;

public class SessionHandler extends GodHandler implements handlers.Interface.ISessionHandler {

    private static CatalogUser catUser;

    public SessionHandler(String userID) throws IOException, ClassNotFoundException, SecurityException {
        catUser = CatalogUser.getCatalog();
        setCurrUser(catUser.getUser(userID));
    }

    @Override
    public boolean startSession(String userID, String pass) throws WrongUserPasswordException, IOException,
            SecurityException {

        User u = catUser.getUser(userID);
        if (u == null) {
            u = new User(userID, pass);
            catUser.addUser(u);
            setCurrUser(u);
            return false;
        }

        if (u.isSamePwd(pass)) {
            setCurrUser(u);
            return true;
        }

        throw new WrongUserPasswordException();

    }

}
