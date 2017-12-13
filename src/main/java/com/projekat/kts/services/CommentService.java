package com.projekat.kts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.kts.model.Comment;
import com.projekat.kts.model.Failure;
import com.projekat.kts.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public List<Comment> findAll(){
		return commentRepository.findAll();
	}
	
	public List<Comment> findByFailure(Failure failure){
		return commentRepository.findByFailure(failure);
	}
	
	public Comment save(Comment comment){
		return commentRepository.save(comment);
	}
}
