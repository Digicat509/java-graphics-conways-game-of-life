package Game;

import gameEngine.GameEngine;
import gameEngine.GameEngine.State;
import gameEngine.Text;
import gameEngine.GameData;

public class ConwaysGame {
    static GameEngine game;
    static GameData gameData;
    static Sound sound;
    static Screen screen;

    public static void main(String[] args)
    {
        gameData = new GameData();
        ConwaysGame conwaysGame = new ConwaysGame();
    }

    public ConwaysGame()
    {
        game = new GameEngine();
        game.setTitle("Conways Game of Life!");
        game.setWidth((int)(game.getWidth()*1.5));
        game.setScale(3f);
        game.title();
        sound = new Sound();
        screen = new Screen();
        screen.updateState(State.TITLE);
        game.start();
    }
}