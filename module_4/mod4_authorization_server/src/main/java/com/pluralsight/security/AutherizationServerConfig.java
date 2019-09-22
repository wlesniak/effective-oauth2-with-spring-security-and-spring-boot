package com.pluralsight.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


@Configuration
@EnableAuthorizationServer
public class AutherizationServerConfig extends AuthorizationServerConfigurerAdapter 
{
	private final PasswordEncoder encoder;
	
	
	public AutherizationServerConfig(PasswordEncoder encoder) {
		this.encoder=encoder;
	}
	
	@Override
    public void configure(
        AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("crypto-portfolio")
							.authorizedGrantTypes("authorization_code")
							.secret(encoder.encode("secret"))
							.scopes("user_info")
							.redirectUris("http://localhost:8080/login/oauth2/code/crypto-portfolio")
							.autoApprove(false);	
	}
	
}
