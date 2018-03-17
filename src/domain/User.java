package domain;

import catalogs.CatalogoUser;
import java.util.*;

/**
 * Classe que define utilizadores
 *
 * @author
 * 47823
 * 47829
 * 47840
 */

public class User {

    private String userID;
    private String password;
    private Map<String, User> follows;
    private Map<String, Photo> photos;


    public User(String id) {
        userID = id;
        follows = new HashMap<>();
        photos = new HashMap<>();

    }

    //////////////    FOLLOWS    /////////////////////

    public void addFollow(User u) {
        follows.put(u.getID(), u);
    }

    public void removeFollow(String userID) {
        follows.remove(userID);
    }

    public User getFollow(String userID) {
        return follows.get(userID);
    }

    //////////////    PHOTOS    /////////////////////

    public void addPhoto(Photo photo) {
        photos.put(photo.getPhotoID(), photo);
    }

    private boolean containsPhoto(String photoID) {
        return photos.containsKey(photoID);
    }

    public Photo getPhoto(String photoID) {
        if (containsPhoto(photoID))
            return photos.get(photoID);
        else
            return null;
    }

    public Iterable<Photo> getAllPhotos() {
        return photos.values();
    }

    public String getID() {
        return userID;
    }

    //////////////    TRATAMENTO DE PHOTOS    /////////////////////


    public Iterable<PhotoData> getAllPhotoData() {

        List<PhotoData> res = new ArrayList<>();
        for (Photo ph : photos.values()) {
            res.add(ph.makePhotoData());
        }
        return res;
    }


    public PhotoOpinion getPhotoOpinion(String photoID) {
        if (containsPhoto(photoID))
            return getPhoto(photoID).makePhotoOpinion();
        else
            return null;

    }

    public Iterable<PhotoOpinion> getAllPhotoOpinion() {

        List<PhotoOpinion> res = new ArrayList<>();
        for (Photo ph : photos.values()) {
            res.add(ph.makePhotoOpinion());
        }
        return res;
    }

    //////////////    TRATAMENTO DE OPINIIONS    /////////////////////

    public boolean makeComment(String com, String uID, String phID) {

        if (phID != null) {
            Photo photo = getPhoto(phID);
            if (photo != null) {
                photo.addComment(uID, com);
                return true;
            }
        }
        return false;
    }

}
