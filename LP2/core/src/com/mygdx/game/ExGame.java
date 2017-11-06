package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ExGame extends Game {
	public static final float PPM = 100;
	public static final float V_LARG = 640;
	public static final float V_ALT = 360;
	
	public SpriteBatch balde;
	
	
	

	public void create () {
		balde = new SpriteBatch();
		setScreen(new PlayScreen(this));
		}

	public void render () {
		super.render();
	}
	
	
}
