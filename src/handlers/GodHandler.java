package handlers;

import domain.*;


public abstract class  GodHandler {

    protected static User currUser;


    protected User getCurrUser() {
        return this.currUser;
    }

    protected void setCurrUser(User u) {
        currUser = u;
    }

}
