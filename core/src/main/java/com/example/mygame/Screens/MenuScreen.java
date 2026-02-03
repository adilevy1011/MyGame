package com.example.mygame.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import com.example.mygame.Main;

public class MenuScreen implements Screen {
    

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private java.util.ArrayList<Button> buttonList; 
    private Texture background;
    private float mouseX, mouseY;
    private Button startGame, options, exit;
    private Main game;

    public MenuScreen(Main game) {
        this.game = game;
        // Initialize menu screen resources here
        
    }

    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        buttonList = new ArrayList<Button>();
        background = new Texture("menuScreenBackground.jpg");
        startGame = new Button("Start Game", Gdx.graphics.getWidth() / 2 - 100, 300, 200, 50, batch, shapeRenderer, new com.badlogic.gdx.graphics.g2d.BitmapFont()); 
        buttonList.add(startGame);
        options = new Button("Options", Gdx.graphics.getWidth() / 2 - 100, 200, 200, 50, batch, shapeRenderer, new com.badlogic.gdx.graphics.g2d.BitmapFont());
        buttonList.add(options);
        exit = new Button("Exit", Gdx.graphics.getWidth() / 2 - 100, 100, 200, 50, batch, shapeRenderer, new com.badlogic.gdx.graphics.g2d.BitmapFont());
        buttonList.add(exit);  
  }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    
    @Override
    public void render(float delta) {
        // Render menu screen here
        ScreenUtils.clear(1f,1f, 1f, 1f);
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
            b.drawText(batch); // just draws text
        }
        batch.end();
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && startGame.isClicked(mouseX, mouseY)) {
            LevelOne gameScreen=new LevelOne(game);
            game.setScreen(gameScreen);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && options.isClicked(mouseX, mouseY)) {
            // Handle options button click actions here
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && exit.isClicked(mouseX, mouseY)) {
            Gdx.app.exit();
        }
    }
    
    
    @Override
    public void dispose() {
        // Dispose menu screen resources here
        shapeRenderer.dispose();
        batch.dispose();
    }

}
