package domain;

import domain_interface.IUser;

import java.io.Serializable;
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
    private transient Map<String, User> follows;
    private Map<String, Photo> photos;
    private Set<String> rec_follows;



    public User(String id, String pass) {
        userID = id;
        password = pass;
        follows = new HashMap<>();
        photos = new HashMap<>();
        rec_follows = new HashSet<>();

    }

    @Override
    public String getID() {
        return userID;
    }

    @Override
    public String getPass() { return password; }

    //////////////    FOLLOWS    /////////////////////

    @Override
    public User getFollow(String userID) {
        return follows.get(userID);
    }

    @Override
    public void addFollow(User u) {
        rec_follows.add(u.getID());
        follows.put(u.getID(), u);
    }

    @Override
    public void removeFollow(String userID) {
        rec_follows.remove(userID);
        follows.remove(userID);
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
        for(Photo ph : photos.values()){
            res.add(ph.clone());
        }
        return res;
    }

    /* mt possivelmente nao preciso
    @Override
    public Iterable<PhotoOpinion> getAllPhotoOpinion() {

        List<PhotoOpinion> res = new ArrayList<>();
        for (Photo ph : photos.values()) {
            res.add(ph.makePhotoOpinion());
        }
        return res;
    }
    */

    //////////////    TRATAMENTO DE OPINIIONS    /////////////////////

    @Override
    public boolean makeComment(String com, String uID, String phID) {

        Photo photo = getPhoto(phID);
        if (photo != null) {
            photo.addComment(uID, com);
            return true;
        }
        return false;
    }

}
