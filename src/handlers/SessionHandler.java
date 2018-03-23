package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.WrongUserPasswordException;

public class SessionHandler extends GodHandler {

    private static CatalogoUser catUser;

    public SessionHandler() {
        catUser = CatalogoUser.getCatalogo();
    }

    public boolean startSession(String userID, String pass) throws WrongUserPasswordException {

        User u = catUser.getUser(userID);
        boolean res;

        if (u == null) {
            u = new User(userID, pass);
            catUser.addUser(u);
            setCurrUser(u);
            return false;
        }

        //VVV estamos a dar a pass a este handler VVV
        if (u.getPass().equals(pass))
            return true;

        throw new WrongUserPasswordException();

    }

}
