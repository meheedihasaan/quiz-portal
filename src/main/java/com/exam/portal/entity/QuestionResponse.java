package com.exam.portal.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponse {

	List<Question> responses = new ArrayList<>();
	
	public void add(Question question) {
		responses.add(question);
	}
	
}
