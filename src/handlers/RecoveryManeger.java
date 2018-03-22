package handlers;

import java.io.*;

public class RecoveryManeger {

    private FileWriter fw;
    private FileReader fr;


    public RecoveryManeger() throws IOException {
        File fl = new File("Files");
        fl.mkdirs();
        fw = new FileWriter(new File(fl, "users.txt"), true);
        fr = new FileReader(new File(fl, "users.txt"));
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
        writer.flush();

    }

    public void simpleRecovery () {

        BufferedReader reader = new BufferedReader(fr);

    }


}
