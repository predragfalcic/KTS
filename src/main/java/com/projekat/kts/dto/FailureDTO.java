package com.projekat.kts.dto;

import com.projekat.kts.model.Failure;

public class FailureDTO {

	private Failure failure;
	private Long tenatId;
	private Long institutionId;
	
	public FailureDTO(){}

	public Failure getFailure() {
		return failure;
	}

	public void setFailure(Failure failure) {
		this.failure = failure;
	}

	public Long getTenatId() {
		return tenatId;
	}

	public void setTenatId(Long tenatId) {
		this.tenatId = tenatId;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	
}
