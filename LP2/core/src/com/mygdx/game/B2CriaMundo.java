package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class B2CriaMundo {
	public B2CriaMundo(World mundo, TiledMap mapa) {
		BodyDef corpoDef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body corpo;
		
		
		
		for(MapObject obj : mapa.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();
			
			corpoDef.type = BodyDef.BodyType.StaticBody;
			corpoDef.position.set((rect.getX() + rect.getWidth() /2) / ExGame.PPM, (rect.getY() + rect.getHeight() /2) / ExGame.PPM);
			
			corpo = mundo.createBody(corpoDef);
			
			shape.setAsBox(rect.getWidth() /2 / ExGame.PPM, rect.getHeight() /2 / ExGame.PPM);
			fdef.shape = shape;
			corpo.createFixture(fdef);
			
		}
	}
}
