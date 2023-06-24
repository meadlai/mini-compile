package com.meadlai.compile;



public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	//
	private final Location location;

	public ParseException(String message, Location location) {
		super(message);
		this.location = location;
	}
	
	public ParseException(String message, Location location, Throwable cause) {
		super(message, cause);
		this.location = location;
	}

	@Override
	public String getMessage() {
		return (this.location != null ? this.location.toString() + ": " + super.getMessage() : super.getMessage());
	}
}
