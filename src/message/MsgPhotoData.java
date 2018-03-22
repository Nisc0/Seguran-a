package message;

import domain.PhotoData;

public class MsgPhotoData extends Message {

    private Iterable<PhotoData> photoDataList;

    public MsgPhotoData(MsgType c_type, MsgError c_err, String user, String followID, boolean success) {
        super(c_type, c_err, user, followID, success);
    }

    public MsgPhotoData(MsgType c_type, MsgError c_err, String user, String followID, boolean success, Iterable<PhotoData> photoDataList) {
        super(c_type, c_err, user, followID, success);
        this.photoDataList = photoDataList;
    }

    public Iterable<PhotoData> getPhotoDataList() {
        return photoDataList;
    }

}
