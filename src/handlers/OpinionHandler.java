package handlers;

import domain.*;

public class OpinionHandler extends  GodHandler {


    public OpinionHandler(User curr) {
        this.curr = curr;
    }

    public boolean makeComment(String comment, String userID, String photoID) {
        User uID = curr.getFollow(userID);
        return (uID != null) && uID.makeComment(comment, userID, photoID);
    }

    public boolean addLike(String userID, String photoID) {
        User uID = curr.getFollow(userID);
        if (uID != null) {
            Photo pID = uID.getPhoto(photoID);
            if (pID != null) {
                return pID.addOpinion(uID.getID(), true);
            }
        }
        return false;
    }

    public boolean addDisLike(String userID, String photoID) {
        User uID = curr.getFollow(userID);
        if (uID != null) {
            Photo pID = uID.getPhoto(photoID);
            if (pID != null) {
                return pID.addOpinion(uID.getID(), false);
            }
        }
        return false;
    }
}