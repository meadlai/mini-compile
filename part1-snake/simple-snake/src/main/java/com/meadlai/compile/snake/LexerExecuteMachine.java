package com.meadlai.compile.snake;

import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class LexerExecuteMachine implements Runnable{
	
    private final LexerGrid grid;
    private final GraphicsContext context;
    private int frameRate = 20;
    private float interval;
    private boolean running;
    private boolean paused;
    private boolean keyPressed;

    public LexerExecuteMachine(final LexerGrid igrid, final GraphicsContext context) {
        this.grid = igrid;
        this.context = context;
        // 1 second = 1000 ms
        // max is: 50 Hz refresh rate
        this.interval = 1000.0f / frameRate; 
        this.running = true;
        this.paused = false;
        this.keyPressed = false;
    }

	@Override
	public void run() {
		while (running && !paused) {
            // 
            float start = System.currentTimeMillis();
            this.keyPressed = false;
            grid.update();
            LexerPainterUtil.paint(grid, context);
            //
            float cost = System.currentTimeMillis() - start;
            // Adjust the refresh rate here
            if (cost < interval) {
                try {
                    Thread.sleep((long) (interval - cost));
                } catch (InterruptedException ignore) {
                	log.error("Adjust the refresh rate was Interrupted");
                }
            }
		}
	}
	
    public void setFrameRate(int rate) {
    	if(rate >= 50) {
    		this.frameRate = 50;
    	}else {
    		this.frameRate = rate;
    	}
        this.interval = 1000.0f / this.frameRate; 
    }

}
