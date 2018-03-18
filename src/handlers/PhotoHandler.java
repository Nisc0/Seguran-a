package handlers;

import domain.*;
import exceptions.*;
import java.util.ArrayList;

public class PhotoHandler extends GodHandler{

    public PhotoHandler(User curr) {
        this.curr = curr;
    }

    public void addPhoto(Photo photo) throws DuplicatePhotoException {

        if (curr.getPhoto(photo.getPhotoID()) != null)
            throw new DuplicatePhotoException();

        curr.addPhoto(photo);

    }

    public Iterable<PhotoData> getPhotosData(String userID) throws NotFollowingException {

        User uID = curr.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        return uID.getAllPhotosData();

    }

    public PhotoOpinion getPhotoOpinion(String userID, String photoID) throws NotFollowingException {

        User uID = curr.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        return uID.getPhotoOpinion(photoID);

    }


    public Iterable<Photo> getAllUserPhotos(String userID) throws NotFollowingException {

        User uID = curr.getFollow(userID);
        if(uID == null)
            throw new NotFollowingException();

        return uID.getAllPhotos();

    }



}
