import catalogs.CatalogoUser;
import domain.Photo;
import domain.PhotoData;
import domain.User;

import java.io.IOException;

public class Testing {

    public static void main (String[] args) {
        System.out.println("yoho");

        try {
            CatalogoUser cat = CatalogoUser.getCatalogo();
            User u = new User("Diogo", "ola");
            cat.addUser(u);
            cat.addUser(new User("Bruno", "piroquia"));

            u.addPhoto(new Photo("a"));
            u.addPhoto(new Photo("b"));
            u.addPhoto(new Photo("c"));

            u.makeComment("noob", "Bruno", "a");
            u.getPhoto("a").addOpinion("Bruno", true);
            System.out.println(u.getPhotoOpinion("a"));
            u.makeComment("wow", u.getID(), "a");
            u.getPhoto("a").addOpinion("Bruno", false);
            System.out.println(u.getPhotoOpinion("a"));




            Iterable<PhotoData> ite = u.getAllPhotosData();

        }
        catch (IOException e) {
            e.printStackTrace();
        }





    }
}
