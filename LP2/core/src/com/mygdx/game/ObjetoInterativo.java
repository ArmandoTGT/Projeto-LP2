package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class ObjetoInterativo {
	protected World mundo;
	protected TiledMap mapa;
	protected TiledMapTile titulo;
	protected Rectangle bounds;
	protected Body corpo;
	protected Fixture fixture;
	
	public ObjetoInterativo(World mundo, TiledMap mapa, Rectangle bounds) {
		this.mundo = mundo;
		this.mapa = mapa;
		this.bounds = bounds;
		
		BodyDef corpoDef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		
		corpoDef.type = BodyDef.BodyType.StaticBody;
		corpoDef.position.set((bounds.getX() + bounds.getWidth() /2) / ExGame.PPM, (bounds.getY() + bounds.getHeight() /2) / ExGame.PPM);
		
		corpo = mundo.createBody(corpoDef);
		
		shape.setAsBox(bounds.getWidth() /2 / ExGame.PPM, bounds.getHeight() /2 / ExGame.PPM);
		fdef.shape = shape;
		fixture = corpo.createFixture(fdef);
	}
	
	public abstract void batida();
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
	}
	
	public TiledMapTileLayer.Cell getCell(){
		 TiledMapTileLayer layer = (TiledMapTileLayer) mapa.getLayers().get(1);
	        return layer.getCell((int)(corpo.getPosition().x * ExGame.PPM / 16),
	                (int)(corpo.getPosition().y * ExGame.PPM / 16));
	}
	
}
