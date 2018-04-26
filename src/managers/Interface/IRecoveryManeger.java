package managers.Interface;

import domain.Photo;
import domain.User;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

public interface IRecoveryManeger {

    /**
     * Write the User and respective password on a file
     * @param user - the User
     * @param pass - the Password
     * @throws IOException
     */
    void writeFile(String user, String pass) throws IOException;

    /**
     * Backup a User on the disk
     * @param u - the User
     * @throws IOException
     */
    void backupUser(User u) throws IOException, SecurityException, GeneralSecurityException;

    void simpleRecovery();

    /**
     * Recovers the Users
     * @return a Iterable with all the recovered Users
     */
    Iterable<User> recUsers() throws IOException, ClassNotFoundException, GeneralSecurityException;

    /**
     * Recovers the images in disk to the photos of the desired User
     * @param userID - the User
     * @param uPh - the Iterable of the photos to be added the images
     * @throws IOException
     */
    void recPhotos(String userID, Iterable<Photo> uPh) throws IOException, ClassNotFoundException,
            GeneralSecurityException;

    /**
     * Backups a image on the disk
     * @param image - the image to be added
     * @param u - the user who posted the image
     * @param photoID - the ID of the photo associated with the image
     * @param extension - the extension of the image
     */
    void saveImage(BufferedImage image, User u, String photoID, String extension) throws IOException,
            GeneralSecurityException;
}
