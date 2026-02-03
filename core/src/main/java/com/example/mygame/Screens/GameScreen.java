package com.example.mygame.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.mygame.Entities.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.example.mygame.Main;

public class GameScreen implements Screen {
    
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<FireBall> fireBalls;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    //private Texture background; //temp
    private OrthographicCamera camera;
    private Main game;
    public GameScreen(Main game) {
        this.game = game;
    }
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        //background = new Texture("grassBackground.jpg"); //temp
        fireBalls = new ArrayList<FireBall>();
        player = new Player("sprite.png", 100, 100, 70, 70, 100, 3);
        enemies = new ArrayList<Enemy>();
        obstacles = new ArrayList<Obstacle>();
        camera = new OrthographicCamera();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f,1f, 1f, 1f);
        camera.position.set(player.getX() + player.getWidth()/2f, player.getY() + player.getHeight()/2f, 0);
        
        camera.update();
        float d = Gdx.graphics.getDeltaTime(); 
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        //batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//temp
        batch.end();
        
        if(player.isAlive()){
            player.draw(batch, shapeRenderer);
            player.update(d);
            player.heal(1);
            if(fireBalls != null){
                for(FireBall fireBall : fireBalls) {
                    fireBall.draw(batch, shapeRenderer);
                    fireBall.update(d);
                    fireBall.move();
                    if(fireBall.isExpired()) {
                        fireBalls.remove(fireBall);
                        break;
                    }
                }
            }
            
            
            
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.moveUp();
                for(Sprite obstacle : obstacles) {
                    if(obstacle.collidesWith(player)) {
                        player.moveDown();
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.moveDown();
                for(Sprite obstacle : obstacles) {
                    if(obstacle.collidesWith(player)) {
                        player.moveUp();
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.moveLeft();
                for(Sprite obstacle : obstacles) {
                    if(obstacle.collidesWith(player)) {
                        player.moveRight();
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.moveRight();
                for(Sprite obstacle : obstacles) {
                    if(obstacle.collidesWith(player)) {
                        player.moveLeft();
                    }
                }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                fireBalls.add(player.shootFireBall(0, 10));
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                fireBalls.add(player.shootFireBall(0, -10));
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                fireBalls.add(player.shootFireBall(10, 0)); 

            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                fireBalls.add(player.shootFireBall(-10, 0)); 
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                game.setScreen(new PauseScreen(game));
            }

        }
        
        if(!obstacles.isEmpty()) {
            for(Sprite obstacle : obstacles) {
                obstacle.draw(batch, shapeRenderer);
                if(fireBalls != null){
                        for(FireBall fireBall : fireBalls) {
                            if(obstacle.collidesWith(fireBall)) {
                                fireBalls.remove(fireBall);
                                break;
                            }
                        }
                    }
            }
        }

        if(!enemies.isEmpty()) {
                for (int i = 0; i < enemies.size(); i++) {
                    
                    enemies.get(i).draw(batch, shapeRenderer);
                    enemies.get(i).takeStepTowardsPlayer(player);
                    if(fireBalls != null){
                        for(FireBall fireBall : fireBalls) {
                            if(fireBall.collidesWith(enemies.get(i))) {
                                enemies.get(i).takeDamage(20);
                                fireBalls.remove(fireBall);
                                break;
                            }
                        }
                    }
                    if(enemies.get(i).isDead()) {
                        enemies.remove(i);
                        if(i != 0)
                            i--; // Adjust index after removal
                    }
                    if(!enemies.isEmpty()){
                        if (player.isAlive() && player.collidesWith(enemies.get(i))) {
                            player.takeDamage(enemies.get(i).getDamage());
                            player.resetHitTimer();
                        }
                    }
                }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        //background.dispose(); //temp
        batch.dispose();
        shapeRenderer.dispose();
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
    public SpriteBatch getBatch() {
        return batch;
    }
}
