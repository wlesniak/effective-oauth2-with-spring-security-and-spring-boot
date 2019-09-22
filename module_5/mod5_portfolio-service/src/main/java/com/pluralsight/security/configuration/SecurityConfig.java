package com.pluralsight.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.pluralsight.security.service.PortfolioCommandService;
import com.pluralsight.security.service.PortfolioQueryService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final PortfolioCommandService portfolioCommandService;
	private final PortfolioQueryService  portfolioQueryService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and().oauth2ResourceServer().jwt();
	}
	
	@EventListener
	public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
		JwtAuthenticationToken jwt = (JwtAuthenticationToken)event.getSource();
		String username = jwt.getToken().getClaimAsString("preferred_username");
		if(!this.portfolioQueryService.userHasAportfolio(username)) {
			this.portfolioCommandService.createNewPortfolio(username);
		}
	}
	
}
