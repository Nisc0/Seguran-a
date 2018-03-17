package handlers;

import domain.*;

public class FollowerHandler extends GodHandler{


    public FollowerHandler(User curr) {
        this.curr = curr;
    }

    public boolean addFollow(String userID) {

        User uID = curr.getFollow(userID);
        if (uID == null)
            return curr.addFollow(userID);
        else
            return false;
    }


    public boolean removeFollow(String userID) {

        User uID = curr.getFollow(userID);
        if (uID != null)
            return curr.removeFollow(userID);
        else
            return false;
    }

}
