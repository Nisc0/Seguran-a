package message;

public class MsgSession extends Message {

    private String pass;

    public MsgSession(MsgType c_type, MsgError c_err, String user, boolean success) {
        super(c_type, c_err, user, null, success);
    }

    public MsgSession(MsgType c_type, MsgError c_err, String user, boolean success, String pass) {
        super(c_type, c_err, user,null, success);
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

}
