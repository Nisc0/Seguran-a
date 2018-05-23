package domain.Interface;

import domain.Comment;

import java.util.List;

public interface IPhotoOpinion {

    /**
     * Get the ID of the photo
     * @return the ID
     */
    String getPhotoID();

    /**
     * Get the number of Likes
     * @return the number of Likes
     */
    int getLikes();

    /**
     * Get the number of DisLikes
     * @return the number of DisLikes
     */
    int getDislikes();

    /**
     * Get the comments of the photo
     * @return a List with the comments
     */
    List<Comment> getComm();
}
