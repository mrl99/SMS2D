package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Spike extends GameObject{
	Rectangle hitbox;
	Sprite sprite;
	Texture texture;
	
	public Spike(int x, int y) {
		hitbox = new Rectangle(x, y, 32, 32);
		texture = new Texture(Gdx.files.internal("sprites/spike.png"));
		sprite = new Sprite(texture, 0, 0, 32, 32);
		
		setPosition(x, y);
	}
	
	public int hits(Rectangle r) {
		return -1;
	}
	@Override
	public void action(int type, float x, float y) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPosition(float x, float y) {
		this.hitbox.x = x;
		this.hitbox.y = y;
		sprite.setPosition(x, y);
	}
	@Override
	public void moveLeft(float delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveRight(float delta) {
		// TODO Auto-generated method stub
		
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
		if(side == 1)
			return 2;
		return 1;
	}
}
