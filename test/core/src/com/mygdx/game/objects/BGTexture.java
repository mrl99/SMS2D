package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BGTexture extends GameObject {

	Sprite sprite;
	Texture texture;
	
	public BGTexture(int x, int y, int width, int height, String texturePath) {
		texture = new Texture(texturePath);
		sprite = new Sprite(texture, 0, 0, width, height);
		setPosition(x, y);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hitAction(int side) {
		// TODO Auto-generated method stub
		return 0;
	}

}
