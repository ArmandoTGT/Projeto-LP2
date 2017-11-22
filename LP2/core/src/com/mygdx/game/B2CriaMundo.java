package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class B2CriaMundo {
	public B2CriaMundo(PlayScreen screen) {
		World mundo = screen.getWorld();
		TiledMap mapa = screen.getMap();
		for(MapObject obj : mapa.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			
			new Meteoro(screen, rect);
			
		}
	}
	//Box2DDebugRenderer debugRendere = new Box2DDebugRenderer(false,false,false,false,false,false);
	
	
}
