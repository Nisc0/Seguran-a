package handlers;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class EncryptManager {

    //TODO: interface?
    private static EncryptManager ourInstance;
    private static final String KEYSTORE_PWD = ""; //FIXME
    private static final String KEYSTORE_FILEPATH = ""; //FIXME
    private static final String KEYSTORE_ALIAS = ""; //FIXME
    private Cipher rsaCipher, aesCipher;
    private Signature sig;
    private KeyPair kp;

    public static EncryptManager getInstance() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (ourInstance == null)
            ourInstance = new EncryptManager();
        return ourInstance;
    }

    private EncryptManager() throws NoSuchAlgorithmException, NoSuchPaddingException {
        rsaCipher = Cipher.getInstance("RSA");
        aesCipher = Cipher.getInstance("AES");
        sig = Signature.getInstance("SHA256withRSA");
    }

    private Key getKey(File userDir, File userFile) throws IOException, KeyStoreException, UnrecoverableKeyException,
            NoSuchAlgorithmException, CertificateException, IllegalBlockSizeException, InvalidKeyException {
        File keyFile = new File(userDir, userFile.getName() + ".key");
        Key K;
        if (!keyFile.exists()) {
            K = createKey();

            //Encontrar Ku
            FileInputStream keyStoreFile = new FileInputStream(KEYSTORE_FILEPATH);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(keyStoreFile, KEYSTORE_PWD.toCharArray());

            Key key = keyStore.getKey(KEYSTORE_ALIAS, KEYSTORE_PWD.toCharArray());

            PublicKey publicKey;

            if (key instanceof PrivateKey) {
                Certificate cert = keyStore.getCertificate(KEYSTORE_ALIAS);
                publicKey = cert.getPublicKey();
                kp = new KeyPair(publicKey, (PrivateKey) key);
            } else {
                keyStoreFile.close();
                throw new SecurityException();
            }

            //cifrar com Ku
            rsaCipher.init(Cipher.WRAP_MODE, kp.getPublic());
            byte[] wrappedBytes = rsaCipher.wrap(K);
            saveKey(wrappedBytes, keyFile);

            keyStoreFile.close();
        } else {
            K = getSecretKey(keyFile);
        }

        return K;
    }

    public byte[] encrypt(byte[] plainData, File userDir, File userFile) throws CertificateException,
            UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyStoreException,
            IllegalBlockSizeException, KeyException, BadPaddingException {
        Key k = getKey(userDir, userFile);
        aesCipher.init(Cipher.ENCRYPT_MODE, k);
        return aesCipher.doFinal(plainData);
    }

    public byte[] decrypt(byte[] cipherData, File userDir, File userFile) throws InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException {
        //TODO se nao houver chave no decrypt, se nao houver um keyfile????
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

    private void saveKey(byte[] wrappedKey, File keyFile) throws IOException {
        FileOutputStream fout = new FileOutputStream(keyFile);
        fout.write(wrappedKey);
        fout.close();
    }

    public byte[] signFile(byte[] toSign, File userDir, File userFile) throws InvalidKeyException, SignatureException,
            IOException {
        File sigFile = new File(userDir, userFile.getName() + ".sig");
        sig.initSign(kp.getPrivate());
        sig.update(toSign);
        byte[] signature = sig.sign();
        FileOutputStream fout = new FileOutputStream(sigFile);
        fout.write(signature);
        fout.close();
        return signature;
    }

    public boolean isVerifiedFile(byte[] toVerifyData, File userDir, File userFile) throws InvalidKeyException,
            IOException, SignatureException {
        File sigFile = new File(userDir, userFile.getName() + ".sig");
        sig.initVerify(kp.getPublic());
        byte[] signature = Files.readAllBytes(sigFile.toPath());
        sig.update(signature);
        return sig.verify(toVerifyData);
    }
}
