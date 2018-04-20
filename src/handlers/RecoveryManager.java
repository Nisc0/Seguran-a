package handlers;

import domain.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;

public class RecoveryManager implements handlers.Interface.IRecoveryManeger {

    private FileWriter fw;
    private FileReader fr;
    private File file_users;
    private EncryptManager em;

    public RecoveryManager() throws IOException {
        File fl = new File("Files");
        fl.mkdir();
        fw = new FileWriter(new File(fl, "users.txt"), true);
        fr = new FileReader(new File(fl, "users.txt"));
        this.file_users = new File(fl, "Users");
        file_users.mkdir();
        em = EncryptManager.getInstance();
    }

    @Override
    public void writeFile(String user, String pass) throws IOException {
        BufferedWriter writer = new BufferedWriter(fw);
        writer.append(user + ":" + pass + "\n");
        writer.flush();
    }

    @Override
    public void backupUser(User u) {
        File fl = new File(file_users, u.getID());
        fl.mkdir();
        File fu = new File(fl, u.getID() + ".u");
        byte[] byteUser = util.BytesUtil.toByteArray(fu);
        byte[] signedByteUser = em.signFile(byteUser);
        writeEncrypt(fl, fu, signedByteUser);
    }

    @Override
    public void saveImage(BufferedImage image, User u, String photoID, String extension) {
        File fl = new File(file_users, u.getID());
        File fi = new File(fl, photoID + "." + extension);
        byte[] byteImage = util.BytesUtil.toByteArray(fi);
        writeEncrypt(fl, fi, byteImage);
    }

    private void writeEncrypt(File toEncryptDir, File encryptFile, byte[] byteFile) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(encryptFile);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(em.encrypt(byteFile, toEncryptDir, encryptFile));
            fout.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void simpleRecovery() { //TODO:
        BufferedReader reader = new BufferedReader(fr);
    }

    @Override
    public Iterable<User> recUsers() {
        File[] fls = file_users.listFiles();
        ArrayList<User> users = new ArrayList<>();

        for (File f : fls) {
            File[] filesUser = f.listFiles((file, s) -> s.toLowerCase().endsWith(".u"));

            File userFile = filesUser[0];

            byte[] encryptedFile = Files.readAllBytes(userFile.toPath());
            byte[] decryptedFile = em.decrypt(encryptedFile, f, userFile);

            if (em.isVerifiedFile(decryptedFile, f, userFile)) {
                users.add((User) util.BytesUtil.toObject(decryptedFile));
            } else {
                //TODO: excepcao - mandar alerta
            }

        }
        return users;
    }


    @Override
    public void recPhotos(String userID, Iterable<Photo> uPh) throws IOException {
        File fl = new File(file_users, userID + "/");
        for (Photo p : uPh) {
            File flPh = new File(fl, p.getPhotoID() + "." + p.getExtension());
            byte[] encryptedFile = Files.readAllBytes(flPh.toPath());
            byte[] decryptedFile = em.decrypt(encryptedFile, fl, flPh);

            if (em.isVerifiedFile(decryptedFile, fl, flPh)) {
                p.setImage((BufferedImage) util.BytesUtil.toObject(decryptedFile)); //TODO exceptions
            } else {
                //TODO: excepcao - mandar alerta
            }
        }
    }

}
