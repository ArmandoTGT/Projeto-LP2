package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class LaserInimigo extends Sprite {

    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;  
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    
    int angulo;

    Body b2body;
    public LaserInimigo(PlayScreen screen, float x, float y, float angulo){
    	super(screen.getAtlasInimigo().findRegion("Tiro Vermelho - Singular"));
    	this.angulo =  (int)angulo;
        
        this.screen = screen;
        this.world = screen.mundo;
        frames = new Array<TextureRegion>();   
        
        
        setBounds(x, y, 6 / ExGame.PPM, 6 /ExGame.PPM);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        if(angulo >= 10 && angulo < 25) {
        bdef.position.set(getX(), getY()+0.1f);
        }else if(angulo >= 25 && angulo < 50){
        	bdef.position.set(getX() -0.1f, getY()+0.1f);
        } else if(angulo >= 50 && angulo < 90) {
        	bdef.position.set(getX() -0.1f, getY());
        } else if(angulo >= -200 && angulo < -180) {
        	bdef.position.set(getX() , getY() -0.1f);
        }else if(angulo >= -245 && angulo < -200) {
        	bdef.position.set(getX()-0.1f , getY() -0.05f);
        }else if(angulo >= -270 && angulo < -245) {
        	bdef.position.set(getX()-0.1f , getY() );
        }
        else {
        bdef.position.set(getX(), getY());
        }
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
         b2body = world.createBody(bdef);

        setOriginCenter();    
        setRotation((PlayScreen.inimigo[1].corpo.getAngle() * MathUtils.radiansToDegrees) - 90);
        
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / ExGame.PPM); 
        
        fdef.filter.categoryBits = ExGame.INIMIGOLASER_BIT;
		fdef.filter.maskBits = ExGame.DEFAULT_BIT | ExGame.PLAYERLASER_BIT | ExGame.PLAYER_BIT | ExGame.INIMIGO_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        
        
       
        if(angulo >= -270 && angulo < -265) {
        	b2body.setLinearVelocity(new Vector2( -3f, -0f));
        	}        
        if(angulo >= -265 && angulo < -255) {
        	b2body.setLinearVelocity(new Vector2( -3f, -0.2f));
        	}
        if(angulo >= -255 && angulo < -250) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, -0.5f));
        	}
        if(angulo >= -250 && angulo < -245) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, -0.8f));
        	}
        if(angulo >= -245 && angulo < -235) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, -1.3f));
        	}  
        if(angulo >= -235 && angulo < -225) {
        	b2body.setLinearVelocity(new Vector2( -2f, -1.7f));
        	}        
        if(angulo >= -225 && angulo < -215) {
        	b2body.setLinearVelocity(new Vector2( -2f, -2.2f));
        	}
        if(angulo >= -215 && angulo < -210) {
        	b2body.setLinearVelocity(new Vector2( -1.5f, -2.5f));
        	}
        if(angulo >= -210 && angulo < -200) {
        	b2body.setLinearVelocity(new Vector2( -1f, -2.5f));
        	}
        if(angulo >= -200 && angulo < -190) {
        	b2body.setLinearVelocity(new Vector2( -0.7f, -2.5f));
        	}
        if(angulo >= -190 && angulo < -180) {
        	b2body.setLinearVelocity(new Vector2( -0.5f, -2.5f));
        	}
        if(angulo >= -180 && angulo < -175) {
        	b2body.setLinearVelocity(new Vector2( 0, -2.5f));
        	} 
        if(angulo >= -175 && angulo < -170) {
        	b2body.setLinearVelocity(new Vector2( 0.2f, -2.5f));
        	} 
        if(angulo >= -170 && angulo < -155) {
        	b2body.setLinearVelocity(new Vector2( 0.5f, -2.5f));
        	} 
        if(angulo >= -155 && angulo < -145) {
        	b2body.setLinearVelocity(new Vector2( 1, -2.5f));
        	} 
        if(angulo >= -155 && angulo < -145) {
        	b2body.setLinearVelocity(new Vector2( 1, -2.5f));
        	} 
        if(angulo >= -145 && angulo < -135) {
        	b2body.setLinearVelocity(new Vector2( 1.5f, -2.5f));
        	} 
        if(angulo >= -135 && angulo < -125) {
        	b2body.setLinearVelocity(new Vector2( 2, -2f));
        	} 
        if(angulo >= -125 && angulo < -115) {
        	b2body.setLinearVelocity(new Vector2( 3, -1.5f));
        	} 
        if(angulo >= -115 && angulo < -105) {
        	b2body.setLinearVelocity(new Vector2( 3, -1f));
        	} 
        if(angulo >= -105 && angulo < -90) {
        	b2body.setLinearVelocity(new Vector2( 3, -0.5f));
        	}   
        if(angulo >= -90 && angulo < -83) {
        	b2body.setLinearVelocity(new Vector2( 3, 0f));
        	}        
        if(angulo >= -83 && angulo < -75) {
        	b2body.setLinearVelocity(new Vector2( 3, 0.3f));
        	}
        if(angulo >= -75 && angulo < -60) {
        	b2body.setLinearVelocity(new Vector2( 3, 1f));
        	}
        if(angulo >= -60 && angulo < -45) {
        	b2body.setLinearVelocity(new Vector2( 3, 2f));
        	}
        if(angulo >= -45 && angulo < -35) {
        	b2body.setLinearVelocity(new Vector2( 2, 2.5f));
        	}
        if(angulo >= -35 && angulo < -20) {
        	b2body.setLinearVelocity(new Vector2( 1, 2.5f));
        	}
        if(angulo >= -20 && angulo < -5) {
        	b2body.setLinearVelocity(new Vector2( 0.5f, 2.5f));
        	}
        if(angulo >= -5 && angulo < 10) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 10 && angulo < 25) {
        	b2body.setLinearVelocity(new Vector2( -1f, 2.5f));
        	}
        if(angulo >= 25 && angulo < 40) {
        	b2body.setLinearVelocity(new Vector2( -2f, 2.5f));
        	}
        if(angulo >= 40 && angulo < 50) {
        	b2body.setLinearVelocity(new Vector2( -2f, 2.5f));
        	}
        if(angulo >= 50 && angulo < 60) {
        	b2body.setLinearVelocity(new Vector2( -2f, 1.5f));
        	}
        if(angulo >= 60 && angulo < 70) {
        	b2body.setLinearVelocity(new Vector2( -3f, 1.5f));
        	}
        if(angulo >= 70 && angulo < 80) {
        	b2body.setLinearVelocity(new Vector2( -3f, 1f));
        	}
        if(angulo >= 80 && angulo < 85) {
        	b2body.setLinearVelocity(new Vector2( -3f, 0.5f));
        	}
        if(angulo >= 85 && angulo <= 90) {
        	b2body.setLinearVelocity(new Vector2( -3f, 0f));
        	}
        			
        
      
    }
    

    public void update(float dt){
        stateTime += dt;
       
       
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        

        
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            
            destroyed = true;
        }
        if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}