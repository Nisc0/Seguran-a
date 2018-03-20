package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.WrongUserPasswordException;

public class SessionHandler extends GodHandler {

    private static CatalogoUser catUser;

    public SessionHandler() {
        this.curr = curr;
        catUser = CatalogoUser.getCatalogo();
    }

    public boolean startSession(String userID, String pass) throws WrongUserPasswordException {

        User u = catUser.getUser(userID);
        if (u == null) {
            catUser.addUser(new User(userID, pass));
            return false;
        }
        //VVV estamos a dar a pass a este handler VVV
        if (u.getPass().equals(pass))
            return true;

        throw new WrongUserPasswordException();

    }
}
