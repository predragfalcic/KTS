package com.projekat.kts.dto;

import java.util.Date;

import com.projekat.kts.model.Failure;

public class FailureDateDTO {
	private Failure failure;
	private Long date;
	
	public FailureDateDTO(){}

	public Failure getFailure() {
		return failure;
	}

	public void setFailure(Failure failure) {
		this.failure = failure;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}
