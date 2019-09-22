package com.pluralsight.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.pluralsight.security.model.CreateSupportQueryDto;
import com.pluralsight.security.model.PostDto;
import com.pluralsight.security.model.SupportQueryDto;
import com.pluralsight.security.service.SupportQueryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SupportQueryController {
		
	private final SupportQueryService supportService;
			
	@GetMapping("/support")
	public ModelAndView getQueries() {
		return new ModelAndView("support","queries",supportService.getSupportQueriesForUser());
	}

	@GetMapping("/support/query/{id}")
	public ModelAndView getQuery(@PathVariable String id) {
		SupportQueryDto query = supportService.getSupportQueryById(id);
		ModelAndView model = new ModelAndView("query","query",query);
		PostDto newPost = new PostDto();
		newPost.setResolve(query.isResolved());
		model.addObject("newPost",new PostDto());
		return model;
	}	
	
	@GetMapping("/support/compose")
	public ModelAndView createNewSupportQuery() {
		ModelAndView model = new ModelAndView();
		model.addObject("newQuery", new CreateSupportQueryDto());
		model.setViewName("compose");
		return model;
	}
	
}
