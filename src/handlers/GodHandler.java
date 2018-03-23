package handlers;

import domain.*;


public abstract class  GodHandler implements handlers.Interface.IGodHandler {

    protected static User currUser;


    @Override
    public User getCurrUser() {
        return this.currUser;
    }

    protected void setCurrUser(User u) {
        currUser = u;
    }

}
