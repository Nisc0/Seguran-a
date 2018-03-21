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

            System.out.println(cat.getUser("Diogo").getID());
            Iterable<PhotoData> ite = u.getAllPhotosData();
            for(PhotoData st : ite) {
                System.out.println(st.getPhotoID() + ":" + st.getDate());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }





    }
}
