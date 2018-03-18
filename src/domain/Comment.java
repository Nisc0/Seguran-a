package domain;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe que define comentarios de fotos
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class Comment {
    private String userID;
    private String comment;
    private Date dataPub;

    public Comment(String userID, String comm) {
        this.userID = userID;
        this.comment = comm;
        Calendar cal = Calendar.getInstance();
        this.dataPub = cal.getTime();
    }

    public String getUserID() {
        return userID;
    }

    public String getComment() {
        return comment;
    }

    public Date getDataPub() {
        return dataPub;
    }
}
