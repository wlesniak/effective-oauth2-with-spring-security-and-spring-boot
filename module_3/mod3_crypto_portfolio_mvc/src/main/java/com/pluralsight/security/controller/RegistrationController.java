package com.pluralsight.security.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pluralsight.security.model.UserDto;
import com.pluralsight.security.service.PortfolioCommandService;
import com.pluralsight.security.service.UserRegistrationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

	private final UserRegistrationService registrationService;
	private final PortfolioCommandService portfolioService;
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user",new UserDto());
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		this.registrationService.registerNewUser(user);
		this.portfolioService.createNewPortfolio(user.getUsername());
		
		return "redirect:register?success";
	}
	
}
