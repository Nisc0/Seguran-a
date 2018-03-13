import java.util.*;


/**
 * Classe que define as fotos
 * @author
 */
public class Photo {
    private String photoID;
    private Date datePub; //data de publicacao
    private Map<String,Boolean> opinion;
    private int likes;
    private int dislikes;
    private List<Comment> comments;

    public Photo (String id){
        photoID = id;
        opinion = new HashMap<>();
        comments = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        datePub = cal.getTime();
        likes = 0;
        dislikes = 0;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void addComment(String user, String comm){
        Comment novoComm = new Comment (user, comm);
        comments.add(novoComm);
    }

    //TODO: tem de se verificar se o user pertence aos followers (mas n aqui!)
    public boolean addOpinion(String user, boolean opi){
        boolean lastOpi;
        lastOpi = opinion.put(user, opi);
        if(lastOpi == opi){
            return false;
        }

        if (opi) {
            likes++;
        } else {
            dislikes++;
        }
        return true;
    }

    public Date getDatePub() {
        return this.datePub;
    }

    public PhotoData makeDataPhoto() {
        return new PhotoData(photoID, datePub);
    }

    public PhotoOpinion makeOpinionPhoto() {
        return new PhotoOpinion(photoID, likes, dislikes, comments);
    }



}
