package message;

public class Msg_Session extends Message {

    private String pass;

    public Msg_Session(Msg_Type c_type, Msg_Error c_err, String user, String pass) {
        super(c_type, c_err, user);
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }
}
