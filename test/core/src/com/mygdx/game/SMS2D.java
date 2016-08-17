package com.mygdx.game;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.HorizMoveBlock;
import com.mygdx.game.objects.VertMoveBlock;
import com.mygdx.game.objects.Person;
import com.mygdx.game.objects.BGTexture;
import com.mygdx.game.objects.Block;
import com.mygdx.game.objects.Spike;

public class SMS2D extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Sprite sprite;
	private OrthographicCamera camera;
	private Person player1;
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private Rectangle leftButton, rightButton, jumpButton;
	private Sprite spriteLeftButton, spriteRightButton, spriteJumpButton;
	private Texture buttonTexture;
	
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		player1 = new Person();
		player1.setPosition(10, 10);
		
		buttonTexture = new Texture(Gdx.files.internal("sprites/arrows.png"));
		spriteLeftButton = new Sprite(buttonTexture, 0, 0, 64, 64);
		spriteRightButton = new Sprite(buttonTexture, 64, 0, 64, 64);
		spriteJumpButton = new Sprite(buttonTexture, 64, 64, 64, 64);
		leftButton = new Rectangle(20, 20, 64, 64);
		rightButton = new Rectangle(120, 20, 64, 64);
		jumpButton = new Rectangle(716, 20, 64, 64);
		
		
		spriteLeftButton.setPosition(20, 20);
		spriteRightButton.setPosition(120, 20);
		spriteJumpButton.setPosition(716, 20);
		
		// adding static blocks
		FileHandle file = Gdx.files.internal("level1");
		StringTokenizer tokens = new StringTokenizer(file.readString());
		while(tokens.hasMoreTokens()) {
			String type = tokens.nextToken();
			if(type.equals("Block")) {
				gameObjects.add(new Block(
								Integer.parseInt(tokens.nextToken()), 
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								tokens.nextToken()));
			}
			else if(type.equals("Spike")) {
				gameObjects.add(new Spike(
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken())));
			}
			else if(type.equals("VertMoveBlock")){
				gameObjects.add(new VertMoveBlock(
								Integer.parseInt(tokens.nextToken()), 
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								tokens.nextToken()));
			}
			else if(type.equals("HorizMoveBlock")){
				gameObjects.add(new HorizMoveBlock(
								Integer.parseInt(tokens.nextToken()), 
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								tokens.nextToken()));
			}
			else if(type.equals("BGTexture")){
				gameObjects.add(new BGTexture(
								Integer.parseInt(tokens.nextToken()), 
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								Integer.parseInt(tokens.nextToken()),
								tokens.nextToken()));
			}
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1); // background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player1.draw(batch);
		for(GameObject g : gameObjects) {
			g.draw(batch);
		}
		
		spriteLeftButton.draw(batch);
		spriteRightButton.draw(batch);
		spriteJumpButton.draw(batch);
		
		batch.end();
		
		// Updates
		
		updateCamera();
		
		
		player1.update(Gdx.graphics.getDeltaTime());
		boolean landed = false;
		for(GameObject g : gameObjects) {
			g.update(Gdx.graphics.getDeltaTime());
			switch(player1.hits(g.getHitBox())){
			case 1:
				landed = true;
				switch(g.hitAction(1)) {
				case 2:		// player hits spike
					player1.restartLevel();
					break;
				default:
					if(g instanceof HorizMoveBlock && ((HorizMoveBlock) g).getMovingLeft())
						player1.action(5, player1.getHitBox().x - ((HorizMoveBlock) g).getHorizSpeed(), g.getHitBox().y + g.getHitBox().height);
					else if(g instanceof HorizMoveBlock && ((HorizMoveBlock) g).getMovingRight())
						player1.action(5, player1.getHitBox().x + ((HorizMoveBlock) g).getHorizSpeed(), g.getHitBox().y + g.getHitBox().height);
					else
						player1.action(1, 0, g.getHitBox().y + g.getHitBox().height);
					break;
				}
				player1.update(Gdx.graphics.getDeltaTime());
				break;
			case 2:		// player hits left
				player1.action(2, g.getHitBox().x + g.getHitBox().width + 1, 0);
				break;
			case 3: 		// player hits right
				player1.action(3, g.getHitBox().x - player1.getHitBox().width - 1, 0);
				break;
			case 4: 		// player hits top
				player1.action(4, 0, g.getHitBox().y - player1.getHitBox().height - 4);
				break;
			}
		}
		if(!landed) {
			player1.setOnGround(false);
		}
		
		// Controls
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player1.moveLeft(Gdx.graphics.getDeltaTime());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player1.moveRight(Gdx.graphics.getDeltaTime());
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			player1.jump();
		}
		
		for(int i = 0; i < 5; i++) {
			if(Gdx.input.isTouched(i)){
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touchPos);
				Rectangle touch = new Rectangle(touchPos.x - 16, touchPos.y - 16, 32, 32);
				
				if(touch.overlaps(leftButton))
					player1.moveLeft(Gdx.graphics.getDeltaTime());
				if(touch.overlaps(rightButton))
					player1.moveRight(Gdx.graphics.getDeltaTime());
				if(touch.overlaps(jumpButton))
					player1.jump();
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public void updateCamera() {
		leftButton.x = player1.getHitBox().x - 380;
		leftButton.y = player1.getHitBox().y - 10;
		spriteLeftButton.setPosition(leftButton.x, leftButton.y);
		rightButton.x = player1.getHitBox().x - 280;
		rightButton.y = player1.getHitBox().y - 10;
		spriteRightButton.setPosition(rightButton.x, rightButton.y);
		jumpButton.x = player1.getHitBox().x + 312;
		jumpButton.y = player1.getHitBox().y - 10;
		spriteJumpButton.setPosition(jumpButton.x, jumpButton.y);
		camera.position.x = player1.getHitBox().x;
		camera.position.y = player1.getHitBox().y + 160;
		camera.update();
	}
}
