package com.pluralsight.security.controller;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.security.model.SupportQueryResponse;
import com.pluralsight.security.service.SupportQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SupportQueryController {
		
	private final SupportQueryService supportService;
			
	@GetMapping("/support/{username}")
	@PreAuthorize("(hasRole('ADMIN') && hasAuthority('SCOPE_portfolio-service-admin')) || (hasRole('USER') && #username == authentication.token.claims['preferred_username'])")
	public List<SupportQueryResponse> getQueries(@PathVariable String username) {
		return supportService.getSupportQueriesForUser(username);
	}
	
	@GetMapping("/support")
	@PostAuthorize("returnObject.username = #username")
	public List<SupportQueryResponse> getQueries(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		return supportService.getSupportQueriesForUser(principal.getToken().getClaimAsString("preferred_username"));
	}

	@GetMapping("/support/query/{id}")
	public SupportQueryResponse getQuery(@PathVariable String id) {
		return supportService.getSupportQueryById(id);
		
	}	
	
}
