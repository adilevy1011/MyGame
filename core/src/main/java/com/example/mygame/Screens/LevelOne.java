package com.example.mygame.Screens;

import com.example.mygame.Main;
import com.example.mygame.Entities.*;


public class LevelOne extends GameScreen {


    public LevelOne(Main game) {
        super(game);
    }
    
    public void show() {
        super.show();
        //No background is implemented for levels for now
        for (int i = 0; i < 5; i++) { 
            getEnemies().add(new Enemy("enemy.png", 200 + i * 120, 200, 50, 50,1,300));
        }
        for(int i = 0; i < 5; i++) { 
            getObstacles().add(new Obstacle("fireball.png", i*100, 300, 40, 40));
        }
    }
    public void render(float delta) {
        super.render(delta);
        
    }
    public void dispose() {
        super.dispose();
    }
}
