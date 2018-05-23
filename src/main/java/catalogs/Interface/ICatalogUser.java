package catalogs.Interface;

import domain.Photo;
import domain.User;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;

public interface ICatalogUser {

    /**
     * Add user to the System
     * @param u - User to add
     * @return true in success, false otherwise
     */
    boolean addUser(User u) throws IOException, GeneralSecurityException;

    /**
     * Verify if a user is registered in the System
     * @param userID - User to verify
     * @return true in success, false otherwise
     */
    boolean containsUser(String userID);

    /**
     * Get a user through the ID
     * @param userID - ID of the desired User
     * @return the User desired
     */
    User getUser(String userID);

    /**
     * Get all registered users
     * @return Iterable with the ID of all users
     */
    Iterable<String> getUsers();

    /**
     * Recover the photos of a given user
     * @param userID - ID of the user
     * @param uPh - Iterable with the photos before recovering
     */
    void getUserPhotos(String userID, Iterable<Photo> uPh) throws IOException, ClassNotFoundException,
            SecurityException, GeneralSecurityException;

    /**
     * Backsup the given User
     * @param u - the User to backup
     */
    void updateUser(User u) throws IOException, SecurityException, GeneralSecurityException;

    /**
     * Save a image in the System
     * @param image - the Image to save
     * @param u - the owner of the image
     * @param photoID - the ID of the photo
     * @param extension - the extension of the photo
     */
    void saveImage(BufferedImage image, User u, String photoID, String extension) throws IOException,
            GeneralSecurityException;


}
