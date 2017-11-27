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


public class LaserPlayer extends Sprite {

    PlayScreen screen;
    World mundo;    
    
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    NavePlayer dono;
    int angulo;

    Body b2body;
    public LaserPlayer(PlayScreen screen, float x, float y, float angulo){
    	super(screen.getAtlas().findRegion("Tiro Azul"));
    	this.angulo =  (int)angulo;
        dono = screen.jogador;
        this.screen = screen;
        this.mundo = screen.mundo;  
        
        
        setBounds(x, y, 6 / ExGame.PPM, 6 /ExGame.PPM);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
       // if(angulo >= 10 && angulo < 25) {
       // bdef.position.set(getX(), getY()+0.1f);
       // }else if(angulo >= 25 && angulo < 50){
       // 	bdef.position.set(getX() -0.1f, getY()+0.1f);
       // } else if(angulo >= 50 && angulo < 90) {
       // 	bdef.position.set(getX() -0.1f, getY());
       // } else if(angulo >= -200 && angulo < -180) {
       // 	bdef.position.set(getX() , getY() -0.1f);
       // }else if(angulo >= -245 && angulo < -200) {
       // 	bdef.position.set(getX()-0.1f , getY() -0.05f);
       // }else if(angulo >= -270 && angulo < -245) {
       // 	bdef.position.set(getX()-0.1f , getY() );
       // }
       // else {
        bdef.position.set(getX(), getY());
       // }
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!mundo.isLocked())
         b2body = mundo.createBody(bdef);

