package com.cablevision.util;

import javax.servlet.ServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;


public class ReCaptchaUtil {

	public static boolean isValido(ServletRequest request) {
		request = ScopedServletUtils.getOuterServletRequest(request);
		String remoteAddr = request.getRemoteAddr();

		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey(ConfigurationHelper.getPropiedad("recaptcha.privatekey",""));

		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");

		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

		if (reCaptchaResponse.isValid()) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isValido(ServletRequest request, String challenge, String uresponse) {
		request = ScopedServletUtils.getOuterServletRequest(request);
		String remoteAddr = request.getRemoteAddr();

		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey(ConfigurationHelper.getPropiedad("recaptcha.privatekey",""));

		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

		if (reCaptchaResponse.isValid()) {
			return true;
		} else {
			return false;
		}

	}

}
