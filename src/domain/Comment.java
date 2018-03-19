package domain;

import domain_interface.IComment;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe que define comentarios de fotos
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class Comment implements IComment {
    private String userID;
    private String comment;
    private Date dataPub;

    public Comment(String userID, String comm) {
        this.userID = userID;
        this.comment = comm;
        Calendar cal = Calendar.getInstance();
        this.dataPub = cal.getTime();
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Date getDataPub() {
        return dataPub;
    }
}
