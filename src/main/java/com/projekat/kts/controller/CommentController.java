package com.projekat.kts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.fabric.xmlrpc.base.Array;
import com.projekat.kts.dto.CommentFailureDTO;
import com.projekat.kts.model.AppUser;
import com.projekat.kts.model.Comment;
import com.projekat.kts.model.Failure;
import com.projekat.kts.repository.AppUserRepository;
import com.projekat.kts.services.CommentService;
import com.projekat.kts.services.FailureService;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private FailureService failureService;
	
	/**
	 * Web service for getting all the comments for the selected failure
	 * 
	 * @return List<Comment>
	 */
	@PreAuthorize("hasRole('ROLE_WORKER') || hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/komentar/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> comments(@PathVariable Long id) {
		Failure f = failureService.findOneById(id);
		
		if(f == null){
			return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Comment>>(commentService.findByFailure(f), HttpStatus.OK);
	}
	
	/**
	 * Web service for adding comment to failure
	 * 
	 * @return List<Comment>
	 */
	@PreAuthorize("hasRole('ROLE_WORKER') || hasRole('ROLE_STANAR')")
	@RequestMapping(value = "/komentar", method = RequestMethod.POST)
	public ResponseEntity<List<Comment>> addComment(@RequestBody CommentFailureDTO cf) {
		if(cf == null || cf.getComment() == null || cf.getFailure() == null || cf.getAuthor() == null){
			return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
		}
		
		Comment comment = cf.getComment();
		AppUser author = cf.getAuthor();
		Failure failure = cf.getFailure();
		
		// Ukoliko su liste komentara null inicijalizujemo nove liste
		if(author.getComments() == null){
			author.setComments(new ArrayList<>());
		}
		if(failure.getComments() == null){
			failure.setComments(new ArrayList<>());
		}
		
		// Set data to the comment
		comment.setAuthor(author);
		comment.setFailure(failure);
		comment.setDateCreated(new Date());
		commentService.save(comment);
		
		// Set data to author of the comment
		author.getComments().add(comment);
		appUserRepository.save(author);
		
		// Set data to the failure
		failure.getComments().add(comment);
		failureService.save(failure);
		
		return new ResponseEntity<List<Comment>>(commentService.findByFailure(failure), HttpStatus.OK);
	}
	
}
