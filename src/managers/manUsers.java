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

        //criação da pasta
        fl.mkdir();


        //autenticação do admin
        Scanner scanner = new Scanner(System.in);
        System.out.println("Administrador, qual a password?");
        String password = scanner.next();


        //vós sois o sal da terra
        final Random r = new SecureRandom();
        byte[] salt = new byte[64];
        r.nextBytes(salt);


        //codificação de pass do admin
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
        SecretKey key = kf.generateSecret(keySpec);


        //verificar se é a primeira execução
        fl.isDirectory();
        if(fl.list().length > 0) {

            //verificar se ficheiro das pass's n foi alterado
            if(!Arrays.equals(obtainMac(key), Files.readAllBytes(macFile.toPath()))) {
                System.out.println("Acesso Negado: Password Errado ou Ficheiro Alterado");
                System.exit(-1);
            }
        }
        else {
            //criação dos ficheiros
            passFile.createNewFile();
            macFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(macFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.write(obtainMac(key));
        }

        System.out.println("Comandos: add, delete, modify");
        System.out.println("Administrador, qual o comando?");
        String comando = scanner.next();


        switch (comando) {

            case "add":

                break;

            case "delete":

                break;

            case "modify":

                break;

        }


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
como defenir qual a pass do admin -- n se define
como verificar se o ficheiro das passes esta ok -- done


*/
