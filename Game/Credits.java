package Game;

import java.util.Queue;
import java.awt.Graphics;
import java.io.IOException;
import java.io.File;
import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;

import gameEngine.GameEngine.State;
import gameEngine.GameImage;
import gameEngine.GameObject;
import gameEngine.Text;

public class Credits extends GameObject {
	ArrayList<GameObject> credits;
	public Credits() {
		credits = new ArrayList<GameObject>();
		x = ConwaysGame.game.getWidth()/2;
		try{make();}catch(Exception e) {e.printStackTrace();}
		dy = 1;
		ConwaysGame.game.getHandeler().add(this, false);
	}
	private void make() throws IOException {
		Queue<String> strs = new LinkedList<String>();
		Scanner s = new Scanner(new BufferedInputStream(getClass().getResource("assets/credits.txt").openStream()));
		while(s.hasNextLine())
			strs.add(s.nextLine());
		int i = 0;
		while(strs.size() > 0)
		{
			if(strs.peek().matches("\\S+.*")) {
					credits.add(new Text(strs.poll(), (int)x, ConwaysGame.game.getHeight()+i*50, 20));
			}
			else {
				strs.remove();
			}
			i++;
		}
	}
	@Override
	public void move() {
		GameObject temp = null;
		for(GameObject t: credits)
		{
			if(t.y+t.h < 0)
				temp = t;
			t.y -= dy;
		}
		if(temp != null)
		{
			credits.remove(temp);
			ConwaysGame.game.getHandeler().remove(temp);
			if(credits.size() == 0) {
				ConwaysGame.screen.updateState(State.TITLE);
			}
			temp = null;
		}
	}
}