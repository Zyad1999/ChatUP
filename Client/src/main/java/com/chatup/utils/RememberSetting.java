package com.chatup.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.Properties;

public class RememberSetting {
    public static void setProperties(String phone, String password){
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            File theDir = new File("./files/config");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            output = new FileOutputStream("./files/config/remember.properties");
            prop.setProperty("PHONE_NUMBER", phone);
            System.out.println("the password is "+password);
            if(password.length()>0){
                prop.setProperty("PASSWORD",encrypt(password, getKey()));
            }else {
                prop.setProperty("PASSWORD",password);
            }

            prop.store(output, null);
        } catch (IOException | GeneralSecurityException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getPassword(){
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("./files/config/remember.properties");
            props.load(fis);
            if(props.getProperty("PASSWORD") != null && props.getProperty("PASSWORD").length()>0) {
                System.out.println("The decrepted passwrod is");
                return decrypt(props.getProperty("PASSWORD"), getKey());
            }
            System.out.println("didnot find password");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String getPhone(){
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("./files/config/remember.properties");
            props.load(fis);
            if(props.getProperty("PHONE_NUMBER") != null && props.getProperty("PHONE_NUMBER").length()>0)
                return props.getProperty("PHONE_NUMBER");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encrypt(String dataToEncrypt, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }

    static SecretKeySpec getKey() {
        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        try {
            SecretKeySpec key = RememberSetting.createSecretKey("encryptedPassword".toCharArray(), salt, iterationCount, keyLength);
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println("can't create key");
            return null;
        }
    }
}
