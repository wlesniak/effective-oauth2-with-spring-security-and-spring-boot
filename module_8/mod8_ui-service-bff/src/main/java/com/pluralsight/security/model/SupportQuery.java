package com.pluralsight.security.model;

import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@RequiredArgsConstructor
@ToString
public class SupportQuery {

	private String id;
	private String subject;
	private Calendar creationTime;
	private String username;
	private boolean resolved;
	private List<Post> posts;
	
}
