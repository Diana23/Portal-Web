package com.cablevision.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarPasswordUtil {
	
	public static boolean validatePassword(String password) {
		// ^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9_-]{8,25}$
		// ((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})
		//^(?=.*[a-zA-Z])(?=.*[0-9])[@#$%!&/()=?�a-zA-Z0-9_-]{8,25}$ Original
		Pattern patron = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&/()=?]).{8,25})");
        Matcher match = patron.matcher(password);
        return match.matches();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(validatePassword("#123-MI_password@$%")); 
		System.out.println(validatePassword("Luis2hp?"));
	}

}