package handlers;

import catalogs.CatalogUser;
import domain.*;
import exceptions.*;

import java.awt.image.BufferedImage;


public class PhotoHandler extends GodHandler implements handlers.Interface.IPhotoHandler {

    private static CatalogUser catUser;

    public PhotoHandler(String userID) {
        catUser = CatalogUser.getCatalog();
        setCurrUser(catUser.getUser(userID));
    }

    @Override
    public void addPhoto(Photo photo, BufferedImage image) throws DuplicatePhotoException {

        if (currUser.getPhoto(photo.getPhotoID()) != null)
            throw new DuplicatePhotoException();

        currUser.addPhoto(photo);
        catUser.updateUser(currUser);
        catUser.saveImage(image, currUser, photo.getPhotoID(), photo.getExtension());

    }

    @Override
    public Iterable<PhotoData> getPhotosData(String userID) throws NoSuchUserException, NotFollowingException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
            if (!currUser.getID().equals(userID)) {
                throw new NotFollowingException();
            }

        return uID.getAllPhotosData();
    }

    @Override
    public PhotoOpinion getPhotoOpinion(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
            if (!currUser.getID().equals(userID)) {
                throw new NotFollowingException();
            }

        PhotoOpinion phO = uID.getPhotoOpinion(photoID);
        if(phO == null)
            throw new NoSuchPhotoException();

        return phO;
    }


    @Override
    public Iterable<Photo> getAllUserPhotos(String userID) throws NoSuchUserException, NotFollowingException {

        User uID = catUser.getUser(userID);

        if(uID == null)
            throw new NoSuchUserException();

        if(!currUser.isFollowing(userID))
                throw new NotFollowingException();

        Iterable<Photo> uPh = uID.getAllPhotos();
        catUser.getUserPhotos(userID, uPh);

        return uPh;
    }


}
