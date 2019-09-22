package com.pluralsight.security.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class RestTemplateConfig {
	
	//@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
			JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			request.getHeaders().add("Authorization", "Bearer " + jwt.getToken().getTokenValue());
			return execution.execute(request, body);
		};
		return builder.additionalInterceptors(interceptor).build();
	}
	
}
