package domain;

import domain_interface.IUser;

import java.util.*;

/**
 * Classe que define utilizadores
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class User implements IUser {

    private String userID;
    private String password;
    private Map<String, User> follows;
    private Map<String, Photo> photos;


    public User(String id) {
        userID = id;
        follows = new HashMap<>();
        photos = new HashMap<>();

    }


    @Override
    public String getID() {
        return userID;
    }

    //////////////    FOLLOWS    /////////////////////

    @Override
    public void addFollow(User u) {
        follows.put(u.getID(), u);
    }

    @Override
    public void removeFollow(String userID) {
        follows.remove(userID);
    }

    @Override
    public User getFollow(String userID) {
        return follows.get(userID);
    }

    //////////////    PHOTOS    /////////////////////

    @Override
    public void addPhoto(Photo photo) {
        photos.put(photo.getPhotoID(), photo);
    }

    private boolean containsPhoto(String photoID) {
        return photos.containsKey(photoID);
    }

    @Override
    public Photo getPhoto(String photoID) {
        if (containsPhoto(photoID))
            return photos.get(photoID);
        else
            return null;
    }

    @Override
    public Iterable<Photo> getAllPhotos() {
        return photos.values();
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
        if (containsPhoto(photoID))
            return getPhoto(photoID).makePhotoOpinion();
        else
            return null;

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
