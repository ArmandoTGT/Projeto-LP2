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
	
	public static final short DEFAULT_BIT = 1;
	public static final short METEORO_BIT = 2;
	public static final short PLAYER_BIT = 4;
	public static final short INIMIGO_BIT = 8;
	public static final short PLAYERLASER_BIT = 16;
	public static final short INIMIGOLASER_BIT = 32;
	public static final short DESTROYED_BIT = 64;
	
	
	public SpriteBatch balde;
	
	
	

	public void create () {
		balde = new SpriteBatch();
		setScreen(new PlayScreen(this));
		}

	public void render () {
		super.render();
	}
	
	
}
