package domain.Interface;

import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import domain.User;

public interface IUser {

    /**
     * Get User ID
     * @return the ID
     */
    String getID();

    /**
     * Get User password
     * @return the password
     */
    String getPass();

    /**
     * Verifies if the password is correct
     * @return true if correct password, false otherwise
     */
    boolean isSamePwd(String pwd);

    /**
     * Add a User to list of people the current User is following
     * @param u - the User to add
     */
    void addFollow(User u);

    /**
     * Remove a User to list of people the current User is following
     * @param u - the User to remove
     */
    void removeFollow(String userID);

    /**
     * Add a photo to the list of photos of the user
     * @param photo - the photo to add
     */
    void addPhoto(Photo photo);

    /**
     * Get a photo of the user through the ID
     * @param photoID -the ID of the photo
     * @return the desired Photo
     */
    Photo getPhoto(String photoID);

    /**
     * Get all the photos of the current User
     * @return a Iterable with all the photos
     */
    Iterable<Photo> getAllPhotos();

    /**
     * Get the basic information of all the photos of the current User
     * @return a Iterable with the desired information of all the photos
     */
    Iterable<PhotoData> getAllPhotosData();

    /**
     * Get the opinion of a photo of the current User
     * @param photoID - the photo of desired
     * @return all the opinions of the photo
     */
    PhotoOpinion getPhotoOpinion(String photoID);

    /**
     * Make a comment on the desired photo
     * @param com - the comment
     * @param uID - the User of made the comment
     * @param phID - the photo
     * @return true if possible to make a comment, false otherwise
     */
    boolean makeComment(String com, String uID, String phID);
}
