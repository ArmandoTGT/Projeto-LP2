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

public class NaveInimiga extends Sprite {
	public World mundo;
	public Body corpo;
	private TextureRegion stand;
	private OrthographicCamera camera;
	private Vector3 pos;
	private PlayScreen screen;
	
	private Array<LaserInimigo> fireballs;
	
	public NaveInimiga(World mundo, PlayScreen screen, OrthographicCamera camera) {
		super(screen.getAtlasInimigo().findRegion("Crawler"));
		this.mundo = mundo;
		this.camera = camera;
		this.screen = screen;
		pos = new Vector3();
		defineNaveInimiga();
		
		stand = new TextureRegion(getTexture(), 0, 0, 32, 32);
		setBounds(0, 0, 32 / ExGame.PPM, 32 / ExGame.PPM);
		setRegion(stand);
		
		fireballs = new Array<LaserInimigo>();
	}
	
	public void update(float dt) {
		setPosition(corpo.getPosition().x - getWidth() /2 , corpo.getPosition().y - getHeight() /2 );
		
		  setOriginCenter();
		  setRotation((corpo.getAngle() * MathUtils.radiansToDegrees) - 90);
		  
		  for(LaserInimigo  ball : fireballs) {
	            ball.update(dt);
	            if(ball.isDestroyed())
	                fireballs.removeValue(ball, true);
	        }
		  
		  //System.out.println( this.getRotation());
		  
	
		}

	static int posicao = 1; 
	
	public void outrometodo(){
		posicao++;
	}
	
	private void defineNaveInimiga() {
		BodyDef corpoDef = new BodyDef();
		corpoDef.position.set((250 * posicao) / ExGame.PPM, 2000 / ExGame.PPM);
		corpoDef.type = BodyType.DynamicBody;
		corpo = mundo.createBody(corpoDef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(9 / ExGame.PPM);
		
		fdef.filter.categoryBits = ExGame.INIMIGO_BIT;
		fdef.filter.maskBits = ExGame.DEFAULT_BIT | ExGame.METEORO_BIT |
				ExGame.PLAYERLASER_BIT | ExGame.PLAYER_BIT | ExGame.INIMIGOLASER_BIT;
		
		fdef.shape = shape;
		corpo.createFixture(fdef);
	}
	
	public void fire(){
        fireballs.add(new LaserInimigo(screen, corpo.getPosition().x, corpo.getPosition().y,  this.getRotation()));
    }

    public void draw(Batch batch){
        super.draw(batch);
        try {
        for(LaserInimigo ball : fireballs)
            ball.draw(batch);
        
        }
        catch(Exception e){
        	
        }
    }

}
