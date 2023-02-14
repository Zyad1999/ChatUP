package com.chatup.utils;

import java.io.*;
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
            prop.setProperty("PASSWORD",password);
            prop.store(output, null);
        } catch (IOException io) {
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
                return props.getProperty("PASSWORD");
            }
            System.out.println("didnot find password");
        } catch (IOException e) {
            e.printStackTrace();
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
}
