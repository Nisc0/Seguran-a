package message;

import domain.Photo;
import domain.PhotoData;
import domain.PhotoOpinion;

public class MsgPhoto extends Message {

    private String photoID;
    private Photo photo;
    private Iterable<Photo> photoList;
    private PhotoOpinion opinion;

    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success) {
        super(c_type, c_err, user, followID, success);
    }

    //para o metodo getPhotoOpinion - client
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
    }

    //para o metodo addPhoto
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID, Photo photo) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.photo = photo;
    }

    //para o metodo copyPhotos
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, Iterable<Photo> photoList) {
        super(c_type, c_err, user, followID, success);
        this.photoList = photoList;
    }

    //para o metodo getPhotoOpinion - server
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID, PhotoOpinion opinion) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.opinion = opinion;
    }

    public String getPhotoID() {
        //todo check param
        return photoID;
    }

    public Photo getPhoto() {
        //todo check param
        return photo;
    }

    public Iterable<Photo> getPhotoList() {
        //todo check param
        return photoList;
    }

    public PhotoOpinion getOpinion() {
        //todo check param
        return opinion;
    }
}
