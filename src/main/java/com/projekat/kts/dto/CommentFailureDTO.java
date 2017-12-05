package com.projekat.kts.dto;

import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Comment;
import com.projekat.kts.model.Failure;

public class CommentFailureDTO {

	private Comment comment;
	private Failure failure;
	private AppUser author;
	
	public CommentFailureDTO(){
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Failure getFailure() {
		return failure;
	}

	public void setFailure(Failure failure) {
		this.failure = failure;
	}

	public AppUser getAuthor() {
		return author;
	}

	public void setAuthor(AppUser author) {
		this.author = author;
	}
}
