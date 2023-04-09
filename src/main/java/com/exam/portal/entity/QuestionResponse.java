package com.exam.portal.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

	private List<Question> responses = new ArrayList<>();
	
	public void add(Question question) {
		responses.add(question);
	}
	
}
