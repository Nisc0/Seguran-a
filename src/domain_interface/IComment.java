package domain_interface;

import java.util.Date;

public interface IComment {

    /**
     * Obtem o ID da foto referente
     * @return o ID guardado
     */
    String getUserID();

    /**
     * Obtem o comentario da foto referente
     * @return o comentario guardado
     */
    String getComment();

    /**
     * Obtem a Date da foto referente
     * @return a Date guardada
     */
    Date getDataPub();
}
