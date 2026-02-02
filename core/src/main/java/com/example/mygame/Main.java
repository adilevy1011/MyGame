package com.example.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.example.mygame.Screens.*;

/**
 * Main game class that initializes and runs the screens.
 */
public class Main extends ApplicationAdapter {
    
    
    private GameScreen gameScreen;
    /**
     * Called when the application is created.
     */
    @Override
    public void create() {
        gameScreen = new GameScreen();
    }
    /** 
     * Called every frame to render the game.
     */
    @Override
    public void render() {
        gameScreen.render();
        
    }
    /** 
     * Called when the application is destroyed to clean up resources.
     */
    @Override
    public void dispose() {
        gameScreen.dispose();
    }
}
