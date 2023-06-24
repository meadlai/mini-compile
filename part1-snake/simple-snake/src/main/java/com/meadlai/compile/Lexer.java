package com.meadlai.compile;

public interface Lexer {
	int peek1();
	int peek2();
	char eat();
	Token produce();
}
