package com.pluralsight.security.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.security.model.SupportQueryResponse;
import com.pluralsight.security.service.SupportQueryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	
	private final SupportQueryService supportQueryService;
	
	@GetMapping("/support/admin")
	public List<SupportQueryResponse> getSupportQueries() {
		return supportQueryService.getSupportQueriesForAllUsers();
	}
		
}
