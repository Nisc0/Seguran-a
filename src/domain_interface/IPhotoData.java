package domain_interface;

import java.util.Date;

public interface IPhotoData {

    /**
     * Obtem o ID da foto referente
     * @return o ID guardado
     */
    String getPhotoID();

    /**
     * Obtem a Date da foto referente
     * @return a Date guardada
     */
    Date getDate();
}
