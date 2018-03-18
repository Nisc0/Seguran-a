package domain;

import java.util.Date;

/**
 * Classe que define a informacao base duma foto
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class PhotoData {

    private String photoID;
    private Date date;

    public PhotoData(String id, Date date){
        this.photoID = id;
        this.date = date;
    }

    public String getPhotoID() {
        return photoID;
    }

    public Date getDate() {
        return date;
    }
}
