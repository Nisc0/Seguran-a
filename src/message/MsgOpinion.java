package message;

public class MsgOpinion extends Message {

    private String photoID;
    private String comment;


    public MsgOpinion(MsgType c_type, MsgError c_err, String user, String followID, String photoID, String comment) {
        super(c_type, c_err, user, followID);
        this.photoID = photoID;
        this.comment = comment;

    }


    public String getPhotoID() {
        return photoID;
    }

    public String getComment() {
        return comment;
    }
}
