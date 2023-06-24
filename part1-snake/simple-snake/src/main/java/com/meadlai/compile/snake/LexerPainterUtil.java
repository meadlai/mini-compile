package com.meadlai.compile.snake;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LexerPainterUtil {

	private LexerPainterUtil() {

	}

	public static void paint(LexerGrid grid, GraphicsContext gc) {
		gc.setFill(LexerGrid.COLOR);
		gc.fillRect(0, 0, grid.getWidth(), grid.getHeight());

		// Show the snake
		LexerSnake snake = grid.getSnake();
		Node header = snake.getHeader();
		LexerPainterUtil.paintNodePoint(header, gc);
		List<Node> body = snake.getBody();
		if (body == null || body.isEmpty()) {
			log.info("Sorry, the snake doesn't have body...");
			return;
		}
		//
		for (int i = body.size() - 1; i >= 0; i--) {
			Node node = body.get(i);
			node.setLocationX(header.getLocationX() - i - 1);
			node.setLocationY(header.getLocationY());
			LexerPainterUtil.paintNodePoint(node, gc);
		}

	}

	private static void paintNodePoint(Node point, GraphicsContext gc) {
		double x = point.getLocationX() * LexerGrid.NODE_SIZE;
		double y = point.getLocationY() * LexerGrid.NODE_SIZE;
		gc.setFill(Color.WHITE);
		gc.fillRect(x, y, LexerGrid.NODE_SIZE, LexerGrid.NODE_SIZE);
		if (point.getValue() == 0 || point.getValue() == '\0') {
			// pass
			log.warn("Null char value in the node");
		} else {
			gc.setFill(Color.RED);
			gc.fillText("" + point.getValue(), x, y + LexerGrid.NODE_SIZE - 2);
		}
	}
	
	

}
