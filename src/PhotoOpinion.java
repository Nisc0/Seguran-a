import java.util.List;

public class PhotoOpinion {

    //private String photoID;
    private int likes;
    private int dislikes;
    private List<Comment> comm;

    public PhotoOpinion(String id, int likes, int dislikes, List<Comment> comm){
        this.photoID = id;
        this.likes = likes;
        this.dislikes = dislikes;
        this.comm = comm;
    }

}
