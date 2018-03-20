package catalogs_interface;

import domain.User;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface ICatalogoUser {

    /**
     * Adiciona um utilizador ao sistema
     * @param u - o utilizador a adicionar
     * @return true se adicionado com sucesso, false caso contrario
     */
    boolean addUser(User u) throws FileNotFoundException, UnsupportedEncodingException;

    /**
     * verifica se um utilizador está registado
     * @param userID - o ID do utilizador que se quer verificar
     * @return true se está registado, false caso contrario
     */
    boolean containsUser(String userID);

    /**
     * Obtem um utilizador atraves do ID respetivo
     * @param userID - o ID do utilizador desejado
     * @return o utilizador desejado
     */
    User getUser(String userID);

    /**
     * Obtem todos os utilizadores registados
     * @return um iteravel com os utilizadores registados
     */
    Iterable<String> getUsers();

}
