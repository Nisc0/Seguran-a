package catalogs;

import catalogs.Interface.ICatalogUser;
import domain.Photo;
import domain.User;
import handlers.RecoveryManager;

import java.awt.image.BufferedImage;
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

public class CatalogUser implements ICatalogUser {

    private static CatalogUser instance;
    private Map<String,User> users;
    private RecoveryManager recov;

    /**
     * Construtor
     */
    private CatalogUser() {
        users = new HashMap<>();
        try {
            recov = new RecoveryManager();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.print("System couldn't recover!");
        }
        System.out.println("Start recovering!");
        for (User u: recov.recUsers()) {
            this.users.put(u.getID(), u);
        }

    }

    public static CatalogUser getCatalog(){
        if (CatalogUser.instance == null){
            CatalogUser.instance = new CatalogUser();
        }
        return CatalogUser.instance;
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

    public void getUserPhotos(String userID, Iterable<Photo> uPh) {

        try {
            recov.recPhotos(userID, uPh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User u) {
            recov.backupUser(u);
    }


    public void saveImage(BufferedImage image, User u, String photoID, String extension) {
        recov.saveImage(image, u, photoID, extension);
    }
}
