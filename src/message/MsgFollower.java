package message;

public class MsgFollower extends Message {

    private String followID;
    private String photoID;

    public MsgFollower(MsgType c_type, MsgError c_err, String user, String followID, String photoID) {
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
