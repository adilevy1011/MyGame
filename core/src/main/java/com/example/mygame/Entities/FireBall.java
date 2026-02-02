package com.example.mygame.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
public class FireBall extends Sprite {
    
    private int xSpeed, ySpeed;
    private float lifetime = 0f; // how long it has existed
    private float maxLifetime = 2f; // seconds

    public FireBall(String imagePath, int x, int y, int xSpeed, int ySpeed) {
        super(imagePath, x, y, 25, 25, 100, 0);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    // call every frame
    public void update(float delta) {
        lifetime += delta;
    }

    public boolean isExpired() {
        return lifetime >= maxLifetime;
    }
    
    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.begin();
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        batch.end();
    }
    public void move() {
        this.setX(this.getX() + this.xSpeed);
        this.setY(this.getY() + this.ySpeed);
    }

}
