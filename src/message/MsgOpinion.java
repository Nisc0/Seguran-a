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
    //todo check param
        return photoID;
    }

    public String getComment() {
        //todo check param
        return comment;
    }

    public boolean getOpinion(){
        //todo check param
        return opinion;
    }
}
