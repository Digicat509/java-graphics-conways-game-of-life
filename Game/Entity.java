package Game;

import java.util.Objects;
import gameEngine.GameObject;

import java.awt.*;

public class Entity extends GameObject implements Comparable<GameObject>{
    public static int SPEED = 10;
    public Entity(int x, int y) {
        super(2);
        this.x = x;
        this.y = y;
        w = 10;
        h = 10;
    }
    public void update() {
        int neighbors = numNeighbors();
        if(neighbors < 2 || neighbors > 3) {
            ConwaysGame.screen.toRemove.add(this);
        }
    }
    public int numNeighbors() {
        int ans = 0;
        for(int i = (int)x-1; i <= x+1; i++)
            for(int j = (int)y-1; j <= y+1; j++)
                if(!(i == x && j == y) && ConwaysGame.screen.game.contains(new Entity(i, j)))
                    ans++;
        return ans;
    }
    @Override
    public void draw(Graphics g) {
        if(ConwaysGame.screen.on)
            update();
        g.setColor(Color.white);
        g.fillRect((int)x*ConwaysGame.screen.grid.currGridSize, (int)y*ConwaysGame.screen.grid.currGridSize, w, h);
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Entity ? x == ((Entity)obj).x && y == ((Entity)obj).y: super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return ""+this.getClass()+" "+x/Grid.GRIDSIZE+", "+y/Grid.GRIDSIZE;
    }
}
