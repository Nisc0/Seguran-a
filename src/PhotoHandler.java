public class PhotoHandler {

    public PhotoHandler() {
        //TODO: user como atributo ou nao?
        //protected User curr;
    }

    //TODO:
    public boolean addPhoto(Photo photo) {
        return curr.addPhoto(photo);
    }

    public Iterable<PhotoData> getPhotosData(String userID) {

        //TODO:porque raio eh que mandas o userID se nao o usas?
        //porque raio eh que mandas o userID se nao o usas?
        //porque raio eh que mandas o userID se nao o usas?
        User uID = curr.getFollow(userID);
        if (uID != null) {
            return uID.getAllPhotoData();
        } else
            return null; // ver se devemos mandar exception

        //TODO:Nao deves.. tens -> esta escrito no enunciado!!!
        //caso contrário, devolve um erro;
        //caso contrário, devolve um erro;

    }

    public Iterable<PhotoOpinion> getPhotoOpinion(String userID, String photoID) {

        //TODO:porque raio eh que mandas o userID e o photoID se nao o usas?
        //porque raio eh que mandas o userID e o photoID se nao o usas?
        //porque raio eh que mandas o userID e o photoID se nao o usas?
        User uID = curr.getFollow(userID);
        if (uID != null) {
            return uID.getAllPhotoOpinion();
        } else
            return null;
    }

    /**
     * FALTA O -G
     */

    public boolean makeComment(String comment, String userID, String photoID) {
        User uID = curr.getFollow(userID);
        return (uID != null) && uID.makeComment(comment, userID, photoID);
    }

    //TODO:Juntar estes dois como fizemos no addOpinion no Photo?
    //Juntar estes dois como fizemos no addOpinion no Photo?
    //Juntar estes dois como fizemos no addOpinion no Photo?
    public boolean addLike(String userID, String photoID){
        User uID = curr.getFollow(userID);
        if(uID.getPhoto(photoID) != null){
            //TODO:arranjar melhor maneira de fazer isto do obterUserID???
            return curr.addOpinion(uID.obterUserID(), true);
        }
        return false;
    }
    //TODO:Juntar estes dois como fizemos no addOpinion no Photo?
    //Juntar estes dois como fizemos no addOpinion no Photo?
    //Juntar estes dois como fizemos no addOpinion no Photo?
    public boolean addDislike(String userID, String photoID){
        User uID = curr.getFollow(userID);
        if(uID.getPhoto(photoID) != null){
            //TODO:arranjar melhor maneira de fazer isto do obterUserID???
            return curr.addOpinion(uID.obterUserID(), false);
        }
        return false;
    }

    /** ISTO NAO EH PRA AQUI, POIS NAO?!
     *
     *
    //TODO:e se se verificar o containfollows aqui em vez de no User?
    //e se se verificar o containfollows aqui em vez de no User?
    //e se se verificar o containfollows aqui em vez de no User?
    public boolean addFollower(String userID){
        User uID = curr.getFollow(userID);
        return curr.addFollow(userID, uID);
    }

    //TODO:e se se verificar o containfollows aqui em vez de no User?
    //e se se verificar o containfollows aqui em vez de no User?
    //e se se verificar o containfollows aqui em vez de no User?
    public boolean removeFollower(String userID){
        User uID = curr.getFollow(userID);
        return curr.removeFollow(userID, uID);
    }

     **/


}
