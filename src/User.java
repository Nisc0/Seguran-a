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

    public Iterable<Photo> getAllPhotos() {
        return photos.values();
    }

	public String obterUserID() {
		return userID;
	}

	//////////////    TRATAMENTO DE PHOTOS    /////////////////////


    
	public Iterable<PhotoData> getAllPhotoData() {

		List<PhotoData> res = new ArrayList<PhotoData>();
		for(Photo ph : photos) {
			res.add(ph.makePhotoData());
		}
		
		return res;

	}

	public Iterable<PhotoOpinion> getAllPhotoOpinion() {

		List<PhotoOpinion> res = new ArrayList<PhotoOpinion>;
		    for(Photo ph : uID.getAllPhotos()) {
			res.add(ph.makePhotoOpinion());
		}
		return res;

	}

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
