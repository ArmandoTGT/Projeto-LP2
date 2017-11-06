package com.mygdx.game;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Nave1 extends Sprite {
	public World mundo;
	public Body corpo;
	private TextureRegion stand;
	private OrthographicCamera camera;
	private Vector3 pos;
	private PlayScreen screen;
	
	private Array<FireBall> fireballs;
	
	public Nave1(World mundo, PlayScreen screen, OrthographicCamera camera) {
		super(screen.getAtlas().findRegion("Nave1"));
		this.mundo = mundo;
		this.camera = camera;
		this.screen = screen;
		pos = new Vector3();
		defineNave1();
		
		stand = new TextureRegion(getTexture(), 0, 10, 32, 32);
		setBounds(0, 10, 32 / ExGame.PPM, 32 / ExGame.PPM);
		setRegion(stand);
		
		fireballs = new Array<FireBall>();
	}
	
	public void update(float dt) {
		setPosition(corpo.getPosition().x - getWidth() /2 , corpo.getPosition().y - getHeight() /2 );
		
		  setOriginCenter();
		  setRotation((corpo.getAngle() * MathUtils.radiansToDegrees) - 90);
		  
		  for(FireBall  ball : fireballs) {
	            ball.update(dt);
	            if(ball.isDestroyed())
	                fireballs.removeValue(ball, true);
	        }
		  
		  //System.out.println( this.getRotation());
		  
	
		}

	private void defineNave1() {
		BodyDef corpoDef = new BodyDef();
		corpoDef.position.set(32 / ExGame.PPM, 32 / ExGame.PPM);
		corpoDef.type = BodyType.DynamicBody;
		corpo = mundo.createBody(corpoDef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(9 / ExGame.PPM);
		
		fdef.shape = shape;
		corpo.createFixture(fdef);
		
	}
	
	public void fire(){
        fireballs.add(new FireBall(screen, corpo.getPosition().x, corpo.getPosition().y,  this.getRotation()));
    }

    public void draw(Batch batch){
        super.draw(batch);
        try {
        for(FireBall ball : fireballs)
            ball.draw(batch);
        
        }
        catch(Exception e){
        	
        }
    }

}
