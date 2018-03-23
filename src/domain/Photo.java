package domain;

import com.sun.org.apache.xpath.internal.operations.Bool;
import domain_interface.IPhoto;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.*;

/**
 * Classe que define fotos
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class Photo implements IPhoto, Serializable {
    private String photoID;
    private String extension;
    private transient BufferedImage image; // é para mudar
    private Date datePub; //data de publicacao
    private Map<String, Boolean> opinion;
    private int likes;
    private int dislikes;
    private List<Comment> comments;

    public Photo(String name) {
        String[] fName = name.split("\\.");
        System.out.println(name);
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getPhotoID() {
        return photoID;
    }

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

    @Override
    public Photo clone() {
        Photo res = new Photo(this.photoID);
        res.likes = this.likes;
        res.dislikes = this.dislikes;
        for (Comment c : comments) {
            res.comments.add(c.clone());
        }
        for (String s : this.opinion.keySet()) {
            res.opinion.put(s, this.opinion.get(s));
        }

        res.datePub = this.getDatePub();

        return res;
    }

    public String getExtension() {
        return extension;
    }
}
