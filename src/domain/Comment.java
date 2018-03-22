package domain;

import domain_interface.IComment;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe que define comentarios de fotos
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class Comment implements IComment, Serializable {
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

    @Override
    public String toString(){
        return ("userID: " + userID + ", " + comment);
    }

    private void setDate(Date dt) {
        this.dataPub = dt;
    }

    @Override
    public Comment clone() {
        Comment res = new Comment(this.userID, this.comment);
        res.setDate(this.dataPub);
        return res;
    }
}
