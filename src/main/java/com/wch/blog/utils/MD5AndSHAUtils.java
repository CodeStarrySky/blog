package com.wch.blog.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5AndSHAUtils {

    public static String md5AndSHA(String password){
        String beforePassword = null;
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            String tmp = new BigInteger(1, md5.digest()).toString(16);
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(tmp.getBytes());
            beforePassword = new BigInteger(1,sha.digest()).toString(16);
        }catch (NoSuchAlgorithmException ex){
            System.out.println(ex);
        }
        return beforePassword;
    }


}
