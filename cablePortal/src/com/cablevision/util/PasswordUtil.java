package com.cablevision.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class PasswordUtil {
	public static String getEncodedPassword(String clearTextPassword)throws NoSuchAlgorithmException {
	  MessageDigest md = MessageDigest.getInstance("MD5");
	  
	  try {
	   md.update(clearTextPassword.getBytes("UTF-8"));
	  } catch (UnsupportedEncodingException e) {
	   md.update(clearTextPassword.getBytes());
	  }
	  
	  byte raw[] = md.digest(); //step 4
	     String hash = (new BASE64Encoder()).encode(raw); //step 5
	     return hash;
	 }
}

