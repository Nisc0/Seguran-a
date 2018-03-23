package domain.Interface;

import domain.PhotoData;
import domain.PhotoOpinion;

import java.awt.image.BufferedImage;
import java.util.*;

public interface IPhoto {

    /**
     * Get the ID of the photo
     * @return the ID
     */
    String getPhotoID();

    /**
     * Get the extension of the Photo
     * @return the extension
     */
    String getExtension();

    /**
     * Get the image of the photo
     * @return the image
     */
    BufferedImage getImage();

    /**
     * Get the date the Photo was added
     * @return when the photo was added
     */
    Date getDatePub();

    /**
     * Add a comment to the photo
     * @param user - the user who made the comment
     * @param comm - the comment to add
     */
    void addComment(String user, String comm);

    /**
     * Add an opinion to the photo
     * @param user - the user who made the opinion
     * @param opi - the type of the opinion, true if positive, false otherwise
     * @return true if possoble to add opinion, false otherwise
     */
    boolean addOpinion(String user, boolean opi);

    /**
     * Object with the ID and date of the photo
     * @return the PhotoData
     */
    PhotoData makePhotoData();

    /**
     * Object with the opinions on the photo
     * @return the Photo Opinion
     */
    PhotoOpinion makePhotoOpinion();

}
