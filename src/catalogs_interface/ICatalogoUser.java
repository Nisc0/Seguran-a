package catalogs_interface;

import domain.User;

public interface ICatalogoUser {

    /**
     * Adiciona um utilizador ao sistema
     * @param userID - o ID do utilizador a adicionar
     * @return true se adicionado com sucesso, false caso contrario
     */
    boolean addUser(String userID);

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
