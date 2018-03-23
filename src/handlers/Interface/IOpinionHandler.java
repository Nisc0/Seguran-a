package handlers.Interface;

import exceptions.*;

public interface IOpinionHandler extends IGodHandler {

    /**
     *  Make a comment on the desired photo of the targeted User
     * @param comment - the comment
     * @param userID - the User
     * @param photoID - the photo
     * @throws NoSuchUserException
     * @throws NotFollowingException
     * @throws NoSuchPhotoException
     */
    void makeComment(String comment, String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException;

    /**
     * Add a Like to a photo of a User
     * @param userID - the user
     * @param photoID - the photo
     * @throws NoSuchUserException
     * @throws NotFollowingException
     * @throws NoSuchPhotoException
     * @throws AlreadyLikedException
     */
    void addLike(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException, AlreadyLikedException;

    /**
     * Add a DisLike to a photo of a User
     * @param userID - the user
     * @param photoID - the photo
     * @throws NoSuchUserException
     * @throws NotFollowingException
     * @throws NoSuchPhotoException
     * @throws AlreadyDislikedException
     */
    void addDisLike(String userID, String photoID) throws NoSuchUserException, NotFollowingException, NoSuchPhotoException, AlreadyDislikedException;
}
