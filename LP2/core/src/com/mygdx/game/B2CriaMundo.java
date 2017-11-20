package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.physics.box2d.World;

public class B2CriaMundo {
	public B2CriaMundo(World mundo, TiledMap mapa) {
			
		
		for(MapObject obj : mapa.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			
			new Meteoro(mundo, mapa, rect);
			
		}
	}
	
	
}
