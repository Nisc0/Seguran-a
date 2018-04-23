package handlers.Interface;

import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import exceptions.DuplicatePhotoException;
import exceptions.NoSuchPhotoException;
import exceptions.NoSuchUserException;
import exceptions.NotFollowingException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IPhotoHandler extends IGodHandler {

    /**
     * Add a photo do the list of photos of the current User
     * @param photo - the photo
     * @param image - the image
     * @throws DuplicatePhotoException
     */
    void addPhoto(Photo photo, BufferedImage image) throws DuplicatePhotoException, IOException, GeneralSecurityException;

    /**
     * Get the basic information of all the photos of the desired User
     * @param userID - the user desired
     * @return a Iterable with the desired information of all the photos
     * @throws NoSuchUserException
     * @throws NotFollowingException
     */
    Iterable<PhotoData> getPhotosData(String userID) throws NoSuchUserException, NotFollowingException;

    /**
     * Get the opinion of a photo of the desired User
     * @param userID - the user
     * @param photoID - the photo
     * @return all the opinions of the photo
     * @throws NoSuchUserException
     * @throws NotFollowingException
     * @throws NoSuchPhotoException
     */
    PhotoOpinion getPhotoOpinion(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException;

    /**
     * Get all the photos of the desired User
     * @param userID - the User
     * @return a Iterable with all the photos
     * @throws NoSuchUserException
     * @throws NotFollowingException
     */
    Iterable<Photo> getAllUserPhotos(String userID) throws NoSuchUserException, NotFollowingException, IOException,
            ClassNotFoundException, GeneralSecurityException;
}
