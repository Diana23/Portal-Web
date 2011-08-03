package com.cablevision.util.sso;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AuteticaGoogle {
	public static final String AUTENTICA_VALIDO="1";
	public static final String AUTENTICA_NO_VALIDO="0";
	
	public static void main(String[] args) {
		autentica("superbebejj@cablevision.net.mx", "whxq2w");
	}
	
	public static String autentica(String usuario,String password){
		try {
		    // Construct data
		    String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(usuario, "UTF-8") +
		    	"&" + URLEncoder.encode("Passwd", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
		    	"&" + URLEncoder.encode("accountType", "UTF-8") + "=" + URLEncoder.encode("HOSTED_OR_GOOGLE", "UTF-8") +
		    	"&" + URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode("cl", "UTF-8") +
		    	"&" + URLEncoder.encode("source", "UTF-8") + "=" + URLEncoder.encode("cablevision-portal-1", "UTF-8");

		    // Send data
		    URL url = new URL("https://www.google.com/accounts/ClientLogin");
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    System.out.println(data);
		    wr.write(data);
		    wr.flush();

		    // Get the response
		    BufferedReader rd = null;
		    try{
		    	rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    }catch (IOException e) {
		    	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
		    String line;
		    StringBuffer respuesta = new StringBuffer();
		    while ((line = rd.readLine()) != null) {
		        respuesta.append(line);
		    }
		    wr.close();
		    rd.close();
		    System.out.println("Respuesta: "+respuesta);
		    if(respuesta.toString().startsWith("SID=")||respuesta.toString().startsWith("Error=AccountDisabled")){
		    	return AUTENTICA_VALIDO;
		    }else if(respuesta.toString().startsWith("Error=")){
		    	return AUTENTICA_NO_VALIDO;
		    }else{
		    	return respuesta.toString();
		    }
		}catch(FileNotFoundException fne){
			fne.printStackTrace();
			return AUTENTICA_NO_VALIDO;
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return AUTENTICA_NO_VALIDO;
	}
}
