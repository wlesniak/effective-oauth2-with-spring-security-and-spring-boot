package com.pluralsight.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {

	@GetMapping("/{path:[^\\\\.]*}")
	public String portfolio() {
		return "forward:/";
	}
	
}
