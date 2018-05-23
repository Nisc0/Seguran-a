package server;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class PhotoShareServer {

    private static File fl = new File("Files");
    private static File passFile = new File(fl, "users.txt");
    private static File macFile = new File(fl, "sec.mac");

    public static void main(String[] args) {
        String port = args[0];
        System.setProperty("javax.net.ssl.keyStore", "./Autentication/ServerKeyStore.keyStore");
        System.setProperty("javax.net.ssl.keyStorePassword", "dibrunis");
        System.setProperty("javax.net.ssl.trustStore", "./Autentication/ServerTrustStore.keyStore");

        try {

            //criação da pasta
            fl.mkdir();

            //autenticação do admin
            Scanner scanner = new Scanner(System.in);
            System.out.println("Administrator, what's your password?");
            String password = scanner.next();

            //o nosso sal, cheio de amor
            byte[] salt = {0x6e, 0x69, 0x73, 0x63, 0x6f, 0x6e, 0x69, 0x69, 0x2d, 0x63, 0x68, 0x61, 0x6e, 0x20, 0x62,
                    0x72, 0x75, 0x6e, 0x6f, 0x6e, 0x69, 0x69, 0x2d, 0x63, 0x68, 0x61, 0x6e, 0x20, 0x64, 0x61, 0x69,
                    0x73, 0x75, 0x6b, 0x69};

            //codificação de pass do admin
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 20);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
            SecretKey key = kf.generateSecret(keySpec);

            //verificar se é a primeira execução
            fl.isDirectory();
            if (fl.list().length > 0) {

                FileInputStream fos = new FileInputStream(macFile);
                ObjectInputStream oos = new ObjectInputStream(fos);
                //verificar se ficheiro das pass's n foi alterado
                byte[] oldMac = (byte[]) oos.readObject();
                oos.close();
                if (!Arrays.equals(makeMac(key), oldMac)) {
                    System.out.println("Access denied: wrong password or corrupted file");
                    System.exit(-1);

                }
            } else {
                System.out.println("Can't connect without MAC!");
                System.exit(-1);
            }
        } catch (ClassNotFoundException | GeneralSecurityException | IOException e) {
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }

        System.out.println("Valid MAC!");
        ServerNetwork server = ServerNetwork.getInstance();
        System.out.println("Server initialized at port " + port + "!");
        server.startServer(port);
    }

    private static byte[] makeMac(SecretKey k) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(k);
        byte[] bt = Files.readAllBytes(passFile.toPath());
        return mac.doFinal(bt);
    }

}