package com.example.mygame.Entities;


public class Enemy extends Sprite {
    
    private int damage, range; 
    
    public Enemy(String imagePath, int x, int y, int width, int height, int speed, int range) {
        super(imagePath, x, y, width, height, 100, speed);
        this.damage = 3;
        this.range = range;
    }
    public double getDistanceToPlayer(Player player) {
        double dx = (player.getX() + player.getWidth() / 2) - (this.getX() + this.getWidth() / 2);
        double dy = (player.getY() + player.getHeight() / 2) - (this.getY() + this.getHeight() / 2);
        return Math.sqrt(dx * dx + dy * dy);
    }
    public void takeStepTowardsPlayer(Player player) {
        if(player.isAlive() && getDistanceToPlayer(player) < range) {
            if (player.getX() > this.getX()) {
            this.moveRight();
            } else if (player.getX() < this.getX()) {
                this.moveLeft();
            }

            if (player.getY() > this.getY()) {
                this.moveUp();
            } else if (player.getY() < this.getY()) {
                this.moveDown();
            }
        }
        
    }
    public int getDamage() {
        return damage;
    }
}
