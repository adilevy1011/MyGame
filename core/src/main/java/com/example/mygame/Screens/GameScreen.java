package com.example.mygame.Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.mygame.Entities.Enemy;
import com.example.mygame.Entities.FireBall;
import com.example.mygame.Entities.Player;

public class GameScreen extends Screen {
    
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<FireBall> fireBalls;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture background;
    public GameScreen() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        background = new Texture("grassBackground.jpg");
        fireBalls = new ArrayList<FireBall>();
        player = new Player("sprite.png", 100, 100, 70, 70, 100, 3);
        enemies = new ArrayList<Enemy>();

        for (int i = 0; i < 3; i++) {
            enemies.add(new Enemy("enemy.png", 200 + i * 120, 200, 50, 50,1));
        }
    }
    
    @Override
    public void render() {
        ScreenUtils.clear(1f,1f, 1f, 1f);
        float delta = Gdx.graphics.getDeltaTime(); 
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        
        if(player.isAlive()){
            player.draw(batch, shapeRenderer);
            player.update(delta);
            player.heal(1);
            if(fireBalls != null){
                for(FireBall fireBall : fireBalls) {
                    fireBall.draw(batch, shapeRenderer);
                    fireBall.update(delta);
                    fireBall.move();
                    if(fireBall.isExpired()) {
                        fireBalls.remove(fireBall);
                        break;
                    }
                }
            }
            
            
            
            if (Gdx.input.isKeyPressed(Input.Keys.W) && player.getY() < Gdx.graphics.getHeight() - player.getHeight()) {
                player.moveUp();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && player.getY() > 0) {
                player.moveDown();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.getX() > 0) {
                player.moveLeft();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.getX() < Gdx.graphics.getWidth() - player.getWidth()) {
                player.moveRight();
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
    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
