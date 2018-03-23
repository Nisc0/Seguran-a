import catalogs.CatalogoUser;
import domain.Photo;
import domain.PhotoData;
import domain.User;
import exceptions.NoSuchPhotoException;
import exceptions.NoSuchUserException;
import exceptions.NotFollowingException;
import exceptions.WrongUserPasswordException;
import handlers.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class tester {



    public static void main(String[] args) {

        System.out.println("yoho");

        CatalogoUser cat = CatalogoUser.getCatalogo();

        System.out.println("Catalogo aberto");

        SessionHandler sessH = new SessionHandler();

        System.out.println("SessionHandler aberto");

        boolean bol = false;

        try {

            bol = sessH.startSession("diogo", "ola");
            System.out.println(bol);
            bol =sessH.startSession("bruno", "piroquia");
            System.out.println(bol);
            bol=sessH.startSession("nisco", "moto");
            System.out.println(bol);
            bol=sessH.startSession("noob", "boon");
            System.out.println(bol);
        }
        catch (WrongUserPasswordException e) {
            e.printStackTrace();
        }

        System.out.println(cat.getUser("diogo"));
        System.out.println(cat.getUser("bruno"));
        System.out.println(cat.getUser("nisco"));
        System.out.println(cat.getUser("noob"));

        File photosInput = new File("photos");
        photosInput.mkdir();

        try {
            BufferedImage dp = ImageIO.read(new File(photosInput, "hooo.jpeg"));
            BufferedImage ep = ImageIO.read(new File(photosInput, "index.jpeg"));
            BufferedImage fp = ImageIO.read(new File(photosInput, "lalala.jpeg"));

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Photo p1 = new Photo("hooo.jpeg");
        Photo p2 = new Photo("index.jpeg");
        Photo p3 = new Photo("lalala.jpeg");

        PhotoHandler photoH = new PhotoHandler();

        System.out.println("PhotoHandler aberto");

        System.out.println(photoH.getCurrUser());

        //System.out.println("adding Photo... " + pHandler.addPhoto(cruz, p1, dp));

//        PhotoHandler pHandler = new PhotoHandler(u);
  //      OpinionHandler oHandler = new OpinionHandler(u);
    //    FollowerHandler fHandler = new FollowerHandler(u);

        /**
        //Photos
        String dickPic = "dickPic";
        String capivara = "capivara";
        String diogo = "diogo";
        String acc = "AssassinsCreedCapivara";

        String dickPicf = dickPic + ".png";
        String capivaraf = capivara + ".jpg";
        String diogof = diogo + ".jpg";
        String accf = acc + ".jpg";
        File photosInput = new File("photos");

        BufferedImage dp = ImageIO.read(new File(photosInput, dickPicf));
        BufferedImage c = ImageIO.read(new File(photosInput, capivaraf));
        BufferedImage ac = ImageIO.read(new File(photosInput, accf));
        BufferedImage d = ImageIO.read(new File(photosInput, diogof));
        Photo p1 = new Photo(dickPicf);
        Photo p2 = new Photo(capivaraf);
        Photo p3 = new Photo(accf);
        Photo p4 = new Photo(diogof);
        System.out.println("adding Photo... " + pHandler.addPhoto(cruz, p1, dp));
        System.out.println("adding Photo... " + pHandler.addPhoto(cruz, p2, c));
        System.out.println("adding Photo... " + pHandler.addPhoto(cruz, p3, ac));
        System.out.println("adding Photo... " + pHandler.addPhoto(cruz, p4, d));
        System.out.println(pHandler.listPhotos(cruz, cruz));
        //Comment
        try {
            oHandler.makeComment("bananas", u.getID(), "photo");
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (NotFollowingException e) {
            e.printStackTrace();
        } catch (NoSuchPhotoException e) {
            e.printStackTrace();
        }
        oHandler.makeComment(cruz, "És linda!", cruz, capivara);
        oHandler.makeComment(cruz, "que inveja", cruz, diogo);
        oHandler.makeComment(cruz, "Vai trabalhar oh!", cruz, acc);
        //Opinion
        System.out.println(pHandler.getPhotoInfo(cruz, cruz, dickPic));
        System.out.println(pHandler.getPhotoInfo(cruz, cruz, capivara));
        System.out.println(pHandler.getPhotoInfo(cruz, cruz, diogo));
        System.out.println(pHandler.getPhotoInfo(cruz, cruz, acc));

        List<Photo> allPhotos = pHandler.getAllPhotos(cruz, cruz);
        File outPhotos = new File("photosOut");
        outPhotos.mkdir();

        for (Photo p : allPhotos) {
            File imageFile = new File(outPhotos, p.getImageName());
            ImageIO.write(p.getImage(), p.getExtension(), imageFile);
        }

         **/


    }
}
