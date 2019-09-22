package com.pluralsight.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.SupportQuery;
import com.pluralsight.security.model.PostResponse;
import com.pluralsight.security.model.SupportQueryResponse;
import com.pluralsight.security.repository.SupportQueryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SupportQueryServiceNoSql implements SupportQueryService {

	private final SupportQueryRepository supportRepository;

	@Override
	public List<SupportQueryResponse> getSupportQueriesForUser(String username) {
		List<SupportQuery> supportQueries = supportRepository.findByUsername(username);
		return mapEntityToModel(supportQueries);
	}

	@Override
	public SupportQueryResponse getSupportQueryById(String id) {
		return mapEntityToModel(this.supportRepository.findById(id).get());
	}
	
	@Override
	public List<SupportQueryResponse> getSupportQueriesForAllUsers() {
		List<SupportQuery> supportQueries = this.supportRepository.findAll();
		return mapEntityToModel(supportQueries);
	}
	
	private SupportQueryResponse mapEntityToModel(SupportQuery supportQuery) {
		List<PostResponse> posts = supportQuery.getPosts().stream().map(post -> {
			return new PostResponse(post.getId(), post.getContent(),post.getUsername(),supportQuery.isResolved());
		}).collect(Collectors.toList());
		return new SupportQueryResponse(supportQuery.getId(), supportQuery.getSubject(), supportQuery.getCreated(),
				supportQuery.getUsername(), supportQuery.isResolved(), posts);

	}
	
	private List<SupportQueryResponse> mapEntityToModel(List<SupportQuery> supportQueries) {
		return supportQueries.stream().map(query -> {
			return mapEntityToModel(query);
		}).collect(Collectors.toList());
	}

}