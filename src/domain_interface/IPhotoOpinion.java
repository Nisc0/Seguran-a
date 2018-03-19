package domain_interface;

import domain.Comment;

import java.util.List;

public interface IPhotoOpinion {

    /**
     * Obtem o ID da foto referente
     * @return o ID guardado
     */
    String getPhotoID();

    /**
     * Obtem o numero de Likes da foto referente
     * @return o numero de Likes
     */
    int getLikes();

    /**
     * Obtem o numero de DisLikes da foto referente
     * @return o numero de DisLikes
     */
    int getDislikes();

    /**
     * Obtem todos os comentarios da foto referente
     * @return uma lista com os comentarios
     */
    List<Comment> getComm();
}
