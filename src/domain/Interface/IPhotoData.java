package domain.Interface;

import java.util.Date;

public interface IPhotoData {

    /**
     * Get the ID of the photo
     * @return the ID stored
     */
    String getPhotoID();

    /**
     * Get the date of the photo
     * @return the date stored
     */
    Date getDate();
}
