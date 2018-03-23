package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.WrongUserPasswordException;

public class SessionHandler extends GodHandler {

    private static CatalogoUser catUser;

    public SessionHandler(User curr) {
        this.currUser = curr;
        catUser = CatalogoUser.getCatalogo();
    }

    public boolean startSession(String userID, String pass) throws WrongUserPasswordException {

        User u = catUser.getUser(userID);
        if (u == null) {
            catUser.addUser(new User(userID, pass));
            return false;
        }

        if (u.isSamePwd(pass))
            return true;

        throw new WrongUserPasswordException();

    }

}
