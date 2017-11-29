package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
	
	public static MorteScreen death;
	public MenuScreen menu;
	
	public void create () {
		menu = new MenuScreen(this);
		death = new MorteScreen(this);
		balde = new SpriteBatch();
		
		
		
		setScreen(menu);
		}

	public void render () {
		super.render();
	}
	
	public void dispose() {
		JogadorCliente.correndo = false;
	}
	
}
