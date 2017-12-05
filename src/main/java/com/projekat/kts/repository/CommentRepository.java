package com.projekat.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.kts.model.Comment;
import com.projekat.kts.model.Failure;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	public List<Comment> findByFailure(Failure failure);
}
