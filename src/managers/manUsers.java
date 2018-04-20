package managers;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

import static java.util.Base64.*;

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
        System.out.println("Administrator, what's the passord?");
        String password = scanner.next();

        //o nosso sal, cheio de amor
        byte[] salt = {0x6e, 0x69, 0x73, 0x63, 0x6f, 0x6e, 0x69, 0x69, 0x2d, 0x63, 0x68, 0x61, 0x6e, 0x20, 0x62, 0x72, 0x75, 0x6e, 0x6f, 0x6e, 0x69, 0x69, 0x2d, 0x63, 0x68, 0x61, 0x6e, 0x20, 0x64, 0x61, 0x69, 0x73, 0x75, 0x6b, 0x69};

        //codificação de pass do admin
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 20);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
        SecretKey key = kf.generateSecret(keySpec);


        //verificar se é a primeira execução
        fl.isDirectory();
        if(fl.list().length > 0) {

            FileInputStream fos = new FileInputStream(macFile);
            ObjectInputStream oos = new ObjectInputStream(fos);
            //verificar se ficheiro das pass's n foi alterado
            if(!Arrays.equals(makeMac(key), (byte[]) oos.readObject())) {
                System.out.println("Access Denied: Wrong Password or Corrumpted File");
                System.exit(-1);
            }
        }
        else {
            //criação dos ficheiros
            passFile.createNewFile();
            macFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(macFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.write(makeMac(key));
            fos.close();
        }

        //pedido do comando
        System.out.println("Administrator, what's the command?");
        System.out.println("Available Commands: add, delete, modify & quit");
        String comand = scanner.next();


        //inicio do processamento
        BufferedWriter bw = new BufferedWriter(new FileWriter(passFile, true));
        BufferedReader br = new BufferedReader(new FileReader(passFile));
        Base64.Encoder enc = getEncoder();
        String name;
        String pass;
        String userInfo;
        byte[] salted;
        FileOutputStream fos = new FileOutputStream(macFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);


        //processamento do comand
        switch (comand) {

            case "add":

                System.out.println("Adding new User:");

                System.out.println("What's the username?");
                name = scanner.next();

                System.out.println("What's the password?");
                pass = scanner.next();

                salt = makeSalt();

                salted = getSalty(pass, salt);

                bw.write(name + ":" + enc.encodeToString(salt) + ":" + enc.encodeToString(salted));
                bw.close();

                oos.write(makeMac(key));
                fos.close();


                break;

            case "delete":

                System.out.println("Deleting User:");

                //verificação de name
                System.out.println("What's the username?");
                name = scanner.next();
                userInfo = searchUser(name, br);

                if(userInfo == null)
                    System.out.println("Error: User not found");

                //verificação de pass
                salt = userInfo.split(":")[1].getBytes();
                System.out.println("What's the old password?");
                pass = scanner.next();

                salted = getSalty(pass, salt);
                if(!salted.equals(userInfo.split(":")[2]))
                    System.out.println("Error: Wrong Password");

                deleteUser(name, br);

                break;

            case "modify":


                System.out.println("Modifying User:");

                //verificação de name
                System.out.println("What's the username?");
                name = scanner.next();
                userInfo = searchUser(name, br);

                if(userInfo == null)
                    System.out.println("Error: User not found");

                //verificação de pass
                salt = userInfo.split(":")[1].getBytes();
                System.out.println("What's the old password?");
                pass = scanner.next();

                salted = getSalty(pass, salt);
                if(!salted.equals(userInfo.split(":")[2]))
                    System.out.println("Error: Wrong Password");

                //defenição de nova pass
                System.out.println("What's the new password?");
                pass = scanner.next();

                salted = getSalty(pass, salt);
                bw.write(name + ":" + enc.encodeToString(salt) + ":" + enc.encodeToString(salted));
                bw.close();

                oos.write(makeMac(key));
                fos.close();

                break;

            case "quit":

                System.out.println("Program terminated!");
                System.exit(0);


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
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }


    }


    private static byte[] makeMac(SecretKey k) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(k);
        byte[] bt = Files.readAllBytes(passFile.toPath());
        return mac.doFinal(bt);
    }

    //vós sois o sal da terra
    private  static  byte[] makeSalt() {

        final Random r = new SecureRandom();
        byte[] salt = new byte[64];
        r.nextBytes(salt);
        return salt;

    }


    private static byte[] getSalty(String pass, byte[] salt) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(pass.getBytes());
        bos.write(salt);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(bos.toByteArray());

    }

    private static String searchUser(String name, BufferedReader br) throws IOException {

        String line = br.readLine();
        while(!name.equals(line.split(":")[0])) {
            line = br.readLine();
        }

        return line;
    }
    private static boolean deleteUser(String name, BufferedReader br) throws IOException {

        File help = new File(fl, "help.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(help));

        FileOutputStream fos = new FileOutputStream(macFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        String line = br.readLine();
        while(line != null) {
            if(name != line.split(":")[0])
                bw.write(line);
            line = br.readLine();
        }

        return help.renameTo(passFile);

    }



}

/*

duvidas:
como defenir qual a pass do admin -- n se define
como verificar se o ficheiro das passes esta ok -- done


*/

