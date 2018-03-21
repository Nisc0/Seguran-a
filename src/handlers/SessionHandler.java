package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.WrongUserPasswordException;

import java.io.IOException;

public class SessionHandler extends GodHandler {

    private static CatalogoUser catUser;

    public SessionHandler() throws IOException {
        catUser = CatalogoUser.getCatalogo();
    }

    public boolean startSession(String userID, String pass) throws WrongUserPasswordException, IOException {

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
