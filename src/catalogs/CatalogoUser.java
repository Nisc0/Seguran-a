package catalogs;

import catalogs_interface.ICatalogoUser;
import domain.User;
import handlers.RecoveryManeger;

import java.io.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que define utilizadores
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class CatalogoUser implements ICatalogoUser {

    private static CatalogoUser instance;
    private Map<String,User> users;
    private RecoveryManeger recov;

    /**
     * Construtor
     */
    private CatalogoUser() throws IOException {
        users = new HashMap<>();
        recov = new RecoveryManeger();
        recov.simpleRecovery();
    }

    public static CatalogoUser getCatalogo() throws IOException {
        if (CatalogoUser.instance == null){
            CatalogoUser.instance = new CatalogoUser();
        }
        return CatalogoUser.instance;
    }

    @Override
    public boolean addUser(User u) throws IOException {

        if(this.containsUser(u.getID()))
            return false;
        else {
            recov.writeFile(u.getID(), u.getPass());
            users.put(u.getID(), u);

            return true;
        }
    }

    @Override
    public boolean containsUser(String userID) {
        return users.containsKey(userID);
    }

    @Override
    public User getUser(String userID) {
        return users.get(userID);
    }


    @Override
    public Iterable<String> getUsers() {
        return users.keySet();
    }

}
