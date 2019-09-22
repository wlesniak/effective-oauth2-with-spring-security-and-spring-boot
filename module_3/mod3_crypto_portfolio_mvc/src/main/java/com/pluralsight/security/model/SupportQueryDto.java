package com.pluralsight.security.model;

import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class SupportQueryDto {

	private final String id;
	private final String subject;
	private final Calendar creationTime;
	private final String username;
	private final boolean resolved;
	private final List<PostDto> posts;
	
}
