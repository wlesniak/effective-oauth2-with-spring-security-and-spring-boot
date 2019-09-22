package com.pluralsight.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import com.pluralsight.security.service.CryptoOidcUserService;
import com.pluralsight.security.userdetails.AdditionalAuthenticationDetailsSource;
import com.pluralsight.security.userdetails.AdditionalAuthenticationProvider;
import com.pluralsight.security.userdetails.FacebookConnectUser;
import com.pluralsight.security.userdetails.Oauth2AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AdditionalAuthenticationProvider additionalProvider;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;
	@Autowired
	private Oauth2AuthenticationSuccessHandler oauthSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.formLogin().loginPage("/login")
				.failureUrl("/login-error")
				.defaultSuccessUrl("/portfolio",true)
				.authenticationDetailsSource(new AdditionalAuthenticationDetailsSource())
				.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository)
				.and()
			.oauth2Login()
				.loginPage("/login")
				.successHandler(oauthSuccessHandler)
				.userInfoEndpoint()
					.customUserType(FacebookConnectUser.class, "facebook")
					.oidcUserService(new CryptoOidcUserService())
				.and()
			.and()
			.authorizeRequests()
				.mvcMatchers("/register","/login","/login-error",
						"/login-verified").permitAll()
				.mvcMatchers("/portfolio/**").hasRole("USER")
				.mvcMatchers("/support/**").hasAnyRole("USER","ADMIN")
				.mvcMatchers("/support/admin/**").access("isFullyAuthenticated() and hasRole('ADMIN')")
				.mvcMatchers("/api/users").hasRole("ADMIN")
				.mvcMatchers("/api/users/{username}/portfolio")
					.access("@isPortfolioOwnerOrAdmin.check(#username)")
				.anyRequest().denyAll();

		// @formatter:on
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/webjars/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(additionalProvider);
	}
	
	@Bean
	public RedirectStrategy getRedirectStrategy() {
		return new DefaultRedirectStrategy();
	}
	
	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		DelegatingPasswordEncoder encoder =  (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;	
	}	
	
}