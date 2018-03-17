package domain;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe que define as fotos
 * @author
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

}
