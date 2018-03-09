package domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que define catalogos de user
 * @author minunes
 */
public class CatalogoUser {
	
	private static CatalogoUser instance;
	private Map<String,User> users;
	
	/**
	 * Construtor
	 */
	private CatalogoUser() {
		users = new HashMap<>();
	}
	
	public static CatalogoUser getCatalogo() {
		if (instance == null){
			instance = new CatalogoUser();
		}
		return instance;
	}

	/**
	 * Adicionar um novo tipo de sensor a este catalogo
	 * @param tp - O tipo de sensor a adicionar
	 * @requires tp != null
	 */
	public boolean addUser(String userID) {
		
		if(containsUser(userID))
		    return false;
		else {
		    users.put(userID, new User (userID));
		    return true;
	    }
	}
	
	public boolean containsUser( String userID) {
		return users.constainsKey(userID);
	}
	
	public User getUser(String userID) {
		return users.get(userID);
	}

	/**
	 * Os nomes dos tipos de sensores existentes neste catalogo
	 * @return Uma colecao iteravel com os nomes dos tipos de sensores        
	 */
	public Iterable<String> getUsers() {
		return users.keySet();
	}

}
