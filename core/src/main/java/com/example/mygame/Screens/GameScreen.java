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
    private int worldBoundryX1, worldBoundryX2, worldBoundryY1, worldBoundryY2;
    private boolean worldBoundriesSet = false;
    public GameScreen(Main game) {
        this.game = game;
    }
    
    public void setWorldBoundries(int x1, int x2, int y1, int y2) {
        this.worldBoundryX1 = x1;
        this.worldBoundryX2 = x2;
        this.worldBoundryY1 = y1;
        this.worldBoundryY2 = y2;
        worldBoundriesSet = true;
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
        if(worldBoundriesSet){
            float halfW = camera.viewportWidth / 2f;
            float halfH = camera.viewportHeight / 2f;

            // Center camera on player
            float camX = player.getX() + player.getWidth() / 2f;
            float camY = player.getY() + player.getHeight() / 2f;

            // Clamp camera so it never shows outside bounds
            camX = Math.max(worldBoundryX1 + halfW, camX);
            camX = Math.min(worldBoundryX2 - halfW, camX);

            camY = Math.max(worldBoundryY1 + halfH, camY);
            camY = Math.min(worldBoundryY2 - halfH, camY);
            camera.position.set(camX, camY, 0);
            camera.update();
        } else {
            camera.position.set(player.getX() + player.getWidth()/2f, player.getY() + player.getHeight()/2f, 0);
            camera.update();
        }
        
        float d = Gdx.graphics.getDeltaTime(); 
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        addToBackground();
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
                if(!worldBoundriesSet || player.getY() + player.getHeight() < worldBoundryY2)
                    player.moveUp();
                for(Sprite obstacle : obstacles) {
                    if(obstacle.collidesWith(player)) {
                        player.moveDown();
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                if(!worldBoundriesSet || player.getY() > worldBoundryY1)
                    player.moveDown();
                for(Sprite obstacle : obstacles) {
                    if(obstacle.collidesWith(player)) {
                        player.moveUp();
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                if(!worldBoundriesSet || player.getX() > worldBoundryX1)
                    player.moveLeft();
                for(Sprite obstacle : obstacles) {
                    if(obstacle.collidesWith(player)) {
                        player.moveRight();
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                if(!worldBoundriesSet || player.getX() + player.getWidth() < worldBoundryX2)
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

    public void addToBackground(){}
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
