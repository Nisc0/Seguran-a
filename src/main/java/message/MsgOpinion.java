package message;

public class MsgOpinion extends Message {

    private String photoID;
    private String comment;
    private boolean opinion;

    public MsgOpinion(MsgType c_type, MsgError c_err, String user, String followID, boolean success) {
        super(c_type, c_err, user, followID, success);
    }

    public MsgOpinion(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID, String comment) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.comment = comment;
    }

    public MsgOpinion(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID, boolean opinion) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.opinion = opinion;
    }

    public String getPhotoID() {
        return (photoID != null) ?photoID:null;
    }

    public String getComment() {
        return (comment != null) ?comment:null;
    }

    public boolean getOpinion(){
        return opinion;
    }
}
