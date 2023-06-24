package com.meadlai.compile;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
    public static final Location NOWHERE = new Location("<No Location>", -1, -1);

	//
	private String fileName;
	private int lineNumber;
	private int columnNumber;

	public Location(String fileName, int lineNumber, int columnNumber) {
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}

}
