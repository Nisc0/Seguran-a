package message;

public class MsgFollower extends Message {

    private String followID;

    public MsgFollower(MsgType c_type, MsgError c_err, String user, String followID, String photoID) {
        super(c_type, c_err, user);
        this.followID = followID;

    }

    public String getFollowID() {
        return followID;
    }

}
