package com.mygdx.game;

import java.util.Random;
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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class NavePlayer extends Sprite {
	public int hp;
	public World mundo;
	public Body corpo;
	private TextureRegion stand;
	private OrthographicCamera camera;
	private Vector3 pos;
	private PlayScreen screen;
	protected Fixture fixture;
	private Array<LaserPlayer> fireballs;
	private boolean setToDestroy;
	private boolean destroyed;
	private float stateTime;	
	private Random rand;
	
	
	public NavePlayer(PlayScreen screen) {
		super(screen.getAtlas().findRegion("RapierAzul"));
		this.mundo = screen.getWorld();
		
		this.screen = screen;
		pos = new Vector3();
		rand = new Random();
		defineNavePlayer();
		
		hp = 100;
		stand = new TextureRegion(getTexture(), 0, 0, 32, 32);
		setBounds(0, 0, 32 / ExGame.PPM, 32 / ExGame.PPM);
		setRegion(stand);
		
		setToDestroy = false;
        destroyed = false;
		
		fireballs = new Array<LaserPlayer>();
		
	}
	
public void update(float dt) {
		
		
		
		// System.out.println((corpo.getAngle() * MathUtils.radiansToDegrees) - 90);
		stateTime += dt;
		if(setToDestroy && !destroyed){			
          mundo.destroyBody(corpo);
         
         System.out.println("tamanho :" + fireballs.size);
         for(int i = 0; i < fireballs.size; i ++) {
        	 fireballs.get(i).some();
         }
          
          fireballs = null;
           // setRegion(new TextureRegion(screen.getVoid().findRegion("void"), 0, 0, 32, 32));
          JogadorCliente.correndo = false;          
          screen.mortePlayer();
           
        		
            destroyed = true;            
           // stateTime = 0;
        } else if(!destroyed) {
		setPosition(corpo.getPosition().x - getWidth() /2 , corpo.getPosition().y - getHeight() /2 );
		
		  setOriginCenter();
		  setRotation((corpo.getAngle() * MathUtils.radiansToDegrees) - 90);
		  
		  for(LaserPlayer  ball : fireballs) {
	            ball.update(dt);
	            if(ball.isDestroyed())
	                fireballs.removeValue(ball, true);
	        }
        }
		  
		  //System.out.println( this.getRotation());
		  
	
		}

	private void defineNavePlayer() {
		BodyDef corpoDef = new BodyDef();
		
		switch(rand.nextInt(9)) {
		
		case 0:
			corpoDef.position.set(1000 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 1:
			corpoDef.position.set(1100 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 2:
			corpoDef.position.set(1200 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 3:
			corpoDef.position.set(1300 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 4:
			corpoDef.position.set(1400 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 5:
			corpoDef.position.set(1500 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 6:
			corpoDef.position.set(1600 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 7:
			corpoDef.position.set(1700 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 8:
			corpoDef.position.set(1800 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		case 9:
			corpoDef.position.set(1900 / ExGame.PPM, 1000 / ExGame.PPM);
			break;
		
		}
		corpoDef.type = BodyType.DynamicBody;
		corpo = mundo.createBody(corpoDef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(9 / ExGame.PPM);
		fdef.filter.categoryBits = ExGame.PLAYER_BIT;
		fdef.filter.maskBits = ExGame.DEFAULT_BIT | ExGame.METEORO_BIT |
				ExGame.INIMIGOLASER_BIT | ExGame.INIMIGO_BIT ;
		
		fdef.shape = shape;
		//fdef.isSensor = true;
		corpo.createFixture(fdef).setUserData(this);	
	}
	
	public void fire(){
        fireballs.add(new LaserPlayer(screen, corpo.getPosition().x, corpo.getPosition().y,  this.getRotation()));
        try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void levaDano(int dano) {
		hp -= dano;
		Hud.levaDano(dano);
		if(hp <= 0)
			setToDestroy = true;
	}
	
    public void draw(Batch batch){
        super.draw(batch);
        try {
        for(LaserPlayer ball : fireballs)
            ball.draw(batch);
        }
        
        catch(Exception e){
        	
        }
    }

	public void addScore(int i){
		Hud.addScore(i);		
	}

}
