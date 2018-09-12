package com.mydream.utils;
/**
 * 加密方法类
 * @author LiPengWei 
 * @date 2018年9月7日
 */

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class EncryptUtil {
	
	/**
	 * 获取MD5加密字符串
	 * @param encryptString
	 * @return
	 */
	public static String stringToMD5(String encryptString) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
            		encryptString.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
	
	/**
	 * 把字符串进行base64加密
	 * @param encryptString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String stringToBASE64(String encryptString)  {
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] textByte = null;
		try {
			textByte = encryptString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encoder.encodeToString(textByte);
	}
	
	/**
	 * 把base64加密的字符串解密
	 * @param base64String
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String base64ToString(String base64String) {
		 Base64.Decoder decoder = Base64.getDecoder();
		 String str = null;
		 try {
			str = new String(decoder.decode(base64String), "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return str;
	}
	
	public static void main(String[] args) {
		String str = "123456";
		String md5Str = new EncryptUtil().stringToMD5(str);
		
		String sst = new EncryptUtil().stringToBASE64(str);
		
	}

}
