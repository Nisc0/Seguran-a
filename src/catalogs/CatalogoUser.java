package catalogs;

import catalogs_interface.ICatalogoUser;
import domain.User;

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

    /**
     * Construtor
     */
    private CatalogoUser() {
        users = new HashMap<>();
    }

    public static CatalogoUser getCatalogo() {
        if (CatalogoUser.instance == null){
            CatalogoUser.instance = new CatalogoUser();
        }
        return CatalogoUser.instance;
    }

    @Override
    public boolean addUser(String userID) {

        if(containsUser(userID))
            return false;
        else {
            users.put(userID, new User(userID));
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
