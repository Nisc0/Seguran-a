package catalogs;

import catalogs_interface.ICatalogoUser;
import domain.Photo;
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
    private CatalogoUser() {
        users = new HashMap<>();
        try {
            recov = new RecoveryManeger();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.print("Sistema não conseguiu recuperar");
        }

        for (User u: recov.recUsers()) {
            this.users.put(u.getID(), u);
        }
    }

    public static CatalogoUser getCatalogo(){
        if (CatalogoUser.instance == null){
            CatalogoUser.instance = new CatalogoUser();
        }
        return CatalogoUser.instance;
    }

    @Override
    public boolean addUser(User u) {

        if(this.containsUser(u.getID()))
            return false;
        else {
            try {
                recov.writeFile(u.getID(), u.getPass());
                users.put(u.getID(), u);
                this.updateUser(u);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

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

    public void getUserPhotos(String userID, Iterable<Photo> uPh) throws IOException {
        recov.recPhotos(userID, uPh);
    }

    public void updateUser(User u) throws IOException {
        recov.backupUser(u);
    }
}
