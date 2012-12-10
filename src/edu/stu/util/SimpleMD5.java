package edu.stu.util;

import java.io.UnsupportedEncodingException;
import java.security.*;

import org.apache.commons.lang.CharSet;
public class SimpleMD5 {

    private final static String[] hexDigits = {
    "0", "1", "2", "3", "4", "5", "6", "7",
    "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String md5(String src)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byteArrayToHexString(md.digest(src.getBytes("UTF-8")));
        } catch (Exception ex) {
            return null;      
		}
    }

    public static void main(String[] args) {
//        System.out.println(SimpleMD5.md5("888888"));
    	System.err.println(isChinese('中'));
    	//ード達成
    	System.err.println(isChinese('ド'));
    }
    
    public static boolean isChinese(char a) { 
        int v = (int)a; 
        return (v >=19968 && v <= 171941); 
   }
    
}
