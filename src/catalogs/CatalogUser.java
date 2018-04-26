package catalogs;

import catalogs.Interface.ICatalogUser;
import domain.Photo;
import domain.User;
import managers.RecoveryManager;

import java.awt.image.BufferedImage;
import java.io.*;

import java.security.GeneralSecurityException;
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
    private Map<String, User> users;
    private RecoveryManager recov;

    /**
     * Construtor
     */
    private CatalogUser() throws IOException, ClassNotFoundException, GeneralSecurityException {
        users = new HashMap<>();
        try {
            recov = new RecoveryManager();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("System couldn't recover!");
        }
        System.out.println("Starting recovering!");
        for (User u : recov.recUsers()) {
            this.users.put(u.getID(), u);
        }

    }

    public static CatalogUser getCatalog() throws IOException, ClassNotFoundException, GeneralSecurityException {
        if (CatalogUser.instance == null) {
            CatalogUser.instance = new CatalogUser();
        }
        return CatalogUser.instance;
    }

    @Override
    public boolean addUser(User u) throws IOException, GeneralSecurityException {

        if (this.containsUser(u.getID()))
            return false;
        else {
            recov.writeFile(u.getID(), u.getPass());
            users.put(u.getID(), u);
            this.updateUser(u);
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

    public void getUserPhotos(String userID, Iterable<Photo> uPh) throws IOException, ClassNotFoundException,
            GeneralSecurityException {
        recov.recPhotos(userID, uPh);
    }

    public void updateUser(User u) throws IOException, GeneralSecurityException {
        recov.backupUser(u);
    }

    public void saveImage(BufferedImage image, User u, String photoID, String extension) throws IOException,
            GeneralSecurityException {
        recov.saveImage(image, u, photoID, extension);
    }
}
