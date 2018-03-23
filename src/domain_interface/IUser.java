package domain_interface;

import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;
import domain.User;

public interface IUser {

    /**
     * Obtem o ID do utilizador
     * @return o ID do utilizador
     */
    String getID();

    String getPass();

    /**
     * Verifica se a pass dada eh igual ah do utilizador
     * @return true se a pass dada eh igual ah do utilizador
     */
    boolean isSamePwd(String pwd);

    /**
     * Adiciona um utilizador à lista de pessoas que se está a seguir
     * @param u - o utilizador a adicionar
     */
    void addFollow(User u);

    /**
     * Remove um utilizador da lista de pessoas que se está a seguir
     * @param userID - o utilizador a remover
     */
    void removeFollow(String userID);

    /**
     * Obtem um utilizador que se está a seguir
     * @param userID - o ID do utilizador que queremos obter
     * @return o utilizador que se está a seguir
     */
    User getFollow(String userID);

    /**
     * Adiciona uma foto à lista de fotos do utilizador
     * @param photo - a foto que se quer adicionar
     */
    void addPhoto(Photo photo);

    /**
     * Obtem foto através do ID
     * @param photoID - o ID da foto que se quer
     * @return a foto desejada
     */
    Photo getPhoto(String photoID);

    /**
     * Obtem todas as fotos do utilizador
     * @return um iteravel com todas as fotos do utilizador
     */
    Iterable<Photo> getAllPhotos();

    /**
     * Obtem a informacao base de todas as fotos
     * @return um iteravel com a informacao mencionada, de todas as fotos
     */
    Iterable<PhotoData> getAllPhotosData();

    /**
     * Obtem todas as opinioes relativas à foto desejada
     * @param photoID - a foto a analisar
     * @return todas as opinioes da foto desejada
     */
    PhotoOpinion getPhotoOpinion(String photoID);

    /**
     * Faz um comentario na foto escolhida
     * @param com - o comentario a fazer
     * @param uID - o utilizador que fez o comentario
     * @param phID - a foto escolhida
     * @return true se possivel fazer o comentario, false caso contrario
     */
    boolean makeComment(String com, String uID, String phID);
}
