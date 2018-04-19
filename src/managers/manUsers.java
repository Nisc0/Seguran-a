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
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class manUsers {


    private static File fl = new File("Files");
    private static File passFile = new File(fl, "users.txt");
    private static File macFile = new File(fl, "sec.mac");


    public static void main (String[] args) {

    try {


        final Random r = new SecureRandom();
        byte[] salt = new byte[64];
        r.nextBytes(salt);

        //Cração da pasta
        fl.mkdir();


        //autenticação do admin
        Scanner scanner = new Scanner(System.in);
        System.out.println("Administrador, qual a password?");
        String password = scanner.next();
        //TODO: ver se a pass está bem

        //codificação de pass do admin
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
        SecretKey key = kf.generateSecret(keySpec);


        //se não é a primeira execução
        fl.isDirectory();
        if(fl.list().length > 0) {

            FileInputStream fPass = new FileInputStream(passFile);
            FileInputStream fMac = new FileInputStream(macFile);

            //verificar se ficheiro das pass's n foi alterado
            if(!Arrays.equals(obtainMac(key), Files.readAllBytes(macFile.toPath())))
                //TODO: para execução
            {

            }




        }

/*

        //codificação de pass, n sei se é para já
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
        SecretKey key = kf.generateSecret(keySpec);

*/

    }
    catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (InvalidKeySpecException e) {
        e.printStackTrace();
    } catch (InvalidKeyException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }



    }


    private static byte[] obtainMac(SecretKey k) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(k);
        byte[] bt = Files.readAllBytes(passFile.toPath());
        return mac.doFinal(bt);
    }
}

/*

duvidas:
como defenir qual a pass do admin
como verificar se o ficheiro das passes esta ok


*/
