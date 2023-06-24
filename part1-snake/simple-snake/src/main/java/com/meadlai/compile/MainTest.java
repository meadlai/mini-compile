package com.meadlai.compile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainTest {

	public static void main(String[] args) {
		int _10 = 10_0;
		int i = 1_12;
		i = _10;
		i = 0b00_10;
		log.info("_id is: {}", _10);
		log.info("i is: {}", i);
		String string = "\\a\rb\nc\fd\te\bf";
		log.info("string is: {}", string);
		int oct = '\b';
		oct = '\n';
		log.info("oct \b is: {} \n /n /\"", oct);
		oct = '\b';
		oct = '\\';
		oct = '\'';
		oct = '\r';
		oct = '\77';
		oct = '\7';
		oct = '\071';
		oct = '\012';
		oct = '\277';
		oct = 0xfffff;
		log.info("oct is: {}", oct);
		String s = "s0x";
		log.info("s is: {}", s);


	}

}
