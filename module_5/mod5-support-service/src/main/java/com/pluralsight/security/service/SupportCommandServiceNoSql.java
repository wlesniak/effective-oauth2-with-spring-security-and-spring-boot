package com.pluralsight.security.service;

import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.Post;
import com.pluralsight.security.entity.SupportQuery;
import com.pluralsight.security.model.CreateSupportQueryRequest;
import com.pluralsight.security.model.PostRequest;
import com.pluralsight.security.repository.SupportQueryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SupportCommandServiceNoSql implements SupportCommandService {

	private final SupportQueryRepository supportRepository;
	
	@Override
	public void createQuery(CreateSupportQueryRequest query) {
		supportRepository.save(mapModelToEntity(query));
	}
	
	@Override
	public void postToQuery(PostRequest postRequest) {
		Post post = new Post(postRequest.getUsername() , postRequest.getContent(), System.currentTimeMillis());
		SupportQuery query = supportRepository.findById(postRequest.getQueryId()).get();
		query.addPost(post);
		if(postRequest.isResolve()) {
			query.resolve();
		}
		supportRepository.save(query);
	}
	
	@Override
	public void resolveQuery(String id) {
		SupportQuery query = supportRepository.findById(id).get();
		query.resolve();
		supportRepository.save(query);
	}
	
	private SupportQuery mapModelToEntity(CreateSupportQueryRequest model) {
		SupportQuery supportQuery = new SupportQuery(model.getUsername(), model.getSubject());
		supportQuery.addPost(model.getContent(), model.getUsername() );
		return supportQuery;
	}
	
}
