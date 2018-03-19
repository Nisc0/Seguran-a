package message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private Msg_Type c_type; // tipo de mensagem
    private Msg_Error c_err; // tipo de erro
    private String user; // o curr user

    public Message(Msg_Type c_type, Msg_Error c_err, String user) {
        this.c_type = c_type;
        this.c_err = c_err;
        this.user = user;
    }

    public Msg_Type getC_type() {
        return c_type;
    }

    public Msg_Error getC_err() {
        return c_err;
    }

    public String getUser() {
        return user;
    }

}

