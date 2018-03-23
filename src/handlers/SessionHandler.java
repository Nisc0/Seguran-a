package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.WrongUserPasswordException;

public class SessionHandler extends GodHandler implements handlers.Interface.ISessionHandler {

    private static CatalogoUser catUser;

    public SessionHandler() {
        catUser = CatalogoUser.getCatalogo();
    }

    @Override
    public boolean startSession(String userID, String pass) throws WrongUserPasswordException {

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
