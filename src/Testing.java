import catalogs.CatalogoUser;
import domain.Photo;
import domain.PhotoData;
import domain.User;
import exceptions.WrongUserPasswordException;
import handlers.*;

import java.io.IOException;

public class Testing {

    public static void main (String[] args) {
        System.out.println("yoho");

        try {
            CatalogoUser cat = CatalogoUser.getCatalogo();
            User u = new User("Diogo", "ola");
            cat.addUser(u);
            cat.addUser(new User("Bruno", "piroquia"));

            SessionHandler sh = new SessionHandler();
            sh.startSession("Diogo", "ola");
            System.out.println("sessao iniciada");

            OpinionHandler oh = new OpinionHandler(u);
            FollowerHandler fh = new FollowerHandler(u);
            PhotoHandler ph = new PhotoHandler(u);
            RecoveryManeger rm = new RecoveryManeger();





            Iterable<PhotoData> ite = u.getAllPhotosData();

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (WrongUserPasswordException e) {
            e.printStackTrace();
        }


    }
}
