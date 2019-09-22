package com.pluralsight.security.service;

import static com.pluralsight.security.util.AuthenticationUtil.getUsername;

import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.Post;
import com.pluralsight.security.entity.SupportQuery;
import com.pluralsight.security.model.CreateSupportQueryDto;
import com.pluralsight.security.model.PostDto;
import com.pluralsight.security.repository.SupportQueryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SupportCommandServiceNoSql implements SupportCommandService {

	private final SupportQueryRepository supportRepository;
	
	@Override
	public void createQuery(CreateSupportQueryDto query) {
		supportRepository.save(mapModelToEntity(query));
	}
	
	@Override
	public void postToQuery(PostDto model) {
		Post post = new Post(getUsername() , model.getContent(), System.currentTimeMillis());
		SupportQuery query = supportRepository.findById(model.getQueryId()).get();
		query.addPost(post);
		if(model.isResolve()) {
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
	
	private SupportQuery mapModelToEntity(CreateSupportQueryDto model) {
		SupportQuery supportQuery = new SupportQuery(getUsername() , model.getSubject());
		supportQuery.addPost(model.getContent(), getUsername() );
		return supportQuery;
	}
		
}
