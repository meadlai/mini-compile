package com.meadlai.compile.snake;

import javafx.scene.paint.Color;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LexerGrid {
	//
	public static final Color COLOR = new Color(0.1, 0.1, 0.1, 1);
    public static final int NODE_SIZE = 15;
    //
    private final int cols;     // The number of columns
    private final int rows;     // The number of rows
    @ToString.Exclude
    private LexerSnake snake;
    //
    private double width;
    private double height;
    
    public LexerGrid(LexerSnake snake, double width, double height) {
    	this.rows = (int) width/ NODE_SIZE;
    	this.cols = (int) height/ NODE_SIZE;
    	//
    	this.width = width;
    	this.height = height;
    	this.snake = snake;
    	this.snake.setGrid(this);
    }
    
    public void update() {
    	
    }

}
