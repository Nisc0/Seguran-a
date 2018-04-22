package managers;

import java.io.*;
import java.util.Base64;

import static java.util.Base64.getDecoder;

public class AuthenticationManager {

    Base64.Decoder denc = getDecoder();
    private static File fl = new File("Files");
    private static File passFile = new File(fl, "users.txt");



    public boolean authenticate (String name, String pass) {

        try {



            BufferedReader br = new BufferedReader(new FileReader(passFile));

            FileInputStream fos = new FileInputStream(passFile);
            ObjectInputStream oos = new ObjectInputStream(fos);

            String line = br.readLine();
            while(line != null) {
                if(!name.equals(line.split(":")[0]))
                    bw.write(line + "\n");
                else
                    bw.write(name + ":" + salt + ":" + salted + "\n");
                line = br.readLine();
            }

            bw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }


}
