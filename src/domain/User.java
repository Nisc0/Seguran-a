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


    public User(String id, String pass) {
        userID = id;
        password = pass;
        follows = new HashMap<>();
        photos = new HashMap<>();

    }

    @Override
    public String getID() {
        return userID;
    }

    @Override
    public boolean isSamePwd(String pwd) { return password.equals(pwd); }

    //////////////    FOLLOWS    /////////////////////

    @Override
    public User getFollow(String userID) {
        return follows.get(userID);
    }

    @Override
    public void addFollow(User u) {
        follows.put(u.getID(), u);
    }

    @Override
    public void removeFollow(String userID) {
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
        return photos.values();
    }

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
