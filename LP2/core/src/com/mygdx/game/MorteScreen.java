package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MorteScreen implements Screen{
	
	private ExGame game;
		
    TextButtonStyle textButtonStyleSair;
    BitmapFont fontSair;
    Skin skinSair;
    TextureAtlas buttonAtlasSair;    
    
    TextButtonStyle textButtonStyleJogar;
    BitmapFont fontJogar;
    Skin skinJogar;
    TextureAtlas buttonAtlasJogar;
    
    static BitmapFont font;
        
    private Texture fundo;
        
    public Stage stage;
    private Viewport port;
    private int score;
    private SpriteBatch balde;
	
    public MorteScreen(final ExGame game){
    	
    	balde = new SpriteBatch();
    	this.game = game;
    	port = new FitViewport(1280, 720, new OrthographicCamera());
    	stage = new Stage(port);
    	fundo = new Texture("coisa/FonteFinal.png");    	
    	FileHandle caminho = new FileHandle("coisa/font.ttf");
		 
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(caminho);
		  FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		  parameter.size = 20 ;		  
		  font = new BitmapFont();					 
		  font = generator.generateFont(parameter);	
		  font.setColor(Color.valueOf("b7b7b7"));		  
		  generator.dispose();
		      	
		//Begin Botão Jogar
		 fontJogar = new BitmapFont();
	     skinJogar = new Skin();
	     buttonAtlasJogar = new TextureAtlas("coisa/RenascerImg.pack");
	     skinJogar.addRegions(buttonAtlasJogar);
	     textButtonStyleJogar = new TextButtonStyle();
	     textButtonStyleJogar.font = fontJogar;
	     textButtonStyleJogar.up = skinJogar.getDrawable("RenascerNormal");
	     textButtonStyleJogar.down = skinJogar.getDrawable("RenascerPressionado");
	     textButtonStyleJogar.checked = skinJogar.getDrawable("RenascerNormal");
	     Button buttonJogar = new TextButton(" ", textButtonStyleJogar);
	     buttonJogar.addListener(new ClickListener() {	    	 
				@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				textButtonStyleJogar.up = skinJogar.getDrawable("RenascerNormal");
				super.exit(event, x, y, pointer, toActor);
			}
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				textButtonStyleJogar.up = skinJogar.getDrawable("RenascerSelecionado");
				return super.mouseMoved(event, x, y);
			}
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				/*
				 * Através desse método quando apertamos o botão,
				 * vamos chamar uma nova tela, que nesse caso é a janela da fila
				 */			
				game.setScreen(new PlayScreen(game));
				MorteScreen.this.pause();
			}		    	
		     });
	     buttonJogar.setPosition(215, 163);
		 stage.addActor(buttonJogar);
		//end Botão Jogar
		 
		//Begin Botão Sair
		 fontSair = new BitmapFont();
	     skinSair = new Skin();
	     buttonAtlasSair = new TextureAtlas("coisa/SairImg.pack");
	     skinSair.addRegions(buttonAtlasSair);
	     textButtonStyleSair = new TextButtonStyle();
	     textButtonStyleSair.font = fontSair;
	     textButtonStyleSair.up = skinSair.getDrawable("SairNormal");
	     textButtonStyleSair.down = skinSair.getDrawable("SairPressionado");
	     textButtonStyleSair.checked = skinSair.getDrawable("SairNormal");
	     Button buttonSair = new TextButton(" ", textButtonStyleSair);
	     buttonSair.addListener(new ClickListener() {	    	 
				@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				textButtonStyleSair.up = skinSair.getDrawable("SairNormal");
				super.exit(event, x, y, pointer, toActor);
			}
			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				textButtonStyleSair.up = skinSair.getDrawable("SairSelecionado");
				return super.mouseMoved(event, x, y);
			}
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}		    	
		     });
	     buttonSair.setPosition(215, 50);
		 stage.addActor(buttonSair);
		//end Botão Sair		
		
	}
	
    public void show() {
    	Gdx.input.setInputProcessor(stage);		
	}

	public void render(float delta) {		
		score = Hud.getScore();
	
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		balde.begin();
		balde.draw(fundo, 0, 0);
		font.draw(balde, "SCORE TOTAL: " + String.valueOf(score), 520, 500); 
		balde.end();		
		
		stage.act(delta);
		stage.draw();		
	}
	
	public void resize(int width, int height) {
				
	}
	
	public void pause() {
				
	}
	
	public void resume() {
				
	}
	
	public void hide() {
				
	}
	
	public void dispose() {
		
	}
}
