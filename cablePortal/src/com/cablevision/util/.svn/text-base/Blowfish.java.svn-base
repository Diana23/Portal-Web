package com.cablevision.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author Crysfel
 *
 */
public class Blowfish {

    public static byte[] encriptar(String cleartext, String key)
            throws Exception {
    	byte[] raw = key.getBytes();
    	SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");

	    // create a cipher based upon Blowfish
	    Cipher cipher = Cipher.getInstance("Blowfish");

	    // initialise cipher to with secret key
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

	    // encrypt message
	    byte[] encrypted = cipher.doFinal(cleartext.getBytes());
	    
	    return encrypted; 
    }

 
    public static String desEncriptar(byte[] ciphertext, String key)
            throws Exception {
    	
    	byte[] raw = key.getBytes();
    	SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
    	// create a cipher based upon Blowfish
	    Cipher cipher = Cipher.getInstance("Blowfish");
    	// re-initialise the cipher to be in decrypt mode
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);

	    // decrypt message
	    byte[] decrypted = cipher.doFinal(ciphertext);  
	    return new String(decrypted);
    }   
} 
