package domain;

import domain.Interface.IUser;
import managers.RecoveryManager;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * Classe que define utilizadores
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class User implements IUser, Serializable {

    private String userID;
    private String password;
    private Map<String, Photo> photos;
    private Set<String> rec_follows;
    private RecoveryManager recov;


    public User(String id, String pass) throws IOException, GeneralSecurityException {
        userID = id;
        password = pass;
        photos = new HashMap<>();
        rec_follows = new HashSet<>();
        RecoveryManager recov = new RecoveryManager();
        recov.backupUser(this);
    }

    @Override
    public String getID() {
        return userID;
    }

    @Override
    public String getPass() { return password; }

    @Override
    public boolean isSamePwd(String pwd) {
        return password.equals(pwd);
    }

    //////////////    FOLLOWS    /////////////////////

    @Override
    public void addFollow(User u) {
        rec_follows.add(u.getID());
    }

    @Override
    public void removeFollow(String userID) {
        rec_follows.remove(userID);
    }

    public boolean isFollowing(String u) {
        return rec_follows.contains(u) || u.equals(userID);
    }

    //////////////    PHOTOS    /////////////////////

    @Override
    public Photo getPhoto(String photoID) {
        return photos.get(photoID);
    }

    @Override
    public void addPhoto(Photo photo) {
        photos.put(photo.getPhotoID(), photo);
    }

    //////////////    TRATAMENTO DE PHOTOS    /////////////////////

    @Override
    public Iterable<PhotoData> getAllPhotosData() {

        List<PhotoData> res = new ArrayList<>();
        for (Photo ph : photos.values()) {
            res.add(ph.makePhotoData());
        }
        return res;
    }

    @Override
    public PhotoOpinion getPhotoOpinion(String photoID) {

        Photo ph = photos.get(photoID);
        if (ph != null)
            return ph.makePhotoOpinion();
        else
            return null;

    }

    @Override
    public Iterable<Photo> getAllPhotos() {
        ArrayList<Photo> res = new ArrayList<>();
        for (Photo ph : photos.values()) {
            res.add(ph.clone());
        }
        return res;
    }

    //////////////    TRATAMENTO DE OPINIONS    /////////////////////

    @Override
    public boolean makeComment(String com, String uID, String phID) {

        Photo photo = getPhoto(phID);
        if (photo != null) {
            photo.addComment(uID, com);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ("userID: " + userID + ", ") +
                "pass: " + password + ", ";
    }

}
