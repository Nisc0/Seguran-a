package handlers;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface IEncryptManager {

    /**
     * Encrypt the given Data
     * @param plainData - not encrypted Data
     * @param userDir - the User's directory
     * @param userFile - the User's file
     * @return the encrypted Data
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws KeyException
     * @throws BadPaddingException
     */
    byte[] encrypt(byte[] plainData, File userDir, File userFile) throws NoSuchAlgorithmException, IOException,
            IllegalBlockSizeException, KeyException, BadPaddingException;

    /**
     * Decrypt the given Data
     * @param cipherData - encrypted Data
     * @param userDir - the User's directory
     * @param userFile - the User's file
     * @return the decrypted Data
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    byte[] decrypt(byte[] cipherData, File userDir, File userFile) throws InvalidKeyException,
                    BadPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException;

    /**
     * Sign a file using SHA256
     * @param toSign - the file to sign
     * @param userDir - the User's directory
     * @param userFile - the User's file
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     */
    void signFile(byte[] toSign, File userDir, File userFile) throws InvalidKeyException, SignatureException,
                            IOException;

    /**
     * Verifies a File
     * @param toVerifyData - the file to verify
     * @param userDir - the User's directory
     * @param userFile - the User's file
     * @return
     * @throws InvalidKeyException
     * @throws IOException
     * @throws SignatureException
     */
    boolean isVerifiedFile(byte[] toVerifyData, File userDir, File userFile) throws InvalidKeyException,
                                    IOException, SignatureException;
}
