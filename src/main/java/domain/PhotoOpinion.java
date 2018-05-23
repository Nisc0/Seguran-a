package domain;

import domain.Interface.IPhotoOpinion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que define a opiniao duma foto
 *
 * @author 47823
 * @author 47829
 * @author 47840
 */

public class PhotoOpinion implements IPhotoOpinion, Serializable {

    private String photoID;
    private int likes;
    private int dislikes;
    private List<Comment> comm;

    public PhotoOpinion(String id, int likes, int dislikes, List<Comment> comm){
        this.photoID = id;
        this.likes = likes;
        this.dislikes = dislikes;
        this.comm = new ArrayList<>(comm);
    }

    @Override
    public String getPhotoID() {
        return photoID;
    }

    @Override
    public int getLikes() {
        return likes;
    }

    @Override
    public int getDislikes() {
        return dislikes;
    }

    @Override
    public List<Comment> getComm() {
        return comm;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("photoID: " + photoID + ", ");
        sb.append("likes: " + likes + ", ");
        sb.append("dislikes: " + dislikes + ", ");
        sb.append("comments: " + comm);
        return sb.toString();
    }
}
