package au.edu.uts.aip.accounts;

import java.security.*;

public class Sha {

    /**
     * Encrypts a string to a hash256
     * @param data that needs to be encoded to hash256
     * @return encrypted hash256 
     * @throws NoSuchAlgorithmException 
     */
    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }
    /**
     * Encrypts an array of bytes to a encrypted Hex String
     * @param bytes of the chars that will be encrypted
     * @return String encrypted Hex String
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
