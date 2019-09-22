package com.pluralsight.security.service;

import com.pluralsight.security.model.CreateSupportQueryRequest;
import com.pluralsight.security.model.PostRequest;

public interface SupportCommandService {

	void createQuery(CreateSupportQueryRequest query);
	void postToQuery(PostRequest supportQueryPostModel);
	void resolveQuery(String id);
	
}
