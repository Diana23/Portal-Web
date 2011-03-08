package com.cablevision.util;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.access.DefaultLocatorFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.stellent.getfile.GetFile;
import com.stellent.getfile.GetFileByNameResult;

public class MailUtil {
	
	public  static void sendMail(String message,String subject,String to,String from) throws NamingException, MessagingException{
		sendMail(message, subject, to, from, false);
	}
	
	public  static void sendMail(String message,String subject,String to,String from,boolean isHtml) throws NamingException, MessagingException{
		InitialContext ic = new InitialContext();
		Session session = (Session)ic.lookup("cablevisionMailSession"); 
		//session.setDebug(true);
		
		Properties props = session.getProperties();
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setSession(session);
		
		
		Message msg = new MimeMessage(session);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		try {
			msg.setFrom(new InternetAddress(props.getProperty("mail.smtp.from"),props.getProperty("mail.smtp.fromName")));
		} catch (UnsupportedEncodingException e) {
			msg.setFrom(new InternetAddress(props.getProperty("mail.smtp.from")));
		}
				
		
		
		InternetAddress[] toAddress = InternetAddress.parse(to, false);
		msg.setRecipients(Message.RecipientType.TO, toAddress);
		
		if(isHtml){
			msg.setContent(message, "text/html");
		}else{
			msg.setText(message);
		}
		 
		
		
		if(StringUtils.isNotEmpty(props.getProperty("mail.smtp.user"))){
			Transport trans = null;
			try{
				trans = session.getTransport("smtp");
				trans.connect(props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.pass"));
				trans.sendMessage(msg, toAddress);
			}finally{
				if(trans!=null){
					trans.close();
				}
				
			}
		}else{
			Transport.send(msg);
		}
		
		
	}
	
	
	public  static void sendMail(String message,String subject,String to,String from,boolean isHtml, String pathFoldersArchivoAdjunto, String fileName) throws NamingException, MessagingException{
		InitialContext ic = new InitialContext();
		Session session = (Session)ic.lookup("cablevisionMailSession"); 
		//session.setDebug(true);
		
		Properties props = session.getProperties();
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setSession(session);
		
		
		Message msg = new MimeMessage(session);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		try {
			msg.setFrom(new InternetAddress(props.getProperty("mail.smtp.from"),props.getProperty("mail.smtp.fromName")));
		} catch (UnsupportedEncodingException e) {
			msg.setFrom(new InternetAddress(props.getProperty("mail.smtp.from")));
		}
				
		
		
		InternetAddress[] toAddress = InternetAddress.parse(to, false);
		msg.setRecipients(Message.RecipientType.TO, toAddress);
		
		if(isHtml){
			msg.setContent(message, "text/html");
		}else{
			msg.setText(message);
		}
		 
		BodyPart texto = new MimeBodyPart();
		texto.setText(message);
		
		BodyPart adjunto = new MimeBodyPart();
		adjunto.setDataHandler(new DataHandler(new FileDataSource(pathFoldersArchivoAdjunto + "/" + fileName)));
		adjunto.setFileName(fileName);
		
		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(texto);
		multiParte.addBodyPart(adjunto);
		
		MimeMessage mimeMessage = new MimeMessage(session);
		// Se rellena el From
		mimeMessage.setFrom(new InternetAddress(from));
		// Se rellenan los destinatarios
		mimeMessage.addRecipients(Message.RecipientType.TO, toAddress);
		// Se rellena el subject
		mimeMessage.setSubject(subject);
		// Se mete el texto y la foto adjunta.
		mimeMessage.setContent(multiParte);
		
		
		if(StringUtils.isNotEmpty(props.getProperty("mail.smtp.user"))){
			Transport trans = null;
			try{
				trans = session.getTransport("smtp");
				trans.connect(props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.pass"));
				trans.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			}finally{
				if(trans!=null){
					trans.close();
				}
				
			}
		}else{
			Transport.send(msg);
		}
		
		
	}
	
	
	public  static void sendMail() throws NamingException, MessagingException{
		InitialContext ic = new InitialContext();
		Session session = (Session)ic.lookup("cablevisionMailSession"); 
		
		session.setDebug(true);
		
		
		Message msg = new MimeMessage(session);
		msg.setSubject("Probando");
		msg.setSentDate(new Date());
		msg.setFrom(InternetAddress.parse("Descorcia@corp.cablevision.net.mx")[0]);
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("luis.perez@jwmsolutions.com", false));
		msg.setText("Probando enviada de mail"); 
		
		
		Transport.send(msg); 
	}
	
	
	public  static void sendMail(String subject,String to,String from,String templateId,Map<String, String> values) throws NamingException, MessagingException, RemoteException, ServiceException, UnsupportedEncodingException{
		GetFileByNameResult result = getFileClient().getGetFileSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),ConfigurationHelper.getPropiedad("ucm.password").getBytes()
			).getFileByName(templateId, "latest", "primary", null);
		
		String template = new String(
				result.getDownloadFile().getFileContent(),"UTF-8"
		);
		
		
		
		if(values!=null){
			//Remplazar valores en mapa
			for (String key : values.keySet()) {
				template = template.replaceAll("\\{"+key+"\\}", values.get(key)!=null?values.get(key):"");
			}
		}
		
		sendMail(template, subject, to, from,true);
	}
	
	private static GetFile getFileClient() {
		BeanFactory factory = DefaultLocatorFactory.getInstance("classpath*:/com/cablevision/conf/cablevisionBeanRefContext.xml")
		.useBeanFactory("cablevision-context")
		.getFactory();

		
		return (GetFile) factory.getBean("GetFileClient");
	}
}
