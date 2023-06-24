package com.meadlai.compile.snake;

import java.util.Observable;
import java.util.Observer;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <p>
 * <li>Snake area
 * <li>Input source code;
 * <li>Show status;
 * <li>Show token list
 * </p>
 * 
 * @author meadlai
 *
 */
@Slf4j
public class LexerMain extends Application implements Observer {
	//
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final float GLASS_SCALE = .3f;
	//
	private LexerSnake snake;
	private LexerGrid grid;
	private LexerExecuteMachine executor;
	private GraphicsContext context;
	private Group root;
	private Canvas canvas;
	private Group glasses;
	private volatile boolean running = false;
//	private static ReentrantLock lock = new ReentrantLock();

	@Override
	public void start(Stage primaryStage) {
		// init GUI
		this.root = new Group();
		canvas = new Canvas(WIDTH, HEIGHT);
		context = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		// init objects
		this.initMainObjects();
		this.initEventLisener();
		this.initGlass();

		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Lexer With Snake");
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		primaryStage.setScene(scene);
		primaryStage.show();

		//
		Thread thread = new Thread(executor);
		thread.setName("Lexer-Execute-Machine ");
		thread.setDaemon(true);
		thread.start();
	}

	private void initMainObjects() {
		Node header = Node.create().setHeader();
		header.setLocationX(7);
		header.setLocationY(1);
		this.snake = new LexerSnake(header);
		char ch = 65;
		for (int i = 0; i < 5; i++) {
			Node node = new Node();
			node.setLocationX(i + 2);
			node.setLocationY(1);
			node.setValue((char) (ch + i));
			this.snake.getBody().add(node);
		}
		this.grid = new LexerGrid(this.snake, WIDTH, HEIGHT);
		this.executor = new LexerExecuteMachine(this.grid, this.context);
		//
		this.snake.addObserver(this);
	}

	/**
	 * put the glasses on the top of snake header
	 */
	private void initGlass() {
		Circle eyeRight = new Circle(0, 0, 5);
		eyeRight.setStroke(Color.BLACK);
		eyeRight.setFill(Color.WHITE);
		Circle eyeLeft = new Circle(10, 0, 5);
		eyeLeft.setStroke(Color.BLACK);
		eyeLeft.setFill(Color.WHITE);
		this.glasses = new Group(eyeRight, eyeLeft);
		double x = this.snake.getHeader().getLocationX() * LexerGrid.NODE_SIZE;
		double y = this.snake.getHeader().getLocationY() * LexerGrid.NODE_SIZE;
		this.glasses.setTranslateX(x);
		this.glasses.setTranslateY(y);
		this.root.getChildren().add(glasses);
	}

	private void initEventLisener() {
		this.canvas.setFocusTraversable(true);
		this.canvas.setOnKeyPressed(e -> {
			if (executor.isKeyPressed()) {
				return;
			}
			//
			switch (e.getCode()) {
			case SPACE:
			case ENTER:
			case RIGHT:
				log.info("Move right with KeyPressed: {}", e.getCode());
				this.snake.move();
				this.updateGlass();
				break;
			case NUMPAD1:
				log.info("Peek one char with KeyPressed: {}", e.getCode());
				this.snake.peek1();
				this.glassPeek(1);
				break;
			case NUMPAD2:
				log.info("Peek two char with KeyPressed: {}", e.getCode());
				this.snake.peek2();
				this.glassPeek(2);
				break;
			case LEFT:
				log.info("LEFT KeyPressed: {}", e.getCode());
				this.snake.back();
				this.updateGlass();
				break;
			default:
				log.info("Unkown KeyPressed: {}", e.getCode());
				break;
			}
		});
	}

	private void glassPeek(int step) {
		log.warn("Peeking: {}, running is: {}", step, running);
		if(this.running) {
			log.error("Peeking: {}, running is: {}", step, running);
			return;
		}
		//
		this.running = true;
		//
		int delt = step * LexerGrid.NODE_SIZE + 6;
		log.info("glassPeek");
		// relocate
		double x = this.snake.getHeader().getLocationX() * LexerGrid.NODE_SIZE;
		double y = this.snake.getHeader().getLocationY() * LexerGrid.NODE_SIZE;
		this.glasses.setTranslateX(x);
		this.glasses.setTranslateY(y);
		//
		TranslateTransition moveFwd = new TranslateTransition(Duration.millis(300), this.glasses);
		moveFwd.setByX(delt);
		moveFwd.setCycleCount(1);
		moveFwd.setAutoReverse(true);

		TranslateTransition moveBack = new TranslateTransition(Duration.millis(300), this.glasses);
		moveBack.setByX(-delt);
		moveBack.setCycleCount(1);
		moveBack.setAutoReverse(true);

		ScaleTransition scale = new ScaleTransition(Duration.millis(300), this.glasses);
		scale.setByX(GLASS_SCALE);
		scale.setByY(GLASS_SCALE);
		scale.setCycleCount(2);
		scale.setAutoReverse(true);
		//

		SequentialTransition sq = new SequentialTransition(moveFwd, scale, moveBack);
		sq.play();
		sq.setOnFinished(event -> {
			log.info("Transition finished");
			this.running = false;
		});
		log.warn("END || this.running is: {}", this.running);

	}
	
	private void updateGlass() {
		double x = this.snake.getHeader().getLocationX() * LexerGrid.NODE_SIZE;
		double y = this.snake.getHeader().getLocationY() * LexerGrid.NODE_SIZE;
		this.glasses.setTranslateX(x);
		this.glasses.setTranslateY(y);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void update(Observable o, Object arg) {
		log.info("Observable is: {}", o);
		log.info("arg is: {}", arg);
		LexerSnake isnake = null;
		if (arg instanceof LexerSnake) {
			isnake = (LexerSnake) arg;
		} else {
			return;
		}
		if (this.snake == isnake) {
			log.info("Snake is eqaul");
		}
		switch (this.snake.getAction()) {
		case "peek1":
//			this.glassPeek(1);
			break;
		case "peek2":
//			this.glassPeek(2);
			break;
		default:
			log.error("No action maps to {}", this.snake.getAction());
			break;
		}

	}
}