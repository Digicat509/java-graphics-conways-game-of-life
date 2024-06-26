package gameEngine;

import java.awt.*;
import java.util.*;

public class GameEngine implements Runnable {
	
	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		ge.start();
	}
	private int width = 320, height=240;
	private float scale = 1f;
	private String title = "Nothing to see here";
	private Window window;
	private Input input;
	private Thread thread;
	private boolean running = false;
	private static int MAXFPS = 60;
	private double UPDATE_CAP;
	boolean render = false;
	double frame_time = 0;
	int fps = 0;
	int frames = 0;
	Handler hand;
	public State state;
	
	public GameEngine() {
		hand = new Handler(this);
	}
	public void start() {
		UPDATE_CAP = 1.0/MAXFPS;
		window = new Window(this);
		thread = new Thread(this);
		input = new Input(this);
		thread.run();
	}
	public void stop() {
		stopPlay();
	}
	public void run() {
		running = true;
		double first_time = 0;
		double last_time = System.nanoTime()/1000000000.0;
		double passed_time = 0;
		double unproccessed_time = 0;
		
		while(running) {
			first_time = System.nanoTime()/1000000000.0;
			passed_time = first_time - last_time;
			last_time = first_time;
			
			render = false;
			unproccessed_time += passed_time;
			frame_time += passed_time;
			
			while(unproccessed_time >= UPDATE_CAP) {
				unproccessed_time -= UPDATE_CAP;
				//render later
				render = true;
				
				if(frame_time >=1.0) {
					frame_time = 0;
					fps = frames;
					frames = 0;
					System.out.println("FPS: " + fps);
				}
			}
			if(render) {
				input.update();
				window.update();
				frames++;
			}		
			else {
				try{Thread.sleep(1);}catch (Exception e){e.printStackTrace();}
			}
		}
	}
	public enum State {
		TITLE,
		PLAYING,
		CREDITS,
		HELP,
		LOADING,
		STOP_PLAY
	}
	public void play() {
		state = State.PLAYING;
	}
	public void stopPlay() {
		state = State.STOP_PLAY;
	}
	public void title() {
		state = State.TITLE;
	}
	public void credits() {
		state = State.CREDITS;
	}
	public void help() {
		state = State.HELP;
	}
	public State getState() {
		return state;
	}
	public int getHeight() {
		return (int)(height*scale);
	}
	public int getWidth() {
		return (int)(width*scale);
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float s)
	{
		scale = s;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getTitle() {
		return title;
	}
	public Input getInput() {
		return input;
	}
	public Window getWindow() {
		return window;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Handler getHandeler()
	{
		return hand;
	}
	public int getFps() {
		return fps;
	}
	public void setMaxFPS(int i) { MAXFPS = i;UPDATE_CAP = 1.0/MAXFPS;}
	public void update(Graphics g) {
		if(state == State.STOP_PLAY) {
			hand.clear();
			title();
		}
		hand.render(g);
	}
}