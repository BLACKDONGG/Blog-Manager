package com.util.md5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author BLACKDONG
 * 
 */
public class MD5 {
	public String encryptByMD5(String PPK){
		byte[] privateKey = null;
		try {
			MessageDigest MD = MessageDigest.getInstance("MD5");
			MD.update(PPK.getBytes());
			privateKey = MD.digest();
//			System.out.print(privateKey.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encode = new BigInteger(1, privateKey).toString(16);
		System.out.print(encode);
		return encode;
		
	}
	public static void main(String[] args) {
		MD5 MD = new MD5();
		MD.encryptByMD5("18150378337"+"WEID");
	}
}
