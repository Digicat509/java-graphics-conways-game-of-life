package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import gameEngine.Button;
import gameEngine.GameEngine.State;
import gameEngine.GameImage;
import gameEngine.GameObject;
import gameEngine.Text;
import gameEngine.Timer;

public class Screen extends GameObject{

	HashSet<Entity> game = new HashSet<>();
	ArrayList<Entity> toAdd = new ArrayList<>();
	ArrayList<Entity> toRemove = new ArrayList<>();
	ArrayList<Button> list = new ArrayList<Button>();
	Color color;
	String font;
	private boolean soundOn = true;
	private Timer keyPressDelayTimer = new Timer(0);
	private Timer keyWaitTimer = new Timer(0);
	private GameImage editBackground;
	Grid grid;
	private Button soundButton;
	int mx = 0, my = 0;
	boolean on;
	long globalTime;

	public Screen() {
		super(Integer.MIN_VALUE);
		color = new Color(164, 143, 181);
		font = "roboto";
		ConwaysGame.game.getHandeler().add(this, false);
	}
	public void updateState(State state)
	{
		ConwaysGame.sound.stopAll();
		ConwaysGame.game.getHandeler().clear();
		ConwaysGame.game.state = state;
		ConwaysGame.game.getHandeler().add(ConwaysGame.screen, false);
		if(state == State.TITLE) {
			keyWaitTimer = new Timer(1);
			new GameImage(-1, getClass().getResource("assets/GameImage1.png"), 0, 0, ConwaysGame.game.getWidth(), ConwaysGame.game.getHeight());
			new Text(ConwaysGame.game.getTitle(), ConwaysGame.game.getWidth()/2-25, 200, 40, font, new Color(36, 31, 41));
			list.add(new Button("Play", ConwaysGame.game.getWidth()/2-75, 300, 100, 50, font, color, getClass().getResource("assets/Button.png"), () -> {this.updateState(State.PLAYING);}));
			list.add(new Button("Credits", ConwaysGame.game.getWidth()/2-75, 580, 100, 50, font, color, getClass().getResource("assets/Button.png"), () -> {this.updateState(State.CREDITS);}));
			soundButton = new Button(ConwaysGame.game.getWidth()-125, 20, 75, 75, getClass().getResource("assets/Sound.png"), () -> {soundOn = !soundOn;try{soundButton.setImage(soundOn?ImageIO.read(getClass().getResource("assets/Sound.png")):ImageIO.read(getClass().getResource("assets/NoSound.png")));}catch(Exception e) {e.printStackTrace();}});
			list.add(soundButton);
		}
		else if(state == State.PLAYING)
		{
			new GameImage(-1, getClass().getResource("assets/GameImage1.png"), 0, 0, ConwaysGame.game.getWidth(), ConwaysGame.game.getHeight());
			list = new ArrayList<Button>();
			ConwaysGame.sound.audio.setFramePosition(0);
			ConwaysGame.sound.audio.loop(Clip.LOOP_CONTINUOUSLY);
			grid = new Grid();
		}
		else if(state == State.CREDITS)
		{
			new Credits();
			list = new ArrayList<Button>();
			ConwaysGame.sound.creditsAudio.setFramePosition(0);
			ConwaysGame.sound.creditsAudio.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	public void draw(Graphics g) {
		if(!soundOn) {
			ConwaysGame.sound.stopAll();
		}
		if(ConwaysGame.game.getInput().isKey(KeyEvent.VK_R)) {
			ConwaysGame.game.stop();
			ConwaysGame.screen.updateState(State.TITLE);
		}
		if(ConwaysGame.game.state == State.TITLE)
		{
			if(keyWaitTimer.timeUp() && ConwaysGame.game.getInput().isMouseClicked()) {
				int mx = ConwaysGame.game.getInput().getMouseX();
				int my = ConwaysGame.game.getInput().getMouseY();
				for(Button b: list)
				{
					b.clickBox(mx, my);
					
				}
			}
			if(ConwaysGame.game.getInput().isKey(KeyEvent.VK_ENTER) && keyWaitTimer.timeUp())
				this.updateState(State.PLAYING);
		}
		if(ConwaysGame.game.state == State.PLAYING)
		{
			if(on) {
				ConwaysGame.game.setMaxFPS(2);
				globalTime = System.currentTimeMillis();
				int minX = Integer.MAX_VALUE;
				int maxX = Integer.MIN_VALUE;
				int minY = Integer.MAX_VALUE;
				int maxY = Integer.MIN_VALUE;
				for(Entity e: game) {
					if(e.x < minX)
						minX = (int)e.x;
					if(e.x > maxX)
						maxX = (int)e.x;
					if(e.y < minY)
						minY = (int)e.y;
					if(e.y > maxY)
						maxY = (int)e.y;
				}
				for (int x = (int) minX-1; x <= maxX+1; x++) {
					for (int y = (int) minY-1; y <= maxY+1; y++) {
						Entity e = new Entity(x, y);
						if (e.numNeighbors() == 3 && !game.contains(e)) {
							toAdd.add(e);
						}
					}
				}
				if(!toAdd.isEmpty()) {
					game.addAll(toAdd);
					for(Entity e: toAdd)
						ConwaysGame.game.getHandeler().add(e, false);
					toAdd.clear();
				}
				if(!toRemove.isEmpty()) {
					game.removeAll(toRemove);
					for(Entity e: toRemove)
						ConwaysGame.game.getHandeler().remove(e);
					toRemove.clear();
				}
			}
			else {
				ConwaysGame.game.setMaxFPS(60);
			}
			if(ConwaysGame.game.getInput().isMouseClicked() && !ConwaysGame.game.getInput().isButton(2)) {
				mx = ConwaysGame.game.getInput().getMouseX()/ grid.currGridSize;
				my = ConwaysGame.game.getInput().getMouseY()/grid.currGridSize;
				Entity e = new Entity(mx, my);
				game.add(e);
				ConwaysGame.game.getHandeler().add(e, false);
			}
			if(ConwaysGame.game.getInput().isKey(KeyEvent.VK_ENTER) && keyPressDelayTimer.timeUp()) {
				on = !on;
				keyPressDelayTimer = new Timer(.1);
			}
		}
	}
}
