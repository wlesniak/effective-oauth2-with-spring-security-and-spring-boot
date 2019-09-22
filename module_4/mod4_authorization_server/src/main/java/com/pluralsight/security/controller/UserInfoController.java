package com.pluralsight.security.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.security.userdetails.MFAUser;
import com.pluralsight.security.userdetails.UserInfo;

@RestController
public class UserInfoController {

	@GetMapping("/userinfo")
	public UserInfo userInfo(@AuthenticationPrincipal MFAUser principal) {
		return new UserInfo(principal.getUsername(), principal.getFirstName(), principal.getLastName(), principal.getEmail());
	}
	
}
