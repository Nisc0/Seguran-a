package domain;

import domain_interface.IPhoto;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.*;

/**
 * Classe que define fotos
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class Photo implements IPhoto {
    private String photoID;
    private String extension;
    private transient BufferedImage image; // é para mudar
    private Date datePub; //data de publicacao
    private Map<String, Boolean> opinion;
    private int likes;
    private int dislikes;
    private List<Comment> comments;

    public Photo(String name) {
        String[] fName = name.split(".");
        photoID = fName[0];
        extension = fName[1];
        opinion = new HashMap<>();
        comments = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        datePub = cal.getTime();
        likes = 0;
        dislikes = 0;
        setImage(image);
    }

    public void setImage(BufferedImage image) { this.image = image; }

    public String getPhotoID() {
        return photoID;
    }

    public String getExtension(){ return extension;}

    public void addComment(String user, String comm) {
        comments.add(new Comment(user, comm));
    }

    public boolean addOpinion(String user, boolean opi) {

        Boolean lastOpi = opinion.put(user, opi);
        if (lastOpi != null && lastOpi == opi)
            return false;

        if (opi) {
            if(lastOpi != null) {
                dislikes--;
            }
            likes++;
        } else {
            if(lastOpi != null) {
                likes--;
            }
            dislikes++;
        }
        return true;
    }

    public Date getDatePub() {
        return this.datePub;
    }

    public PhotoData makePhotoData() {
        return new PhotoData(photoID, datePub);
    }

    public PhotoOpinion makePhotoOpinion() {
        return new PhotoOpinion(photoID, likes, dislikes, comments);
    }

    //TODO
    public String toString(){
        return "ola";
    }

}
