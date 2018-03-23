package domain.Interface;

import java.util.Date;

public interface IComment {

    /**
     * Get the ID of the User who made de commentary
     * @return the ID desired
     */
    String getUserID();

    /**
     * Get the comment made
     * @return the stored comment
     */
    String getComment();

    /**
     * Get the Date the comment was made
     * @return the Date stored
     */
    Date getDataPub();

}
