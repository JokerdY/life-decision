package com.life.decision.support.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String encryption(String str){
        byte[] digest = null;
        // md5加密
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        if(digest==null){
            return null;
        }
        return new BigInteger(1,digest).toString(16);
    }
}
