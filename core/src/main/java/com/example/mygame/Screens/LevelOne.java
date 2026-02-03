package com.example.mygame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.example.mygame.Main;
import com.example.mygame.Entities.*;


public class LevelOne extends GameScreen {

    private Texture background; //temp
    public LevelOne(Main game) {
        super(game);
        setWorldBoundries(-Gdx.graphics.getWidth(), Gdx.graphics.getWidth(), -Gdx.graphics.getHeight(), Gdx.graphics.getHeight());
    }
    
    public void show() {
        super.show();
        background = new Texture("grassBackground.jpg"); //temp
        //No background is implemented for levels for now
        for (int i = 0; i < 5; i++) { 
            getEnemies().add(new Enemy("enemy.png",  i * 120-200, 200, 50, 50,1,300));
        }
        for(int i = -4; i < 9; i++) { 
            for(int j = -1; j < 1; j++) {
                getObstacles().add(new Obstacle("fireball.png", i*150, j*200+400, 40, 40)); 
           }
        }
    }
    @Override
    public void addToBackground() {
        //No background for levels for now
        getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//temp
        getBatch().draw(background, Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getBatch().draw(background, -Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getBatch().draw(background, 0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//temp
        getBatch().draw(background, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getBatch().draw(background, -Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getBatch().draw(background, 0, -Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//temp
        getBatch().draw(background, Gdx.graphics.getWidth(), -Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getBatch().draw(background, -Gdx.graphics.getWidth(), -Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
    }
    public void render(float delta) {
        super.render(delta);
        
    }
    public void dispose() {
        super.dispose();
        background.dispose();
    }
}
