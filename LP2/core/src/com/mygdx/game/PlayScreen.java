package com.mygdx.game;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.text.Position;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
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
	private Hud hud;
	
	public static World mundo;
	private Box2DDebugRenderer b2Render;
	
	static public NavePlayer jogador;
	static public NaveInimiga[] inimigo;
	
	private TextureAtlas atlas;
	private TextureAtlas atlasInimigo;
	private TextureAtlas vaziu;
	static float x[];

	static float y[];
	static int pos[];
	static float ang[];
	static int inimigos;
	
	public int mouseX;
	public int mouseY;
	public static String mandouTiro;
	public static String recebeuTiro[];
	
	Vector2 d;
	Vector2 a;

	JogadorCliente jogadorCCorredor = new JogadorCliente();
	
	
	ExecutorService piscina = Executors.newFixedThreadPool(3);
	boolean Correr = true;
	
	public PlayScreen(ExGame game) {	
		x = new float[19];
		pos = new int[19];
		y = new float[19];
		ang = new float[19];
		
		hud = new Hud(game.balde);
		this.game = game;		
		camera = new OrthographicCamera();
		port = new FitViewport(ExGame.V_LARG / ExGame.PPM, ExGame.V_ALT / ExGame.PPM, camera);	
		mandouTiro = "nãoatirou";
		recebeuTiro = new String[19];
		for(int i = 0; i < 19; i++) {
		recebeuTiro[i] = "nãoatirou";
		}
		carregaMapa = new TmxMapLoader();
		mapa = carregaMapa.load("coisa/Mapa.tmx");
		atlas = new TextureAtlas("coisa/NaveR.pack");
		atlasInimigo = new TextureAtlas("coisa/NaveC.pack");
		vaziu = new TextureAtlas("coisa/void.pack");
		renderMapa = new OrthogonalTiledMapRenderer(mapa, 1 / ExGame.PPM);
		camera.position.set(port.getWorldWidth() /2, port.getWorldHeight() /2, 0);
		
		mundo = new World(new Vector2(0, 0), true);
		b2Render = new Box2DDebugRenderer(false,false,false,false,false,false); //Criar o mundo e parar de desenhas as linhas do box2D
		inimigo = new NaveInimiga[20];
		for(int i = 0; i < 20; i++){
		inimigo[i] = new NaveInimiga(this, i);
		inimigo[i].outrometodo();
		}
		jogador = new NavePlayer(this);
		
		
		
		new B2CriaMundo(this);
		
		//Pixmap pm = new Pixmap(Gdx.files.internal("coisa/cursor.png"));
		
		//Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth(), pm.getHeight()));
		Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);		
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
				
		
	}
	

	public void update(float dt, float screenX, float screenY) {
		handleInput(dt);
		
		mundo.step(1/60f, 6, 2);
		
		camera.position.x = jogador.corpo.getPosition().x;
		camera.position.y = jogador.corpo.getPosition().y;
		
		jogador.update(dt);
		
		for(int i = 0; i < 20; i++) {
			try{
		inimigo[i].update(dt);
			}catch(Exception f) {
				
			}
		}
		
		camera.update();		
		
		
		renderMapa.setView(camera);
	}
	
	
	public TextureAtlas getAtlasInimigo(){
		return atlasInimigo;
		
		
	}
	
	public TextureAtlas getAtlas(){
		return atlas;
		
		
	}
	
	public TextureAtlas getVoid(){
		return vaziu;
		
		
	}
	
	public void show() {		
		
	}
	public void morte(int morto) {
		inimigo[morto] = null;
		System.out.println(morto);
	}

	
	public void render(float delta) {
		update(delta, camera.position.x, camera.position.y);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		renderMapa.render();
		if(Correr){
			Correr = false;
			
			piscina.submit(jogadorCCorredor);
		}
		mundo.setContactListener(new listaColisao());
		
		b2Render.render(mundo, camera.combined);
		
		game.balde.setProjectionMatrix(camera.combined);
		game.balde.begin();
		jogador.draw(game.balde);
		
		for(int i = 0; i < 20; i++) {
			try{
		inimigo[i].draw(game.balde);
			}catch(Exception e){
				
			}
		}
		
		if(inimigo[0] != null)transforma0(pos[0], x[0], y[0], ang[0], recebeuTiro[0]);
		//System.out.println("inimigo 3: " + inimigo[3].corpo.getPosition().x + " " + inimigo[3].corpo.getPosition().y + " " );
		if(inimigo[1] != null)transforma1(pos[1], x[1], y[1], ang[1], recebeuTiro[1]);
		//System.out.println("inimigo 4: " + inimigo[4].corpo.getPosition().x + " " + inimigo[4].corpo.getPosition().y + " " );
		if(inimigo[2] != null)transforma2(pos[2], x[2], y[2], ang[2], recebeuTiro[2]);
		if(inimigo[3] != null)transforma3(pos[3], x[3], y[3], ang[4], recebeuTiro[3]);
		if(inimigo[4] != null)transforma4(pos[4], x[4], y[4], ang[4], recebeuTiro[4]);
		if(inimigo[5] != null)transforma5(pos[5], x[5], y[5], ang[5], recebeuTiro[5]);
		if(inimigo[6] != null)transforma6(pos[6], x[6], y[6], ang[6], recebeuTiro[6]);
		if(inimigo[7] != null)transforma7(pos[7], x[7], y[7], ang[7], recebeuTiro[7]);
		if(inimigo[8] != null)transforma8(pos[8], x[8], y[8], ang[8], recebeuTiro[8]);
		if(inimigo[9] != null)transforma9(pos[9], x[9], y[9], ang[9], recebeuTiro[9]);
		//System.out.println("inimigo 2: " + inimigo[2].corpo.getPosition().x + " " + inimigo[2].corpo.getPosition().y + " " );
		
		game.balde.end();
		game.balde.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
		
        
			}
	
	public static void setInimigo0(int i, float posx, float posy, float angulo, String tiro){
		x[0] = posx;
		y[0] = posy;	
		ang[0] = angulo;
		pos[0] = i;
		recebeuTiro[0] = tiro;
		}
	
	public static void setInimigo1(int i, float posx, float posy, float angulo, String tiro){
		x[1] = posx;
		y[1] = posy;	
		ang[1] = angulo;
		pos[1] = i;
		recebeuTiro[1] = tiro;
	}
	
	public static void setInimigo2(int i, float posx, float posy, float angulo, String tiro){
		x[2] = posx;
		y[2] = posy;	
		ang[2] = angulo;
		pos[2] = i;
		recebeuTiro[2] = tiro;
}
	public static void setInimigo3(int i, float posx, float posy, float angulo, String tiro){
		x[3] = posx;
		y[3] = posy;	
		ang[3] = angulo;
		pos[3] = i;
		recebeuTiro[3] = tiro;
}
	public static void setInimigo4(int i, float posx, float posy, float angulo, String tiro){
		x[4] = posx;
		y[4] = posy;	
		ang[4] = angulo;
		pos[4] = i;
		recebeuTiro[4] = tiro;
}
	public static void setInimigo5(int i, float posx, float posy, float angulo, String tiro){
		x[5] = posx;
		y[5] = posy;	
		ang[5] = angulo;
		pos[5] = i;
		recebeuTiro[5] = tiro;
}
	public static void setInimigo6(int i, float posx, float posy, float angulo, String tiro){
		x[6] = posx;
		y[6] = posy;	
		ang[6] = angulo;
		pos[6] = i;
		recebeuTiro[6] = tiro;
}
	public static void setInimigo7(int i, float posx, float posy, float angulo, String tiro){
		x[7] = posx;
		y[7] = posy;	
		ang[7] = angulo;
		pos[7] = i;
		recebeuTiro[7] = tiro;
}
	public static void setInimigo8(int i, float posx, float posy, float angulo, String tiro){
		x[8] = posx;
		y[8] = posy;	
		ang[8] = angulo;
		pos[8] = i;
		recebeuTiro[8] = tiro;
}
	public static void setInimigo9(int i, float posx, float posy, float angulo, String tiro){
		x[9] = posx;
		y[9] = posy;	
		ang[9] = angulo;
		pos[9] = i;
		recebeuTiro[9] = tiro;
}
		
	
	public void transforma0(int i, float x, float y, float angulo, String tiro){
		inimigo[0].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[0].fire();
		}
	}
	
	public void transforma1(int i, float x, float y, float angulo, String tiro){
		inimigo[1].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[1].fire();
		}
				
	}
	public void transforma2(int i, float x, float y, float angulo, String tiro){
		inimigo[2].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[2].fire();
		}
		
	}
	public void transforma3(int i, float x, float y, float angulo, String tiro){
		inimigo[3].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[3].fire();
		}
		
	}
	public void transforma4(int i, float x, float y, float angulo, String tiro){
		inimigo[4].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[4].fire();
		}
		
	}
	public void transforma5(int i, float x, float y, float angulo, String tiro){
		inimigo[5].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[5].fire();
		}
		
	}
	public void transforma6(int i, float x, float y, float angulo, String tiro){
		inimigo[6].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[6].fire();
		}
		
	}
	public void transforma7(int i, float x, float y, float angulo, String tiro){
		inimigo[7].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[7].fire();
		}
		
	}
	public void transforma8(int i, float x, float y, float angulo, String tiro){
		inimigo[8].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[8].fire();
		}
		
	}
	public void transforma9(int i, float x, float y, float angulo, String tiro){
		inimigo[9].corpo.setTransform(new Vector2(x,y), angulo);
		if(tiro.equals("atirou")) {
			inimigo[9].fire();
		}
		
	}
	
	public void resize(int width, int height) {
		port.update(width, height);
	}

	public TiledMap getMap() {
		return mapa;
		}
	public World getWorld() {
		return mundo;
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
		hud.dispose();
			
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
		jogador.fire();
		mandouTiro = "atirou";
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mandouTiro = "nãoatirou";
						
		return true;
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
		
		return false;
	}


	
	
	

}
