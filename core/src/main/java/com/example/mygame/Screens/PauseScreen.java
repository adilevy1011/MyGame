package com.example.mygame.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.mygame.Main;

public class PauseScreen implements Screen {
    private Main game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private java.util.ArrayList<Button> buttonList; 
    private Texture background;
    private float mouseX, mouseY;
    private Button startGame, options, exit;
    public PauseScreen(Main game) {
        this.game = game;
        
    }
    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        buttonList = new ArrayList<Button>();
        background = new Texture("grassBackground.jpg");
        startGame = new Button("Resume Game", Gdx.graphics.getWidth() / 2 - 100, 300, 200, 50, batch, shapeRenderer, new com.badlogic.gdx.graphics.g2d.BitmapFont()); 
        buttonList.add(startGame);
        options = new Button("Options", Gdx.graphics.getWidth() / 2 - 100, 200, 200, 50, batch, shapeRenderer, new com.badlogic.gdx.graphics.g2d.BitmapFont());
        buttonList.add(options);
        exit = new Button("Exit Game", Gdx.graphics.getWidth() / 2 - 100, 100, 200, 50, batch, shapeRenderer, new com.badlogic.gdx.graphics.g2d.BitmapFont());
        buttonList.add(exit);  
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    
    @Override
    public void render(float delta) {
        // Render pause screen here
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, com.badlogic.gdx.Gdx.graphics.getWidth(), com.badlogic.gdx.Gdx.graphics.getHeight());
        batch.end();
        // draw buttons
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Button b : buttonList) {
            b.drawShape(shapeRenderer); // just draws rect
        }
        shapeRenderer.end();
        batch.begin();
        for (Button b : buttonList) {
            b.drawText(batch); // draws text
        }
        batch.end();
         mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && startGame.isClicked(mouseX, mouseY)) {
            game.setScreen(new GameScreen(game));
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && options.isClicked(mouseX, mouseY)) {
            // Handle options button click actions here
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && exit.isClicked(mouseX, mouseY)) {
            Gdx.app.exit();
        }
    }
    
    @Override
    public void dispose() {}
}
