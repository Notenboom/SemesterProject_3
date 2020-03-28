package utilities.functions;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Class for Hashing and validating hashed passwords
 * @author Group 6
 * @version 1.9.0
 * @implNote Implementation inspired from the "Java Secure Hashing – MD5, SHA256, SHA512, PBKDF2, BCrypt, SCrypt" article
 * by Lokesh Gupta
 */
public class HashFunctions
{
    /**
     * Publicly accessible method for enabling the hashing functionality
     * @param password
     * @return Hashed password
     */
    public static String hashPassword(String password)
    {
        try
        {
            return generatePasswordHash(password);
        }
        catch(NoSuchAlgorithmException |InvalidKeySpecException e)
        {
            e.printStackTrace();
            return "A error has occured";
        }
    }

    /**
     * Publicly accessible subroutine for enabling the validation of the hashed password
     * @param originalPassword
     * @param storedPassword
     * @return
     */
    public static Boolean checkPassword(String originalPassword, String storedPassword)
    {
        try
        {
            return validatePassword(originalPassword, storedPassword);
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method for generating the has password
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        //Number of iterations
        int iterations = 65238;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);

        //Using Password Based Key Derivation Function 2
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Salt Generator
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Password validation method
     * @param originalPassword
     * @param storedPassword
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchAlgorithmException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    /**
     * Method to convert to hex
     * @param array
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else
        {
            return hex;
        }
    }

    /**
     * Method to convert from hex
     * @param hex
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
