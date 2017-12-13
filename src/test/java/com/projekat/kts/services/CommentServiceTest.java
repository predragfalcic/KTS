package com.projekat.kts.services;

import static com.projekat.kts.constants.CommentConstants.DB_COUNT_COMMENT_BY_FAILURE;
import static com.projekat.kts.constants.CommentConstants.DB_ID_FAILURE;
import static com.projekat.kts.constants.CommentConstants.NEW_DATE;
import static com.projekat.kts.constants.CommentConstants.NEW_TEXT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.projekat.kts.model.Comment;
import com.projekat.kts.model.Failure;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService commentService;

	@Autowired
	private FailureService failureService;
	
	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		Comment comment = new Comment();
		comment.setText(NEW_TEXT);
		comment.setDateCreated(NEW_DATE);
		
		int dbSizeBeforeAdd = commentService.findAll().size();
		
		Comment dbComment = commentService.save(comment);
		assertThat(dbComment).isNotNull();
				
		// Validate that new comment is in the database
        List<Comment> comments = commentService.findAll();
        assertThat(comments).hasSize(dbSizeBeforeAdd + 1);
        dbComment = comments.get(comments.size() - 1); //get last comment
        
		assertThat(comment.getText()).isEqualTo(NEW_TEXT);
        assertThat(comment.getDateCreated()).isEqualTo(NEW_DATE);           
	}
	
	@Test
	public void testOneFailure() {
		Failure failure = failureService.findOneById(DB_ID_FAILURE);
		List<Comment> comments = commentService.findByFailure(failure);
		assertThat(comments).hasSize(DB_COUNT_COMMENT_BY_FAILURE); 
	}
}
