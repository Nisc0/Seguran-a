package domain;

import domain_interface.IPhotoData;

import java.util.Date;

/**
 * Classe que define a informacao base duma foto
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class PhotoData implements IPhotoData {

    private String photoID;
    private Date date;

    public PhotoData(String id, Date date){
        this.photoID = id;
        this.date = date;
    }

    @Override
    public String getPhotoID() {
        return photoID;
    }

    @Override
    public Date getDate() {
        return date;
    }
}