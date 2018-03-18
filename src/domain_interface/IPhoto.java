package domain_interface;

import domain.PhotoData;
import domain.PhotoOpinion;

import java.util.*;

public interface IPhoto {

    /**
     * Obtem o ID da foto
     * @return o ID da foto
     */
    String getPhotoID();

    /**
     * Adiciona um comentario à foto
     * @param user - utilizador que fez o comentario
     * @param comm - o comentario a adicionar
     */
    void addComment(String user, String comm);

    /**
     * Adiciona uma opiniao à foto
     * @param user - utilizador que fez a opiniao
     * @param opi - tipo da opiniao, true se positiva, false se negativa
     * @return true se possivel adicionar opiniao, false caso contraio
     */
    boolean addOpinion(String user, boolean opi);

    /**
     * Obtem a data de criacao da foto
     * @return a data de criacao da foto
     */
    Date getDatePub();

    /**
     * Reune o ID e a data de criacao da foto
     * @return objeto com o ID e data de criacao da foto
     */
    PhotoData makePhotoData();

    /**
     * Reune toda a informacao sobre as opinioes da foto
     * @return objeto com as opinioes da foto
     */
    PhotoOpinion makePhotoOpinion();


}
