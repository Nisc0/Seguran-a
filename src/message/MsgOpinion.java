package message;

public class MsgOpinion extends Message {

    private String photoID;
    private String comment;
    private boolean opinion;

    public MsgOpinion(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID, String comment) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.comment = comment;
    }

    public MsgOpinion(MsgType c_type, MsgError c_err, String user, String followID, boolean success, boolean opinion) {
        super(c_type, c_err, user, followID, success);
        this.opinion = opinion;
    }

    public String getPhotoID() {
        return photoID;
    }

    public String getComment() {
        return comment;
    }

    public boolean getOpinion(){ return opinion; }
}
