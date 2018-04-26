package managers;

import domain.Photo;
import domain.User;
import managers.Interface.IRecoveryManeger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.util.ArrayList;

public class RecoveryManager implements IRecoveryManeger {

    private FileWriter fw;
    private FileReader fr;
    private File file_users;
    private EncryptManager em;

    public RecoveryManager() throws IOException, GeneralSecurityException {
        File fl = new File("Files");
        this.file_users = new File(fl, "Users");

        fw = new FileWriter(new File(fl, "users.txt"), true);
        fr = new FileReader(new File(fl, "users.txt"));
        this.file_users = new File(fl, "Users");
        file_users.mkdir();
        try {
            em = EncryptManager.getInstance();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e){
            throw new GeneralSecurityException(e.getMessage());
        }
    }

    @Override
    public void writeFile(String user, String pass) throws IOException {
        BufferedWriter writer = new BufferedWriter(fw);
        writer.append(user + ":" + pass + "\n");
        writer.flush();
    }

    @Override
    public void backupUser(User u) throws IOException, GeneralSecurityException {
        File fl = new File(file_users, u.getID());
        fl.mkdir();
        File fu = new File(fl, u.getID() + ".u");
        byte[] byteUser = util.BytesUtil.toByteArray(u);
        try {
            em.signFile(byteUser, fl, fu);
        }catch (InvalidKeyException | SignatureException e){
            throw new GeneralSecurityException(e.getMessage());
        }
        writeEncrypt(fl, fu, byteUser);
    }

    @Override
    public void saveImage(BufferedImage image, User u, String photoID, String extension) throws IOException,
            GeneralSecurityException{
        File fl = new File(file_users, u.getID());
        File fi = new File(fl, photoID + "." + extension + ".sc");
        byte[] byteImage = util.BytesUtil.serializeImage(image, extension);
        writeEncrypt(fl, fi, byteImage);
    }

    private void writeEncrypt(File toEncryptDir, File encryptFile, byte[] byteFile) throws IOException,
            GeneralSecurityException {
        try {
            Files.write(encryptFile.toPath(), em.encrypt(byteFile, toEncryptDir, encryptFile));
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | KeyException | BadPaddingException e){
            throw new GeneralSecurityException(e.getMessage());
        }
    }

    @Override
    public void simpleRecovery() { //TODO:
        BufferedReader reader = new BufferedReader(fr);
    }

    @Override
    public Iterable<User> recUsers() throws IOException, ClassNotFoundException, GeneralSecurityException {
        File[] fls = file_users.listFiles();
        ArrayList<User> users = new ArrayList<>();

        for (File f : fls) {
            File[] filesUser = f.listFiles((file, s) -> s.toLowerCase().endsWith(".u"));

            File userFile = filesUser[0];
                        try {
                byte[] encryptedFile = Files.readAllBytes(userFile.toPath());
                byte[] decryptedFile = em.decrypt(encryptedFile, f, userFile);

                if (em.isVerifiedFile(decryptedFile, f, userFile)) {
                    users.add((User) util.BytesUtil.toObject(decryptedFile));
                }
            } catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException |
                    BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
                throw new GeneralSecurityException(e.getMessage());
            }
        }
        return users;
    }

    @Override
    public void recPhotos(String userID, Iterable<Photo> uPh) throws IOException, ClassNotFoundException,
            GeneralSecurityException {
        File fl = new File(file_users, userID + "/");
        for (Photo p : uPh) {
            File flPh = new File(fl, p.getPhotoID() + "." + p.getExtension() + ".sc");
            try {
                byte[] encryptedFile = Files.readAllBytes(flPh.toPath());
                byte[] decryptedFile = em.decrypt(encryptedFile, fl, flPh);

                p.setImage(util.BytesUtil.deserializeImage(decryptedFile));

            } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException |
                    IllegalBlockSizeException e) {
                throw new GeneralSecurityException(e.getMessage());
            }
        }
    }


}
