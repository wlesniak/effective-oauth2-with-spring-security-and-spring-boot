package com.pluralsight.security.service;

import com.pluralsight.security.model.CreateSupportQueryDto;
import com.pluralsight.security.model.PostDto;

public interface SupportCommandService {

	void createQuery(CreateSupportQueryDto query);
	void postToQuery(PostDto supportQueryPostModel);
	void resolveQuery(String id);
	
}
