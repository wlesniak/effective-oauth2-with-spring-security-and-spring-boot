package com.pluralsight.security.model;

import java.util.Calendar;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupportQueryResponse {

	private String id;
	private String subject;
	private Calendar creationTime;
	private String username;
	private boolean resolved;
	private List<PostDto> posts;
	
}
