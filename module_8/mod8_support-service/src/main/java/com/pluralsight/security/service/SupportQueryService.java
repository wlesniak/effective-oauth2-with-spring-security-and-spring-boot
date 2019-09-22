package com.pluralsight.security.service;

import java.util.List;

import com.pluralsight.security.model.SupportQueryResponse;

public interface SupportQueryService {

	List<SupportQueryResponse> getSupportQueriesForUser(String username);
	SupportQueryResponse getSupportQueryById(String queryId);
	List<SupportQueryResponse> getSupportQueriesForAllUsers();
	
}
