package domain;

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
	}
	
	public String obterUserID() {
		return userID;
	}
	
	public boolean addFollows(String userID, User user) {

		if(containsFollows(String userID))
		    return false;
		else {
	        follows.put(userID, user):
	        return true;
	}
	
	public boolean containsFollows(String userID) {
		return follows.containdKey(userID);
	}
	
	public User getFollows(String userID) {
		return follows.get(userID);
	}

}
