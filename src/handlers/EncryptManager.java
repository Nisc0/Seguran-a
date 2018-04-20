package handlers;

import exceptions.PrivateKeyNotFoundException;
import sun.security.pkcs11.wrapper.CK_ATTRIBUTE;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.Certificate;

public class EncryptManager {

    //TODO: interface?
    private static EncryptManager ourInstance;
    private static final String KEYSTORE_PWD = ""; //FIXME
    private static final String KEYSTORE_FILEPATH = ""; //FIXME
    private static final String KEYSTORE_ALIAS = ""; //FIXME
    private Cipher pkCipher, aesCipher;
    private Signature sig;
    private KeyPair kp;

    public static EncryptManager getInstance() {
        if (ourInstance == null)
            ourInstance = new EncryptManager();
        return ourInstance;
    }


    public EncryptManager() { //TODO: tratar do IOException - quando der bosta, eh pra fechar!
        pkCipher = Cipher.getInstance("RSA");
        aesCipher = Cipher.getInstance("AES");
        sig = Signature.getInstance("SHA256withRSA");
    }

    private Key getKey(File userDir, File userFile) throws PrivateKeyNotFoundException { //TODO: exceptions
        File keyFile = new File(userDir, userFile.getName() + ".key");
        SecretKey K;
        if (!keyFile.exists()) {
            K = createKey();

            //Encontrar Ku
            FileInputStream keyStoreFile = new File(KEYSTORE_FILEPATH);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(keyStoreFile, KEYSTORE_PWD.toCharArray());

            Key key = keyStore.getKey(KEYSTORE_ALIAS, KEYSTORE_PWD.toCharArray());

            PublicKey publicKey;

            if (key instanceof PrivateKey) {
                Certificate cert = keyStore.getCertificate(KEYSTORE_ALIAS);
                publicKey = cert.getPublicKey();
                kp = new KeyPair(publicKey, (PrivateKey) key);
            } else {
                //fixme - meter no relatorio e ques
                throw new PrivateKeyNotFoundException();
            }

            //cifrar com Ku
            pkCipher.init(Cipher.WRAP_MODE, kp.getPublic());
            byte[] wrappedBytes = pkCipher.wrap(util.BytesUtil.toByteArray(K));
            saveKey(wrappedBytes, keyFile);

        } else {
            K = getSecretKey(keyFile);
        }

        return K;
    }

    public byte[] encrypt(byte[] plainData, File userDir, File userFile) {
        Key k = getKey(userDir, userFile);
        aesCipher.init(Cipher.ENCRYPT_MODE, k);
        return aesCipher.doFinal(plainData);
    }

    public byte[] decrypt(byte[] cipherData, File userDir, File userFile) {
        //TODO se nao houver chave no decrypt, se nao houver um keyfile????
        File keyFile = new File(userDir, userFile.getName() + ".key");
        Key k = getSecretKey(keyFile);
        aesCipher.init(Cipher.DECRYPT_MODE, k);
        aesCipher.doFinal(cipherData);
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

    private SecretKey getSecretKey(File keyFile) {
        byte[] encryptedKey = Files.readAllBytes(keyFile.toPath());
        pkCipher.init(Cipher.UNWRAP_MODE, kp.getPrivate());
        return pkCipher.unwrap(encryptedKey, "RSA", Cipher.SECRET_KEY);
    }

    private void saveKey(byte[] wrappedKey, File keyFile) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(keyFile);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(wrappedKey);
            fout.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] signFile(byte[] toSign, File userDir, File userFile){
        File sigFile = new File(userDir, userFile.getName() + ".sig");
        sig.initSign(kp.getPrivate());
        sig.update(toSign);
        byte[] signature = sig.sign();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sigFile));
        oos.writeObject(signature);
        return signature;
    }

    public boolean isVerifiedFile(byte[] toVerifyData, File userDir, File userFile){
        File sigFile = new File(userDir, userFile.getName() + ".sig");
        sig.initVerify(kp.getPublic());
        byte[] signature = Files.readAllBytes(sigFile.toPath());
        sig.update(signature);
        return sig.verify(toVerifyData);
    }

    //TODO: metodos
    //cifrar
        //hash (java faz?)
    //decifar
    //assinar ficheiro
    //verificar a assinatura do ficheiro
    //
}
