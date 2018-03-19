package message;

public class Msg_Photo extends Message {

    private String followID;
    private String photoID;

    public Msg_Photo(Msg_Type c_type, Msg_Error c_err, String user, String followID, String photoID) {
        super(c_type, c_err, user);
        this.followID = followID;
        this.photoID = photoID;
    }

    public String getFollowID() {
        return followID;
    }

    public String getPhotoID() {
        return photoID;
    }
}
