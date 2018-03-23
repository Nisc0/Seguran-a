import catalogs.CatalogUser;
import domain.Photo;
import exceptions.*;
import handlers.FollowerHandler;
import handlers.OpinionHandler;
import handlers.PhotoHandler;
import handlers.SessionHandler;

import java.io.File;

public class test_recov {
    public static void main(String[] args) {

        System.out.println("yoho");

        CatalogUser cat = CatalogUser.getCatalogo();

        System.out.println("Catalogo aberto");

        SessionHandler sessH = new SessionHandler();

        System.out.println("SessionHandler aberto");

        boolean bol = false;

        try {

            bol=sessH.startSession("noob", "boon");
            System.out.println("Start Session: " + sessH.getCurrUser() + "state: " + bol);
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
            OpinionHandler opH = new OpinionHandler();

            System.out.println("OpinionHandler aberto");

            FollowerHandler folH = new FollowerHandler();

            System.out.println("FollowerHandler aberto");

            opH.makeComment("bleh", "noob", "hooo");

            Photo p1 = new Photo("hooo.jpeg");
            Photo p2 = new Photo("index.jpeg");
            Photo p3 = new Photo("lalala.jpeg");

            PhotoHandler photoH = new PhotoHandler();

            System.out.println("PhotoHandler aberto");

            bol =sessH.startSession("diogo", "ola");

            System.out.println("Start Session: " + sessH.getCurrUser() + "state: " + bol);



            opH.makeComment("mais bananas", "noob", "hooo");

            System.out.println(photoH.getPhotoOpinion("noob", p1.getPhotoID()));

            bol =sessH.startSession("nisco", "moto");

            folH.addFollow("noob"); // dar erro 1º

            System.out.println("Start Session: " + sessH.getCurrUser() + "state: " + bol);

            opH.addDisLike("noob", p1.getPhotoID());

            System.out.println(photoH.getPhotoOpinion("noob", p1.getPhotoID()));

            opH.makeComment("ohohoh", "noob", "hooo");

            System.out.println(photoH.getPhotoOpinion("noob", p1.getPhotoID()));


        }
        catch (NotFollowingException e) {
            e.printStackTrace();
        }  catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (NoSuchPhotoException e) {
            e.printStackTrace();
        } catch (WrongUserPasswordException e) {
            e.printStackTrace();
        } catch (AlreadyFollowingException e) {
            e.printStackTrace();
        }catch (AlreadyDislikedException e) { e.printStackTrace();
        }



    }
}

