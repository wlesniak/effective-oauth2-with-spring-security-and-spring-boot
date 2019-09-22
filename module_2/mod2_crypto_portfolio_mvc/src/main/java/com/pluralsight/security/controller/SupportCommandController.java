package com.pluralsight.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pluralsight.security.model.CreateSupportQueryDto;
import com.pluralsight.security.model.PostDto;
import com.pluralsight.security.service.SupportCommandService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SupportCommandController {

	private final SupportCommandService supportService;
	
	@PostMapping("/support")
	public String createNewQuery(@ModelAttribute CreateSupportQueryDto createSupportQueryDto) {
		supportService.createQuery(createSupportQueryDto);
		return "redirect:/support";
	}

	@PostMapping("/support/query/{id}")
	public String postToQuery(@ModelAttribute PostDto postDto, @PathVariable String id) {
		postDto.setQueryId(id);
		supportService.postToQuery(postDto);
		if(postDto.isResolve()) {
			this.supportService.resolveQuery(id);
		}
		return "redirect:/support/query/"+postDto.getQueryId();
	}
	
}
