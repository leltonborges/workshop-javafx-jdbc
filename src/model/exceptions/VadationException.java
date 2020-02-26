package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class VadationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors = new HashMap<>();

	public VadationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErrors(){
		return errors;
	}
	
	public void addErrors(String fieldName, String errorMessege) {
		errors.put(fieldName, errorMessege);
	}
} 
