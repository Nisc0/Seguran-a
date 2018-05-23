package managers;

import managers.Interface.IEncryptManager;

import javax.crypto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.Certificate;

public class EncryptManager implements IEncryptManager {

    private static EncryptManager ourInstance;
    private static final String KEYSTORE_PWD = "dibrunis";
    private static final String KEYSTORE_FILEPATH = "./Autentication/ServerKeyStore.keyStore";
    private static final String KEYSTORE_ALIAS = "ServerKeyStore";
    private Cipher rsaCipher, aesCipher;
    private Signature sig;
    private KeyPair kp;

    public static EncryptManager getInstance() throws GeneralSecurityException, IOException {
        if (ourInstance == null)
            ourInstance = new EncryptManager();
        return ourInstance;
    }

    private EncryptManager() throws IOException, GeneralSecurityException {
        rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        sig = Signature.getInstance("SHA256withRSA");

        FileInputStream keyStoreFile = null;

        //Encontrar Ku
        try {
            keyStoreFile = new FileInputStream(KEYSTORE_FILEPATH);
        } catch (FileNotFoundException e) {
            System.out.println("No .keystore file found!");
            throw e;
        }
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(keyStoreFile, KEYSTORE_PWD.toCharArray());

        Key key = keyStore.getKey(KEYSTORE_ALIAS, KEYSTORE_PWD.toCharArray());

        PublicKey publicKey;

        if (key instanceof PrivateKey) {
            Certificate cert = keyStore.getCertificate(KEYSTORE_ALIAS);
            publicKey = cert.getPublicKey();
            kp = new KeyPair(publicKey, (PrivateKey) key);
        } else {
            keyStoreFile.close();
            throw new GeneralSecurityException();
        }
        keyStoreFile.close();
    }

    private Key getKey(File userDir, File userFile) throws IOException, NoSuchAlgorithmException,
            IllegalBlockSizeException, InvalidKeyException {
        File keyFile = new File(userDir, userFile.getName() + ".key");
        Key K;
        if (!keyFile.exists()) {
            K = createKey();

            //cifrar com Ku
            rsaCipher.init(Cipher.WRAP_MODE, kp.getPublic());
            byte[] wrappedBytes = rsaCipher.wrap(K);
            Files.write(keyFile.toPath(), wrappedBytes);

        } else {
            K = getSecretKey(keyFile);
        }

        return K;
    }

    @Override
    public byte[] encrypt(byte[] plainData, File userDir, File userFile) throws NoSuchAlgorithmException, IOException,
            IllegalBlockSizeException, KeyException, BadPaddingException {
        Key k = getKey(userDir, userFile);
        aesCipher.init(Cipher.ENCRYPT_MODE, k);
        return aesCipher.doFinal(plainData);
    }

    @Override
    public byte[] decrypt(byte[] cipherData, File userDir, File userFile) throws InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException {
        File keyFile = new File(userDir, userFile.getName() + ".key");
        Key k = getSecretKey(keyFile);
        aesCipher.init(Cipher.DECRYPT_MODE, k);
        return aesCipher.doFinal(cipherData);
    }

    private SecretKey createKey() {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kg.init(128);
        return kg.generateKey();
    }

    private Key getSecretKey(File keyFile) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        byte[] encryptedKey = Files.readAllBytes(keyFile.toPath());
        rsaCipher.init(Cipher.UNWRAP_MODE, kp.getPrivate());
        return rsaCipher.unwrap(encryptedKey, "AES", Cipher.SECRET_KEY);
    }

    @Override
    public void signFile(byte[] toSign, File userDir, File userFile) throws InvalidKeyException, SignatureException,
            IOException {
        File sigFile = new File(userDir, userFile.getName() + ".sig");
        sig.initSign(kp.getPrivate());
        sig.update(toSign);
        byte[] signature = sig.sign();
        Files.write(sigFile.toPath(), signature);
    }

    @Override
    public boolean isVerifiedFile(byte[] toVerifyData, File userDir, File userFile) throws InvalidKeyException,
            IOException, SignatureException {
        File sigFile = new File(userDir, userFile.getName() + ".sig");
        sig.initVerify(kp.getPublic());
        byte[] signature = Files.readAllBytes(sigFile.toPath());
        sig.update(toVerifyData);
        return sig.verify(signature);
    }
}
