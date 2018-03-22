package handlers;

import domain.*;


public abstract class  GodHandler {

    protected User currUser;


    protected User getCurrUser() {
        return this.currUser;
    }

}
