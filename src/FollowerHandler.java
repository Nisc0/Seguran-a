public class FollowerHandler extends GodHandler{


    public boolean addFollow(String userID) {

        User uID = curr.getFollow(userID);
        if (uID == null)
            return curr.addFollow(userID, uID);
        else
            return false;
    }


    public boolean removeFollow(String userID) {

        User uID = curr.getFollow(userID);
        if (uID != null)
            return curr.removeFollow(userID, uID);
        else
            return false;
    }

}
