package handlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RecoveryManeger {

    private FileWriter fw;


    public RecoveryManeger() throws IOException {
        fw = new FileWriter("Files/users", true);
    }

    public FileWriter getFW() {
        return fw;
    }

    public void setFile( String path) throws IOException {
        this.fw = new FileWriter(path, true);
    }

    public void writeFile (String user, String pass) throws IOException {
        BufferedWriter writer = new BufferedWriter(fw);
        writer.append(user + ":" + pass + "\n");
        writer.close();

    }


}
