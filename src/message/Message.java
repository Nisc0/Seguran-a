package message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private MsgType c_type; // tipo de mensagem
    private MsgError c_err; // tipo de erro
    private String user; // o curr user
    private String followID; //o outro user

    public Message(MsgType c_type, MsgError c_err, String user, String followID) {
        this.c_type = c_type;
        this.c_err = c_err;
        this.user = user;
        this.followID = followID;
    }

    public MsgType getC_type() {
        return c_type;
    }

    public MsgError getC_err() {
        return c_err;
    }

    public String getUser() {
        return user;
    }

    public String getfollowID(){ return followID; }

}

