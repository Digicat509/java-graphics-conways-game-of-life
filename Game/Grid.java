package Game;

import java.awt.Color;
import java.awt.Graphics;

import gameEngine.GameObject;

public class Grid extends GameObject {
	public static final int GRIDSIZE = 10;
	public int currGridSize = 10;
	public Grid()
	{
		super(5);
		ConwaysGame.game.getHandeler().add(this, false);
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		for(int x = (int)this.x-currGridSize; x < ConwaysGame.game.getWidth()+currGridSize; x+= currGridSize)
			for(int y = (int)this.y-currGridSize; y < ConwaysGame.game.getHeight()+currGridSize; y += currGridSize)
				g.drawRect(x, y, currGridSize, currGridSize);
	}
}
