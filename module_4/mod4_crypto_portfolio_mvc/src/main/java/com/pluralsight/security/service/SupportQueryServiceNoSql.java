package com.pluralsight.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.SupportQuery;
import com.pluralsight.security.model.PostDto;
import com.pluralsight.security.model.SupportQueryDto;
import com.pluralsight.security.repository.SupportQueryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SupportQueryServiceNoSql implements SupportQueryService {

	private final SupportQueryRepository supportRepository;

	@Override
	public List<SupportQueryDto> getSupportQueriesForUser() {
		List<SupportQuery> supportQueries = supportRepository.findByUsername(getUsername());
		return mapEntityToModel(supportQueries);
	}

	@Override
	public SupportQueryDto getSupportQueryById(String id) {
		return mapEntityToModel(this.supportRepository.findById(id).get());
	}
	
	@Override
	public List<SupportQueryDto> getSupportQueriesForAllUsers() {
		List<SupportQuery> supportQueries = this.supportRepository.findAll();
		return mapEntityToModel(supportQueries);
	}

	private SupportQueryDto mapEntityToModel(SupportQuery supportQuery) {
		List<PostDto> posts = supportQuery.getPosts().stream().map(post -> {
			return new PostDto(post.getId(), post.getContent(),post.getUsername(),supportQuery.isResolved());
		}).collect(Collectors.toList());
		return new SupportQueryDto(supportQuery.getId(), supportQuery.getSubject(), supportQuery.getCreated(),
				supportQuery.getUsername(), supportQuery.isResolved(), posts);

	}
	
	private String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	private List<SupportQueryDto> mapEntityToModel(List<SupportQuery> supportQueries) {
		return supportQueries.stream().map(query -> {
			return mapEntityToModel(query);
		}).collect(Collectors.toList());
	}

}