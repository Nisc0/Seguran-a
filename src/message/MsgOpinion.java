package message;

public class MsgOpinion extends Message {

    private String followID;
    private String photoID;
    private String comment;


    public MsgOpinion(MsgType c_type, MsgError c_err, String user, String followID, String photoID, String comment) {
        super(c_type, c_err, user);
        this.followID = followID;
        this.photoID = photoID;
        this.comment = comment;

    }

    public String getFollowID() {
        return followID;
    }

    public String getPhotoID() {
        return photoID;
    }

    public String getComment() {
        return comment;
    }
}
