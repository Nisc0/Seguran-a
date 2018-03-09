import java.util.*;

/**
 * Classe que define as fotos
 * @author
 */
public class Photo {
    private String photoID;
    private Map<String,Boolean> opinion;
    private int likes;
    private int dislikes;
    private Date dataPub; //data de publicacao
    private List<Comment> comments;

    public Photo (String id){
        photoID = id;
        opinion = new HashMap<>();
        comments = new ArrayList<>();
    }

    public String obterPhotoID() {
        return photoID;
    }

    public void addComment(String user, String comm){
        Comment novoComm = new Comment (user, comm);
        comments.add(novoComm);
    }
}