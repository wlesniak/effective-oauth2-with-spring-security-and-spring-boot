package com.pluralsight.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.pluralsight.security.service.PortfolioService;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PortfolioService portfolioService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()//.csrf().disable()
		.authorizeRequests().anyRequest()
		.authenticated().and().oauth2ResourceServer().jwt();
	}
	
	@EventListener
	public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
		JwtAuthenticationToken jwt = (JwtAuthenticationToken)event.getSource();
		if(!this.portfolioService.userHasPortfolio(jwt.getToken())) {
			this.portfolioService.createPortfolio(jwt.getToken());
		}
	}
	
}