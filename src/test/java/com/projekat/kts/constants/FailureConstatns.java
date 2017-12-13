package com.projekat.kts.constants;

import java.util.Date;

public class FailureConstatns {

	public static final String NEW_NAME = "new_failure";
	public static final String NEW_DESCRIPTION = "new_description";
	public static final Date NEW_DATE_CREATED = new Date();
	public static final Date NEW_DATE_SOLVED = new Date();
	public static final Date NEW_DATE_ZAKAZANO = new Date();

	public static final boolean NEW_HAS_SOLVED = false;
	public static final boolean NEW_HAS_WORKER = false;
	public static final boolean NEW_HAS_ZAKAZANO = false;
	
	//id of a failure who is referenced by other entities
    public static final Long DB_ID_REFERENCED = 2L; 
    
    public static final Long DB_ID_BUILDING = 1L;
    public static final Long DB_ID_WORKER = 5L;
    
    public static final Long DB_ID = 4L;
    public static final String DB_DESCRIPTION = "Neki kvar 1";
    public static final String DB_NAME = "Kvar 1";
	public static final boolean DB_HAS_SOLVED = true;
	public static final boolean DB_HAS_WORKER = true;
	public static final boolean DB_HAS_ZAKAZANO = true;
	
	public static final int DB_COUNT_FAILURES = 3;
	public static final int DB_COUNT_BY_BUILDING = 3;
	public static final int DB_COUNT_BY_WORKER = 1;
}
