package com.mygdx.game;

import java.util.Vector;

import javax.swing.text.Position;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PlayScreen implements Screen, InputProcessor {
	
	private ExGame game;	
		
	public Vector2 vec;
	
	private OrthographicCamera camera;
	private Viewport port;
		
	private TmxMapLoader carregaMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderMapa;
	
	public World mundo;
	private Box2DDebugRenderer b2Render;
	
	public Nave1 jogador;
	
	private TextureAtlas atlas;

	public int mouseX;
	public int mouseY;
	
	Vector2 d;
	Vector2 a;

	public PlayScreen(ExGame game) {	
		
		
		this.game = game;		
		camera = new OrthographicCamera();
		port = new FitViewport(ExGame.V_LARG / ExGame.PPM, ExGame.V_ALT / ExGame.PPM, camera);	
		
		carregaMapa = new TmxMapLoader();
		mapa = carregaMapa.load("coisa/betaMap.tmx");
		atlas = new TextureAtlas("coisa/Nave1.atlas");
		renderMapa = new OrthogonalTiledMapRenderer(mapa, 1 / ExGame.PPM);
		camera.position.set(port.getWorldWidth() /2, port.getWorldHeight() /2, 0);
		
		mundo = new World(new Vector2(0, 0), true);
		b2Render = new Box2DDebugRenderer();
		
		jogador = new Nave1(mundo, this, camera);
		
		new B2CriaMundo(mundo, mapa);
		
		Gdx.input.setInputProcessor(this);
		
		
	}
	
	
	

	
	
	private void handleInput(float dt) {
		
		
					
		
		if(Gdx.input.isKeyPressed(Input.Keys.W) && jogador.corpo.getLinearVelocity().y <= 2){
			jogador.corpo.applyForce(0, 1f, 0, 0, true);
			
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.S) && jogador.corpo.getLinearVelocity().y >= -2){
			jogador.corpo.applyForce(0, -1f, 0, 0, true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.D) && jogador.corpo.getLinearVelocity().x <= 2){
			jogador.corpo.applyForce(1f, 0, 0, 0, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A) && jogador.corpo.getLinearVelocity().x >= -2){
			jogador.corpo.applyForce(-1f, 0, 0, 0, true);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			camera.zoom += 0.02;
			//if(camera.zoom > 1.8999991)camera.zoom = (float) 1.8999991;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			camera.zoom -= 0.02;			
			//if(camera.zoom < 0.30000037)camera.zoom = (float) 0.30000037;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            jogador.fire();
    	}
		
		
		
		
		
		
		
		
		
		
	}

	public void update(float dt, float screenX, float screenY) {
		handleInput(dt);
		
		mundo.step(1/60f, 6, 2);
		
		camera.position.x = jogador.corpo.getPosition().x;
		camera.position.y = jogador.corpo.getPosition().y;
		
		jogador.update(dt);
		
		camera.update();		
		
		
		renderMapa.setView(camera);
	}
	
	
	public TextureAtlas getAtlas(){
		return atlas;
		
		
	}
	
	public void show() {		
		
	}

	
	public void render(float delta) {
		update(delta, camera.position.x, camera.position.y);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		renderMapa.render();
		
		mundo.setContactListener(new listaColisao());
		
		b2Render.render(mundo, camera.combined);
		
		game.balde.setProjectionMatrix(camera.combined);
		game.balde.begin();
		jogador.draw(game.balde);
		
		game.balde.end();
		
		
		
		
		
		
		
			}

	
	public void resize(int width, int height) {
		port.update(width, height);
	}

	
	public void pause() {
		
	}

	
	public void resume() {
		
	}

	
	public void hide() {
		
	}

	
	public void dispose() {
		mapa.dispose();
		renderMapa.dispose();
		mundo.dispose();
			
	}






	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean mouseMoved(int screenX, int screenY) {
			
			mouseX = screenX;
			mouseY = screenY;
		  Vector3 sp3 = camera.unproject(new Vector3(screenX, screenY, 0));
		  Vector2 sp2 = new Vector2(sp3.x, sp3.y);

		  // Take the vector that goes from body origin to mouse in camera space
		  Vector2 a = jogador.corpo.getPosition();
		  Vector2 d = sp2.sub(a);
		  

		  // Now you can set the angle;
		  jogador.corpo.setTransform(jogador.corpo.getPosition(), d.angleRad());
		  
		  		  
		return true;
	}






	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


	
	





	/*@Override
	public boolean mouseMoved(int screenX, int screenY) {
		int mouseX = screenX;
	    int mouseY = screenY;
	    float rot = MathUtils.radiansToDegrees * MathUtils.atan2((mouseY - jogador.getX()) /ExGame.PPM, (mouseX - jogador.getY()) /ExGame.PPM);
	    System.out.println("oxe");
	    jogador.setRotation(rot);
		return true;
	}*/

}
