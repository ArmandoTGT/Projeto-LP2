package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Meteoro extends ObjetoInterativo{
	public Meteoro(World mundo, TiledMap mapa, Rectangle bounds) {
		super(mundo, mapa, bounds);
		fixture.setUserData(this);
		setCategoryFilter(ExGame.METEORO_BIT);
	}

	@Override
	public void batida() {
		Gdx.app.log("Bateu ", "");	
		//setCategoryFilter(ExGame.DESTROYED_BIT);
		//getCell().setTile(null);
	}

	
	
}
