package message;

public class MsgSession extends Message {

    private String pass;

    public MsgSession(MsgType c_type, MsgError c_err, String user, String pass, boolean success) {
        super(c_type, c_err, user,null, success);
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

}
