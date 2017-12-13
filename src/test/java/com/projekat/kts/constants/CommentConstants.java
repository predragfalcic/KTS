package com.projekat.kts.constants;

import java.util.Date;

public class CommentConstants {

	public static final String NEW_TEXT = "new_text";
	public static final Date NEW_DATE = new Date();
	
	//id of a building who is referenced by other entities
    public static final Long DB_ID_REFERENCED = 1L; 
    
    public static final Long DB_ID_FAILURE = 4L;
    public static final Long DB_ID = 2L;
    public static final String DB_TEXT = "Prvi komentar";
	
	public static final int DB_COUNT_COMMENT = 5;
	public static final int DB_COUNT_COMMENT_BY_FAILURE = 3;
}
