package managers;

import exceptions.WrongUserPasswordException;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Base64.getDecoder;

public class AuthenticationManager {


    private static AuthenticationManager instance;
    private static Base64.Decoder denc;
    private static File fl;
    private static File passFile;

    private AuthenticationManager() {

        denc= getDecoder();
        fl= new File("Files");
        passFile= new File(fl, "users.txt");

    }

    public static AuthenticationManager getAuthenticater(){

        if (AuthenticationManager.instance == null){
            AuthenticationManager.instance = new AuthenticationManager();
        }
        return AuthenticationManager.instance;
    }

    public boolean authenticate (String name, String pass) throws WrongUserPasswordException {

        byte[] salt;
        byte[] salted;

        try{

            BufferedReader br = new BufferedReader(new FileReader(passFile));

            String line = br.readLine();
            while(line != null || !line.contains(name)) {
                line = br.readLine();
            }

            if(line == null) {
                System.out.println("User not found!");
                return false;
            }

            salt = denc.decode(line.split(":")[1]);
            salted = getSalty(pass, salt);

            if(!Arrays.equals(salted, denc.decode(line.split(":")[2]))) {
                throw new WrongUserPasswordException();
            }

            br.close();

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }

    }

    private static byte[] getSalty(String pass, byte[] salt) throws NoSuchAlgorithmException, IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(pass.getBytes());
        bos.write(salt);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(bos.toByteArray());

    }


}
