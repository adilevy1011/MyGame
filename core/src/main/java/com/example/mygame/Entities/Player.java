package com.example.mygame.Entities;

public class Player extends Sprite {
    
    private float timeOfHit = 0f; 
    private float timePassed = 3f; // seconds

    public Player(String imagePath, int x, int y, int width, int height, int health, int speed) {
        super(imagePath, x, y, width, height, health, speed);
    }

    public FireBall shootFireBall(int xSpeed, int ySpeed) {
        // Create a new FireBall instance
        FireBall fireBall = new FireBall("fireball.png", this.getX() + this.getWidth()/2-10, this.getY() + this.getHeight() / 2 - 10, xSpeed, ySpeed);
        return fireBall;
    }

    public void update(float delta) {
        timeOfHit += delta;
    }
    public void resetHitTimer() {
        timeOfHit = 0f;
    }
    public void heal(int amount) {
        if(timeOfHit >= timePassed){
            this.setHealth(this.getHealth() + amount);
            if(this.getHealth() > 100) {
                this.setHealth(100);
            }
        }
        
    }
    public boolean isAlive() {
        return this.getHealth() > 0;
    }
    
}
