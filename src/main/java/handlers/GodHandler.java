package handlers;

import domain.User;


public abstract class  GodHandler implements handlers.Interface.IGodHandler {

    protected User currUser;

    @Override
    public User getCurrUser() {
        return this.currUser;
    }

    protected void setCurrUser(User u) {
        currUser = u;
    }

}
