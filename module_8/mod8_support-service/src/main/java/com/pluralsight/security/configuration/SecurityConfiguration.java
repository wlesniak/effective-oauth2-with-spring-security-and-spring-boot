package com.pluralsight.security.configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import com.pluralsight.security.validators.CryptoJwtTokenValidator;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.authorizeRequests()
				.mvcMatchers("/support/admin/**").access("hasRole('ADMIN') && hasAuthority('SCOPE_portfolio-service-admin')")
				.anyRequest().authenticated()
		.and()
			.oauth2ResourceServer()
				.jwt()
					.decoder(jwtTokenDecoder())
					.jwtAuthenticationConverter(new JwtGrantedAuthoritiesConverter());;
	}

    private JwtDecoder jwtTokenDecoder() {
    	NimbusJwtDecoderJwkSupport decoder = (NimbusJwtDecoderJwkSupport)
    			JwtDecoders.fromOidcIssuerLocation("http://localhost:8081/auth/realms/CryptoInc");
    	OAuth2TokenValidator<Jwt> defaultValidators = JwtValidators
    			.createDefaultWithIssuer("http://localhost:8081/auth/realms/CryptoInc");
    	OAuth2TokenValidator<Jwt> delegatingValidator = 
    			new DelegatingOAuth2TokenValidator<>(defaultValidators, new CryptoJwtTokenValidator());
    	decoder.setJwtValidator(delegatingValidator);
    	return decoder;
    }
    
}
