package handlers;

import domain.*;
import exceptions.*;


public class PhotoHandler extends GodHandler{

    public PhotoHandler(User curr) {
        this.currUser = curr;
    }

    public void addPhoto(Photo photo) throws DuplicatePhotoException {

        if (currUser.getPhoto(photo.getPhotoID()) != null)
            throw new DuplicatePhotoException();

        currUser.addPhoto(photo);

    }

    public Iterable<PhotoData> getPhotosData(String userID) throws NotFollowingException {

        User uID = currUser.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        return uID.getAllPhotosData();

    }

    public PhotoOpinion getPhotoOpinion(String userID, String photoID) throws NotFollowingException, NoSuchPhotoException {

        User uID = currUser.getFollow(userID);
        if (uID == null)
            throw new NotFollowingException();

        PhotoOpinion phO = uID.getPhotoOpinion(photoID);
        if(phO == null)
            throw new NoSuchPhotoException();

        return phO;

    }


    public Iterable<Photo> getAllUserPhotos(String userID) throws NotFollowingException {

        User uID = currUser.getFollow(userID);
        if(uID == null)
            throw new NotFollowingException();

        return uID.getAllPhotos();

    }



}
