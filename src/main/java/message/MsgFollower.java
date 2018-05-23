package message;

public class MsgFollower extends Message {

    public MsgFollower(MsgType c_type, MsgError c_err, String user, String followID, boolean success) {
        super(c_type, c_err, user, followID, success);
    }
}
