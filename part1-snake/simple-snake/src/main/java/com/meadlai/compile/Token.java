package com.meadlai.compile;

import java.io.Serializable;

import com.meadlai.compile.java.TokenType;

import lombok.Data;

@Data
public class Token implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	//
	private TokenType type;
	private String literal;
	private String fileName;
	private int lineNumber;
	private int columnNumber;
	private Location location;

	public Token(String tokenFileName, int tokenLineNumber, int tokenColumnNumber, TokenType tokenType, String value) {
		this.fileName = tokenFileName;
		this.lineNumber = tokenLineNumber;
		this.columnNumber = tokenColumnNumber;
		this.type = tokenType;
		this.literal = value;
		this.location = new Location(tokenFileName, tokenLineNumber, tokenColumnNumber);
	}

	public Token(Location tokenLocation, TokenType type, String value) {
		this.fileName = tokenLocation.getFileName();
		this.lineNumber = tokenLocation.getLineNumber();
		this.columnNumber = tokenLocation.getColumnNumber();
		this.location = tokenLocation;
		this.type = type;
		this.literal = value;
	}

}
