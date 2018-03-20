package server;

import message.Message;
import message.MsgSession;

public class ServerMessage {

    private static ServerMessage instance;

    private ServerMessage(){
    }

    public static ServerMessage getInstance(){
        return (instance == null)? instance = new ServerMessage() : instance;
    }


    public MsgSession startSession(MsgSession logMsg) {
        //stub
        return null;
    }

    public Message STARTSESSION {

    }
    public Message ADDPHOTO {

    }
    public Message ALLPHOTOSDATA{

    }
    public Message  PHOTOOPINION{

    }
    public Message ALLPHOTOS{

    }
    public Message COMMENTPHOTO{

    }
    public Message LIKEPHOTO{

    }
    public Message DISLIKEPHOTO{

    }
    public Message FOLLOWUSER {

    }
    public Message UNFOLLOWUSER{

    }
}
