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
    private Map<String, User> follows;
    private Map<String, Photo> photos;
    private static CatalogoUser catUser;

    public User(String id) {
        userID = id;
        follows = new HashMap<>();
        photos = new HashMap<>();
        catUser = CatalogoUser.getCatalogo();
    }

    //////////////    FOLLOWS    /////////////////////

    public boolean addFollow(String userID) {

        if (!containsFollow(userID)) {
            User u = catUser.getUser(userID);
            if (u != null) {
                follows.put(userID, u);
                return true;
            }
        }
            return false;

    }

    public boolean removeFollow(String userID) {

        if (containsFollow(userID)) {
            follows.remove(userID);
            return true;
        } else {
            return false;
        }
    }

    private boolean containsFollow(String userID) {
        return follows.containsKey(userID);
    }

    public User getFollow(String userID) {
        return follows.get(userID);
    }

    //////////////    PHOTOS    /////////////////////

    public boolean addPhoto(Photo photo) {

        if (containsPhoto(photo.getPhotoID()))
            return false;
        else {
            photos.put(photo.getPhotoID(), photo);
            return true;
        }
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
