package message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private MsgType c_type; // tipo de mensagem
    private MsgError c_err; // tipo de erro
    private String user; // o curr user
    private String followID; //o outro user
    private boolean success; //a operaçao teve sucesso ou nao

    public Message(MsgType c_type, MsgError c_err, String user, String followID, boolean success) {
        this.c_type = c_type;
        this.c_err = c_err;
        this.user = user;
        this.followID = followID;
        this.success = success;
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

    public String getFollowID(){
        return followID;
    }

    public boolean getSuccess() {
        return success;
    }
}

