package message;

public class MsgSession extends Message {

    private String pwd;

    public MsgSession(MsgType c_type, MsgError c_err, String user, boolean success) {
        super(c_type, c_err, user, null, success);
    }

    public MsgSession(MsgType c_type, MsgError c_err, String user, boolean success, String pwd) {
        super(c_type, c_err, user, null, success);
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

}
