package domain;

import java.util.List;

/**
 * Classe que define a opiniao duma foto
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class PhotoOpinion {

    private String photoID;
    private int likes;
    private int dislikes;
    private List<Comment> comm;

    public PhotoOpinion(String id, int likes, int dislikes, List<Comment> comm){
        this.photoID = id;
        this.likes = likes;
        this.dislikes = dislikes;
        this.comm = comm;
    }

    public String getPhotoID() {
        return photoID;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public List<Comment> getComm() {
        return comm;
    }
}
