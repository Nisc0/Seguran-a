package managers;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.Scanner;

public class manUsers {

public static void main (String[] args) {

    try {

        final Random r = new SecureRandom();
        byte[] salt = new byte[64];
        r.nextBytes(salt);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Administrador, qual a password?");
        String password = scanner.next();

        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
        SecretKey key = kf.generateSecret(keySpec);

        FileWriter fw;
        FileReader fr;
        File file_users;


        File fl = new File("Files");
        fl.mkdir();
        fw = new FileWriter(new File(fl, "users.txt"), true);
        fr = new FileReader(new File(fl, "users.txt"));
        this.file_users = new File(fl, "Users");
        file_users.mkdir();

        passe linha comando, gerar key, contedo em bytes, meter no mac

                2 files, mac e users

    }
    catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (InvalidKeySpecException e) {
        e.printStackTrace();
    }


}
}
