package com.meadlai.compile;

public class Utils {

	private Utils() {
		// private
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isDigitDecimal(int c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isOctalDigit(int c) {
		return c >= '0' && c <= '7';
	}

	public static boolean isBinaryDigit(int c) {
		return c == '0' || c == '1';
	}

	public static boolean isDecimalDigit(int c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isHexDigit(int c) {
		return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
	}

}