        setOriginCenter();    
        setRotation((PlayScreen.jogador.corpo.getAngle() * MathUtils.radiansToDegrees) - 90);
        
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / ExGame.PPM);      
        
        fdef.filter.categoryBits = ExGame.PLAYERLASER_BIT;
		fdef.filter.maskBits = ExGame.DEFAULT_BIT | ExGame.INIMIGOLASER_BIT | ExGame.INIMIGO_BIT ;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        
        float x = Float.parseFloat( String.valueOf( 3*Math.cos((Math.PI/180)*(angulo+90) ) ) );
    	float y = Float.parseFloat( String.valueOf( 3*Math.sin((Math.PI/180)*(angulo+90) ) ) );
    	b2body.setLinearVelocity(new Vector2( x, y));
        
        /*
        if(angulo >= -270 && angulo < -265) {
        	float x = Float.parseFloat( String.valueOf( 3*Math.cos((Math.PI/180)*180) ) );
        	float y = Float.parseFloat( String.valueOf( 3*Math.sin((Math.PI/180)*180) ) );
        	b2body.setLinearVelocity(new Vector2( x, y));
        	}  
        if(angulo >= -265 && angulo < -255) {
        	float x = Float.parseFloat( String.valueOf( 3*Math.cos((Math.PI/180)*angulo) ) );
        	float y = Float.parseFloat( String.valueOf( 3*Math.sin((Math.PI/180)*angulo ) );
        	b2body.setLinearVelocity(new Vector2( x, y));
        	}
        if(angulo >= -255 && angulo < -250) {
        	float x = Float.parseFloat( String.valueOf( 3*Math.cos((Math.PI/180)*197.5) ) );
        	float y = Float.parseFloat( String.valueOf( 3*Math.sin((Math.PI/180)*197.5) ) );
        	b2body.setLinearVelocity(new Vector2( x, y));
        	}
        if(angulo >= -250 && angulo < -245) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, -0.7f));
        	}
        if(angulo >= -245 && angulo < -240) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, -0.9f));
        	}  
        if(angulo >= -240 && angulo < -235) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, -1.1f));
        	}  
        if(angulo >= -235 && angulo < -230) {
        	b2body.setLinearVelocity(new Vector2( -2f, -1.3f));
        	}  
        if(angulo >= -230 && angulo < -225) {
        	b2body.setLinearVelocity(new Vector2( -2f, -1.5f));
        	} 
        if(angulo >= -225 && angulo < -220) {
        	b2body.setLinearVelocity(new Vector2( -2f, -1.7f));
        	} 
        if(angulo >= -220 && angulo < -215) {
        	b2body.setLinearVelocity(new Vector2( -1.5f, -1.9f));
        	} 
        if(angulo >= -215 && angulo < -210) {
        	b2body.setLinearVelocity(new Vector2( -1.4f, -1.9f));
        	} 
        if(angulo >= -210 && angulo < -205) {
        	b2body.setLinearVelocity(new Vector2( -1.0f, -2.0f));
        	} 
        if(angulo >= -205 && angulo < -200) {
        	b2body.setLinearVelocity(new Vector2( -1.0f, -2.0f));
        	} 
        if(angulo >= -200 && angulo < -195) {
        	b2body.setLinearVelocity(new Vector2( -1.0f, -2.7f));
        	} 
        if(angulo >= -195 && angulo < -190) {
        	b2body.setLinearVelocity(new Vector2( -1.0f, -2.9f));
        	} 
        if(angulo >= -190 && angulo < -185) {
        	b2body.setLinearVelocity(new Vector2( -0.5f, -3.1f));
        	} 
        if(angulo >= -185 && angulo < -180) {
        	b2body.setLinearVelocity(new Vector2( -0.5f, -3.2f));
        	}
        if(angulo >= -180 && angulo < -175) {
        	b2body.setLinearVelocity(new Vector2( 0f, -3.4f));
        	} 
        if(angulo >= -175 && angulo < -170) {
        	b2body.setLinearVelocity(new Vector2( 0f, -3.6f));
        	} 
        if(angulo >= -170 && angulo < -165) {
        	b2body.setLinearVelocity(new Vector2( 0f, -3.8f));
        	} 
        if(angulo >= -165 && angulo < -160) {
        	b2body.setLinearVelocity(new Vector2( 0.5f, -4.0f));
        	} 
        if(angulo >= -160 && angulo < -155) {
        	b2body.setLinearVelocity(new Vector2( 0.5f, -4.2f));
        	} 
        //tá indo abaixo
        if(angulo >= -155 && angulo < -150) {
        	b2body.setLinearVelocity(new Vector2( 1.5f, -3.0f));
        	} 
        if(angulo >= -150 && angulo < -145) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, -2.5f));
        	} 
        if(angulo >= -145 && angulo < -140) {
        	b2body.setLinearVelocity(new Vector2( 1.5f, -2.0f));
        	} 
        if(angulo >= -140 && angulo < -135) {
        	b2body.setLinearVelocity(new Vector2( 1.4f, -2.0f));
        	} 
        if(angulo >= -135 && angulo < -130) {
        	b2body.setLinearVelocity(new Vector2( 1.5f, -2.0f));
        	} 
        if(angulo >= -130 && angulo < -125) {
        	b2body.setLinearVelocity(new Vector2( 1.5f, -2.5f));
        	} 
        if(angulo >= -125 && angulo < -120) {
        	b2body.setLinearVelocity(new Vector2( 1.5f, -2.5f));
        	} 
        if(angulo >= -120 && angulo < -115) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, -1.5f));
        	} 
        if(angulo >= -115 && angulo < -110) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, -1.3f));
        	} 
        if(angulo >= -110 && angulo < -105) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, -1f));
        	} 
        if(angulo >= -105 && angulo < -100) {
        	b2body.setLinearVelocity(new Vector2( 2.5f, -0.5f));
        	}   
        if(angulo >= -100 && angulo < -95) {
        	b2body.setLinearVelocity(new Vector2( 2.5f, -0.5f));
        	}        
        if(angulo >= -95 && angulo < -90) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0f));
        	}
        if(angulo >= -90 && angulo < -85) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0f));
        	}
        if(angulo >= -85 && angulo < -80) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0.2f));
        	}
        if(angulo >= -80 && angulo < -75) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0.3f));
        	}
        if(angulo >= -75 && angulo < -70) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0.4f));
        	}
        if(angulo >= -70 && angulo < -65) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0.5f));
        	}
        if(angulo >= -65 && angulo < -60) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0.6f));
        	}
        if(angulo >= -60 && angulo < -55) {
        	b2body.setLinearVelocity(new Vector2( 2.0f, 0.7f));
        	}
        if(angulo >= -55 && angulo < -50) {
        	b2body.setLinearVelocity(new Vector2( 0.9f, 2.5f));
        	}
        if(angulo >= -50 && angulo < -45) {
        	b2body.setLinearVelocity(new Vector2( 0.5f, 2.5f));
        	}
        if(angulo >= -45 && angulo < -40) {
        	b2body.setLinearVelocity(new Vector2( 0.5f, 3.0f));
        	}
        if(angulo >= -40 && angulo < -35) {
        	b2body.setLinearVelocity(new Vector2( 0.4f, 3.0f));
        	}
        if(angulo >= -35 && angulo < -30) {
        	b2body.setLinearVelocity(new Vector2( 0.4f, 3.3f));
        	}
        if(angulo >= -30 && angulo < -25) {
        	b2body.setLinearVelocity(new Vector2( 0.3f, 3.3f));
        	}
        if(angulo >= -25 && angulo < -20) {
        	b2body.setLinearVelocity(new Vector2( 0.3f, 3.0f));
        	}
        if(angulo >= -20 && angulo < -15) {
        	b2body.setLinearVelocity(new Vector2( 0.3f, 3.0f));
        	}
        if(angulo >= -15 && angulo < -10) {
        	b2body.setLinearVelocity(new Vector2( 0.3f, 3.0f));
        	}
        if(angulo >= -10 && angulo < -5) {
        	b2body.setLinearVelocity(new Vector2( 0.3f, 3.0f));
        	}
        if(angulo >= -5 && angulo < 0) {
        	b2body.setLinearVelocity(new Vector2( 0.2f, 3.5f));
        	}
        if(angulo >= 0 && angulo < 5) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 5 && angulo < 10) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 10 && angulo < 15) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 15 && angulo < 20) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 20 && angulo < 25) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 25 && angulo < 30) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 30 && angulo < 35) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 35 && angulo < 40) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 40 && angulo < 45) {
        	b2body.setLinearVelocity(new Vector2( 0f, 2.5f));
        	}
        if(angulo >= 45 && angulo < 50) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, 2.5f));
        	}
        if(angulo >= 50 && angulo < 55) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, 2.5f));
        	}
        if(angulo >= 55 && angulo < 60) {
        	b2body.setLinearVelocity(new Vector2( -2.5f, 2.5f));
        	}
        if(angulo >= 60 && angulo < 65) {
        	b2body.setLinearVelocity(new Vector2( -5.5f, 1f));
        	}
        if(angulo >= 65 && angulo < 70) {
        	b2body.setLinearVelocity(new Vector2( -5.5f, 1f));
        	}
        if(angulo >= 70 && angulo < 75) {
        	b2body.setLinearVelocity(new Vector2( -6f, 0.7f));
        	}
        if(angulo >= 75 && angulo < 80) {
        	b2body.setLinearVelocity(new Vector2( -6f, 0.7f));
        	}
        if(angulo >= 80 && angulo < 85) {
        	b2body.setLinearVelocity(new Vector2( -6f, 0.5f));
        	}
        if(angulo >= 85 && angulo <= 90) {
        	b2body.setLinearVelocity(new Vector2( -6f, 0f));
        	}
        	*/
       
    }
    

    public void update(float dt){
        stateTime += dt;
       
       
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        

        
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            mundo.destroyBody(b2body);            
            destroyed = true;
        }
        if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);        
    	}
    
    public void some() {
    	mundo.destroyBody(b2body);;
    }
    
    
    public void addScore(int i) {
    	dono.addScore(i);
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}