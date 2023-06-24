package com.meadlai.compile.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.meadlai.compile.Lexer;
import com.meadlai.compile.Token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
@ToString
public class LexerSnake extends Observable implements Lexer{
	//
	@ToString.Exclude
	private List<Node> body;
	private Node header;
	@ToString.Exclude
	private LexerGrid grid;
	//
	private String action;
    private int xVelocity;
    private int yVelocity;

	public LexerSnake(Node initHeadNode) {
		//
		this.body = new ArrayList<>();
		//
		if (initHeadNode == null) {
			this.header = Node.create().setHeader();
		} else {
			this.header = initHeadNode;
		}
	}

	@Override
	public int peek1() {
		log.info("Peek1");
		this.action = "peek1";
		this.changing();
		return ' ';
	}

	@Override
	public int peek2() {
		log.info("Peek2");
		this.action = "peek2";
		this.changing();
		return ' ';
	}
	
	@Override
	public char eat() {
		this.changing();
		return ' ';
	}

	@Override
	public Token produce() {
		this.changing();
		return null;
	}

	public void move() {
		int x = this.header.getLocationX() + 1;
		if (x > this.grid.getCols()) {
			this.changeLine();
			return;
		}
		this.header.setLocationX(x);
	}

	public void back() {
		int x = this.header.getLocationX() - 1;
		int y = this.header.getLocationY() - 1;
		if (x <= 1) {
			this.header.setLocationX(this.grid.getCols());
			if (y <= 1) {
				log.error("Can't move up with y axis");
				this.header.setLocationY(y);
			}
			return;
		}
		this.header.setLocationX(x);
	}
	
	public void goUp() {
        if (yVelocity == 1 && this.getLenght() > 1) return;
        xVelocity = 0;
        yVelocity = -1;
    }

    public void goDown() {
        if (yVelocity == -1 && this.getLenght() > 1) return;
        xVelocity = 0;
        yVelocity = 1;
    }

    public void goLeft() {
        if (xVelocity == 1 && this.getLenght() > 1) return;
        xVelocity = -1;
        yVelocity = 0;
    }

    public void goRight() {
        if (xVelocity == -1 && this.getLenght() > 1) return;
        xVelocity = 1;
        yVelocity = 0;
    }

	public void changeLine() {
		int y = this.header.getLocationY() + 1;
		this.header.setLocationX(1);
		this.header.setLocationY(y);
	}



	public int getLenght() {
		return this.body.size();
	}

	private void changing() {
		this.setChanged();
		this.notifyObservers(this);
		this.clearChanged();
		this.action = "none";
	}

}
