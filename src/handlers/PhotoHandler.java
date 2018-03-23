package handlers;

import catalogs.CatalogoUser;
import domain.*;
import exceptions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class PhotoHandler extends GodHandler{

    private static CatalogoUser catUser;

    public PhotoHandler() {
        catUser = CatalogoUser.getCatalogo();
    }

    public void addPhoto(Photo photo, BufferedImage image) throws DuplicatePhotoException {

        if (currUser.getPhoto(photo.getPhotoID()) != null)
            throw new DuplicatePhotoException();

        currUser.addPhoto(photo);
        catUser.updateUser(currUser);
        catUser.saveImage(image, currUser, photo.getPhotoID(), photo.getExtension());

    }

    public Iterable<PhotoData> getPhotosData(String userID) throws NoSuchUserException, NotFollowingException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
            if (currUser.getID() != userID) {
                throw new NotFollowingException();
            }

        return uID.getAllPhotosData();
    }

    public PhotoOpinion getPhotoOpinion(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
            if (currUser.getID() != userID) {
                throw new NotFollowingException();
            }

        PhotoOpinion phO = uID.getPhotoOpinion(photoID);
        if(phO == null)
            throw new NoSuchPhotoException();

        return phO;
    }


    public Iterable<Photo> getAllUserPhotos(String userID) throws NoSuchUserException, NotFollowingException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
            if (currUser.getID() != userID) {
                throw new NotFollowingException();
            }

        Iterable<Photo> uPh = uID.getAllPhotos();
        catUser.getUserPhotos(userID, uPh);

        return uPh;
    }


}
