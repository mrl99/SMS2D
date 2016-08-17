package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Person extends GameObject {
	private Rectangle bottom, left, right, top, step, hitbox;
	private Sprite sprite;
	private Texture texture;
	private float velocityY;
	private int jumpNum;
	private int life;
	private boolean onGround;
	
	public Person() {
		hitbox = new Rectangle(0, 0, 64, 64);
		bottom = new Rectangle(0, 0, 64, 8);
		left = new Rectangle(0, 8, 8, 56);
		right = new Rectangle(56, 8, 8, 56);
		top = new Rectangle(8, 56, 48, 8);
		
		step = new Rectangle(0, 16, 64, 54);
		
		onGround = false;
		
		texture = new Texture(Gdx.files.internal("sprites\\color_wheel.png"));
		sprite = new Sprite(texture, 0, 0, 64, 64);
		this.setPosition(0, 10);
		velocityY = 0;
		jumpNum = 2;
		life = 8;
	}
	
	public void setVelocityY(int y) {
		velocityY = y;
	}
	
	public void setOnGround(boolean b) {
		onGround = b;
	}
	
	public void setLife(int l) {
		life = l;
	}
	
	public boolean getOnGround() {
		return onGround;
	}
	
	public int hits(Rectangle r) {
		if(r == null)
			return 0;
		boolean hboxsHit[] = {false, false, false};
		if(bottom.overlaps(r)) { // return 1
			hboxsHit[0] = true;
			onGround = true;
			velocityY = 0;
		}
		if(left.overlaps(r)){ // return 2
			hboxsHit[1] = true;
		}
		if(right.overlaps(r)){ // return 3
			hboxsHit[2] = true;
		}
		if(top.overlaps(r)){
			return 4;
		}
		if(hboxsHit[1] && hboxsHit[2]) { // player gets crushed
			restartLevel();
		}
		else if(hboxsHit[0] && hboxsHit[1]) { // bot and left overlap
			if(!step.overlaps(r))
				return 1;
			return 2;
		}
		else if(hboxsHit[0] && hboxsHit[2]) { // bot and right overlap
			if(!step.overlaps(r))
				return 1;
			return 3;
		}
		else if(hboxsHit[0]) {
			return 1;
		}else if(hboxsHit[1]) {
			return 2;
		}
		else if(hboxsHit[2]) {
			return 3;
		}
		return 0;
	}
	
	public void action(int type, float x, float y) {
		if (type == 1 || type == 4) {
			velocityY = 0;
			setPosition(bottom.x, y);
		}
		else if(type == 2 || type == 3) {
			velocityY = 0;
			setPosition(x, bottom.y);
		}
		else if(type == 5) {
			velocityY = 0;
			setPosition(x, y);
		}
	}
	
	public void restartLevel() {
		System.out.println("Dead");
		setPosition(0, 20);
		setVelocityY(0);
	}
	
	public void update(float delta){
		if(!onGround) {
			velocityY -= 40 * delta;
			if(velocityY < -12)
				velocityY = -12;
			bottom.y += velocityY;
			left.y += velocityY;
			top.y += velocityY;
			hitbox.y += velocityY;
			step.y += velocityY;
		} else {
			jumpNum = 2;
		}
		if(life == 0 || hitbox.y < -50) {
			restartLevel();
		}
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	public void setPosition(float x, float y) {
		hitbox.x = x;
		hitbox.y = y;
		
		bottom.x = x;
		bottom.y = y;
		
		left.x = x;
		left.y = y + 8;
		
		right.x = x + 56;
		right.y = y + 8;

		top.x = x + 8;
		top.y = y + 56;
		
		step.x = x;
		step.y = y + 16;
		
		sprite.setPosition(x, y);
	}
	
	public void moveLeft(float delta) {
		bottom.x -= (300 * delta);
		top.x -= (300 * delta);
		left.x -= (300 * delta);
		right.x -= (300 * delta);
		step.x -= (300 * delta);
		hitbox.x -= (300 * delta);
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	public void moveRight(float delta) {
		bottom.x += (300 * delta);
		top.x += (300 * delta);
		left.x += (300 * delta);
		right.x += (300 * delta);
		step.x += (300 * delta);
		hitbox.x += (300 * delta);
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	public void jump() {
		if(jumpNum > 0)
			velocityY = 9;
		jumpNum--;
		onGround = false;
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public Rectangle getHitBox() {
		return hitbox;
	}

	@Override
	public int hitAction(int side) {
		// TODO Auto-generated method stub
		return 0;
	}
}
