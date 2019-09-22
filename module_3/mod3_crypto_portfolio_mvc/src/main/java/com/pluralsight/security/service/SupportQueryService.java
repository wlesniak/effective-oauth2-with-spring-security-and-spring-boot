package com.pluralsight.security.service;

import java.util.List;

import com.pluralsight.security.model.SupportQueryDto;

public interface SupportQueryService {

	List<SupportQueryDto> getSupportQueriesForUser();
	SupportQueryDto getSupportQueryById(String queryId);
	List<SupportQueryDto> getSupportQueriesForAllUsers();
	
}
