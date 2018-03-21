package message;

import domain.Photo;
import domain.PhotoData;

public class MsgPhoto extends Message {

    private String photoID;
    private Photo photo;
    private Iterable<Photo> photoList;
    private Iterable<PhotoData> photoDataList;

    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, String photoID, Photo photo) {
        super(c_type, c_err, user, followID);
        this.photoID = photoID;
        this.photo = photo;

    }

    public String getPhotoID() {
        return photoID;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void addPhotoList(Iterable<Photo> list){
        this.photoList = list;
    }

    public void addPhotoDataList(Iterable<PhotoData> list){
        this.photoDataList = list;
    }

}
