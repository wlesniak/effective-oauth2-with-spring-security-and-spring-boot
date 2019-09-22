package com.pluralsight.security.userdetails;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import lombok.Getter;

public class AdditionalAuthenticationDetails extends WebAuthenticationDetails{

	@Getter
	private String securityPin;

	public AdditionalAuthenticationDetails(HttpServletRequest request) {
		super(request);
		String securityPin = request.getParameter("securityPin");
		if(securityPin != null) {
			this.securityPin=securityPin;
		}
	}
	
}
