package message;

import domain.Photo;
import domain.PhotoOpinion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class MsgPhoto extends Message {

    private String photoID;
    private Photo photo;
    private Iterable<Photo> photoList;
    private PhotoOpinion opinion;
    private byte[] photoBytes;
    private byte[][] photoListBytes;

    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success) {
        super(c_type, c_err, user, followID, success);
    }

    //para o metodo getPhotoOpinion - client
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
    }

    //para o metodo addPhoto
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID,
                    Photo photo, BufferedImage img) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.photo = photo;

        byte[] photoBytes = null;
        try {
            photoBytes = serializeImage(img, photo);
        } catch (IOException e) {
            System.out.println("Serialization of photo failed!");
        }

        this.photoBytes = photoBytes;
    }

    //para o metodo copyPhotos
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, Iterable<Photo> photoList) {
        super(c_type, c_err, user, followID, success);
        List<Photo> list = (List<Photo>) photoList;
        photoListBytes = new byte[list.size()][];

        int i = 0;
        for (Photo ph : photoList) {
            try {
                photoListBytes[i] = serializeImage(ph.getImage(), ph);
            } catch (IOException e) {
                System.out.println("Serialization of photo failed!");
            }
            i++;
        }
        this.photoList = photoList;
    }

    //para o metodo getPhotoOpinion - server
    public MsgPhoto(MsgType c_type, MsgError c_err, String user, String followID, boolean success, String photoID, PhotoOpinion opinion) {
        super(c_type, c_err, user, followID, success);
        this.photoID = photoID;
        this.opinion = opinion;
    }

    public String getPhotoID() {
        return photoID;
    }

    public Photo getPhoto() {
        return photo;
    }

    public PhotoOpinion getOpinion() {
        return opinion;
    }

    public Iterable<Photo> getPhotoList() {
        Iterable<Photo> photoL = photoList;
        byte[][] imagesSerialized = photoListBytes;
        int i = 0;
        for (Photo ph : photoL) {
            try {
                ph.setImage(deserializeImage(imagesSerialized[i]));
            } catch (IOException e) {
                System.out.println("Deserialization of photo failed!");
            }
            i++;
        }
        return photoList;
    }

    public BufferedImage getBufferedImage() throws IOException {
        return deserializeImage(photoBytes);
    }

    private byte[] serializeImage(BufferedImage img, Photo photo) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, photo.getExtension(), baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    private BufferedImage deserializeImage(byte[] img) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(img);
        BufferedImage image = ImageIO.read(bais);
        return image;
    }
}
