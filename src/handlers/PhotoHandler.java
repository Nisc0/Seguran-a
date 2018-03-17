package handlers;

import domain.*;
import java.util.ArrayList;

public class PhotoHandler extends GodHandler{

    public PhotoHandler(User curr) {
        this.curr = curr;
    }

    public boolean addPhoto(Photo photo) {
        return curr.addPhoto(photo);
    }

    public Iterable<PhotoData> getPhotosData(String userID) {

        User uID = curr.getFollow(userID);
        if (uID != null) {
            return uID.getAllPhotoData();
        } else
            return new ArrayList<>(); // ver se devemos mandar exception

        //TODO:Nao deves.. tens -> esta escrito no enunciado!!!
        //caso contrário, devolve um erro;
        //caso contrário, devolve um erro;

    }

    public PhotoOpinion getPhotoOpinion(String userID, String photoID) {

        User uID = curr.getFollow(userID);
        if (uID != null) {
            return uID.getPhotoOpinion(photoID);
        } else
            return null;
    }

    public Iterable<Photo> getAllUserPhotos(String userID) {

        User uID = curr.getFollow(userID);
        if(uID != null)
            return uID.getAllPhotos();
        else
            return new ArrayList<>();

    }



}
