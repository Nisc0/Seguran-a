package handlers;

import domain.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class RecoveryManeger {

    private FileWriter fw;
    private FileReader fr;
    private File file_users;

    public RecoveryManeger() throws IOException {
        File fl = new File("Files");
        fw = new FileWriter(new File(fl, "users.txt"), true);
        fr = new FileReader(new File(fl, "users.txt"));
        this.file_users = new File(fl, "Users");
        file_users.mkdirs();
    }

    public FileWriter getFW() {
        return fw;
    }

    public void setFile(String path) throws IOException {
        this.fw = new FileWriter(path, true);
    }

    public void writeFile(String user, String pass) throws IOException {
        BufferedWriter writer = new BufferedWriter(fw);
        writer.append(user + ":" + pass + "\n");
        writer.flush();

    }

    public void backupUser(User u) throws IOException {

        File fl = new File(file_users, u.getID());
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        fl.mkdir();

        try {

            fout = new FileOutputStream(fl);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(u);

            System.out.println("Done");

            fout.close();
            oos.close();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }


    public void simpleRecovery() {

        BufferedReader reader = new BufferedReader(fr);

    }

    public Iterable<User> recUsers() {
        File[] fls = file_users.listFiles();
        ArrayList<User> users = new ArrayList<>();

        for (File f : fls) {
            try {

                FileInputStream fin = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fin);
                User u = (User) ois.readObject();
                users.add(u);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return users;

    }


    public void recPhotos(String userID, Iterable<Photo> uPh) throws IOException {
        File fl = new File(file_users, userID + "/");
        for (Photo p : uPh) {
            File flPh = new File(fl, p.getPhotoID() + "." + p.getExtension());
            p.setImage(ImageIO.read(flPh));
        }
    }

    public void saveImage(BufferedImage image, User u) {
        File fl = new File(file_users, u.getID());
        FileOutputStream fout;
        ObjectOutputStream oos;
        fl.mkdir();

        try {

            fout = new FileOutputStream(fl);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(u);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
