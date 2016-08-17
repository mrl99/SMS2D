package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class HorizMoveBlock extends Block {

	private int horizSpeed;
	private int currSpeed;
	private int cycleTime;
	private int cycleMax;
	private int moveLength;
	private boolean movingLeft;
	private boolean movingRight;
	
	public HorizMoveBlock(int x, int y, int width, int height, int moveSpeed, int cycleMax, int moveLength, String texturePath) {
		super(x, y, width, height, texturePath);
		horizSpeed = moveSpeed;
		this.cycleMax = cycleMax;
		this.moveLength = moveLength;
		currSpeed = 0;
		cycleTime = 0;
	}
	
	public boolean getMovingLeft() {
		return movingLeft;
	}
	
	public boolean getMovingRight() {
		return movingRight;
	}
	
	public int getHorizSpeed() {
		return horizSpeed;
	}

	@Override
	public void update(float delta) {
		cycleTime++;
		int max = cycleMax / Math.abs(horizSpeed);
		if(cycleTime == max)
			cycleTime = 0;
		if((cycleTime / 40) % (max / 40) < (int)(max * 0.0025) * moveLength) {
			movingLeft = true;
			hitbox.x -= horizSpeed;
			sprite.setPosition(hitbox.x, hitbox.y);
		} else if((cycleTime / 40) % (max / 40) >= (int)(max * 0.0125) && (cycleTime / 40) % (max / 40) < (int)(max * 0.0125) + (int)(max * 0.0025) * moveLength) {
			movingRight = true;
			hitbox.x += horizSpeed;
			sprite.setPosition(hitbox.x, hitbox.y);
		} else {
			movingLeft = false;
			movingRight = false;
		}
		
	}

	@Override
	public void setPosition(float x, float y) {
		hitbox.x = x;
		hitbox.y = y;
		sprite.setPosition(x, y);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public Rectangle getHitBox() {
		return hitbox;
	}

	@Override
	public int hitAction(int side) {
		return 0;
	}

}
