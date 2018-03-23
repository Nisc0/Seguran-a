package message;

import domain.Photo;
import domain.PhotoOpinion;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MsgPhoto extends Message {

    private String photoID;
    private Photo photo;
    private Iterable<Photo> photoList;
    private PhotoOpinion opinion;
    private byte[] photoBytes;
    private byte[][] photoListBytes;
    private BufferedImage img;

    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success) {
        super(c_type, c_err, user, followID, success);
    }

    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID, Photo photo) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.photo = photo;
    }

    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, Iterable<Photo> photoList) {
        super(c_type, c_err, user, followID, success);
        ArrayList<Photo> list =  (ArrayList) photoList;
        photoListBytes = new byte[list.size()][];
        int j = 0;

        for(Photo ph: photoList){
            photoListBytes[j] = serializePhoto(ph.getImage());
            j++;
        }
        this.photoList = photoList;
    }


    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, PhotoOpinion opinion) {
        super(c_type, c_err, user, followID, success);
        this.opinion = opinion;
    }

    public String getPhotoID() {
        return (photoID != null)?photoID:null;
    }

    public Photo getPhoto() {
        return (photo != null)?photo:null;
    }

    public Iterable<Photo> getPhotoList() {
        return (photoList != null)?photoList:null;
    }

    public PhotoOpinion getOpinion() {
        return (opinion != null)? opinion:null;
    }

    public BufferedImage getBufferedImage(){
        return deserializePhoto(photoBytes);
    }


    private  byte[] serializePhoto(BufferedImage image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }


    private  BufferedImage deserializePhoto(byte[] imageBytes){
        InputStream baos = new ByteArrayInputStream(imageBytes);
        BufferedImage image = null;
        try {
            image = ImageIO.read(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
