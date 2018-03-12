import java.sql.Date;
import java.util.*;

/**
 * Classe que define utilizadores
 * @author
 */
public class User {
	private String userID;
	private Map<String, User> follows;
	private Map<String, Photo> photos;

	public User (String id){
		userID = id;
		follows = new HashMap<>();
		photos = new HashMap<>();
	}

    //////////////    PHOTOS    /////////////////////

    public boolean addPhoto(Photo photo) {

        if (containsPhoto(photo.getPhotoID()))
            return false;
        else {
            photos.put(photo.getPhotoID(), photo);
            return true;
        }
    }

    public boolean containsPhoto(String photoID) {
        return photos.containsKey(photoID);
    }

    public Photo getPhoto(String photoID) {
        if (containsPhoto(photoID))
            return photos.get(photoID);
        else
            return null;
    }

    public Collection<Photo> getAllPhotos() {
        return photos.values();
    }

	public String obterUserID() {
		return userID;
	}

    //////////////    FOLLOWS    /////////////////////

	public boolean addFollows(String userID, User user) {

		if(containsFollows(userID))
		    return false;
		else {
            follows.put(userID, user);
            return true;
        }
	}

	public boolean containsFollows(String userID) {
		return follows.containsKey(userID);
	}

	public User getFollows(String userID) {
		return follows.get(userID);
	}

	//////////////    TRATAMENTO DE PHOTOS    /////////////////////

	public Iterable<Photo> getFollowPhoto(String userID) {

		User uID = getFollows(userID);
		if (uID != null)
			return uID.getAllPhotos();

		else
			return null;
	}

    /**
	public Iterable<Data<String, Date>> getAllPhotoData() {

		List<Data<String, Date>> res;

		res = new ArrayList<Data<String, Date>>();
		for(Photo ph : uID.getAllPhotos()) {
			res.add(ph.getDataPhoto());
		}

		return res;

	}

	public Iterable<Opinion<Integer, Integer, List<Comment>>> getAllPhotoOpinion() {

		uID = getFollows(userID);
		List<Opinion<Integer, Integer, List<Comment>>> res;

		if(uID != null) {
			res = new ArrayList<Opinion<Integer, Integer, List<Comment>>>();
			for(Photo ph : uID.getAllPhotos()) {
				res.add(ph.getOpinionPhoto());
			}
		}

		else
			return null;
	}
    **/

	public boolean makeComment(String com, String uID, String phID) {

		if (phID != null) {
			Photo photo = getPhoto(phID);
			if (photo != null) {
				photo.addComment(uID, com);
				return true;
			}
		}
		return false;
	}

}