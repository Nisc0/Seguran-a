package managers;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.Scanner;

public class manUsers {


    private static File fl = new File("Files");
    private static File pass = new File(fl, "users.txt");
    private static File mac = new File(fl, "sec.mac");


    public static void main (String[] args) {

    try {

        fl.mkdir();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Administrador, qual a password?");
        String password = scanner.next();

        final Random r = new SecureRandom();
        byte[] salt = new byte[64];
        r.nextBytes(salt);

        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
        SecretKey key = kf.generateSecret(keySpec);

        fl.mkdir();
        FileWriter fw = new FileWriter(new File(fl, "users.txt"), true);
        FileReader fr = new FileReader(new File(fl, "users.txt"));




    }
    catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (InvalidKeySpecException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }



    }


    private static byte[] obtainMac(SecretKey k) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(k);
        byte[] bt = Files.readAllBytes(pass.toPath());
        return mac.doFinal(bt);
    }
}
