package catalogs;

import catalogs_interface.ICatalogoUser;
import domain.User;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
    public boolean addUser(User u) throws FileNotFoundException, UnsupportedEncodingException {

        if(this.containsUser(u.getID()))
            return false;
        else {
            users.put(u.getID(), u);
            File fl = new File("File/" + u.getID());
            fl.mkdirs();
            PrintWriter writer = new PrintWriter("info", "UTF-8");
            writer.println("User: " + u.getID() + "/");
            writer.println("Pass: " + u.getPass());
            writer.close();

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
