package com.mygdx.game;

import java.util.concurrent.Executor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class listaColisao implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		switch(cDef){
			case ExGame.PLAYER_BIT | ExGame.METEORO_BIT:
				if(fixA.getFilterData().categoryBits == ExGame.PLAYER_BIT) {
                    ((ObjetoInterativo) fixB.getUserData()).batida();//((NavePlayer) fixA.getUserData());
                    ((NavePlayer) fixA.getUserData()).levaDano(5);
                    
				}
                else {
                    ((ObjetoInterativo) fixA.getUserData()).batida();//((NavePlayer) fixB.getUserData());
                    ((NavePlayer) fixB.getUserData()).levaDano(5);
                }
                break;        
    		case ExGame.INIMIGO_BIT | ExGame.METEORO_BIT:
    			if(fixA.getFilterData().categoryBits == ExGame.PLAYER_BIT) {
                     ((ObjetoInterativo) fixB.getUserData()).batida();//((NavePlayer) fixA.getUserData());
                     ((NaveInimiga) fixA.getUserData()).levaDano(5);
                        
    			}
                 else {
                     ((ObjetoInterativo) fixA.getUserData()).batida();//((NavePlayer) fixB.getUserData());
                     ((NaveInimiga) fixB.getUserData()).levaDano(5);
                }
                    break;
			case ExGame.PLAYERLASER_BIT | ExGame.INIMIGO_BIT:
				if(fixA.getFilterData().categoryBits == ExGame.PLAYERLASER_BIT) {
					((NaveInimiga) fixB.getUserData()).levaDano(50);//((NavePlayer) fixA.getUserData());
					((LaserPlayer) fixA.getUserData()).addScore(100);
					 ((LaserPlayer) fixA.getUserData()).setToDestroy();
				}
                else {
                    ((NaveInimiga) fixA.getUserData()).levaDano(50);//((NavePlayer) fixB.getUserData());
                    ((LaserPlayer) fixB.getUserData()).addScore(100);
                    ((LaserPlayer) fixB.getUserData()).setToDestroy();
                }
                break;
                
			case ExGame.PLAYER_BIT | ExGame.INIMIGO_BIT:
				//System.out.println("BATEU MORAL");
				if(fixA.getFilterData().categoryBits == ExGame.PLAYER_BIT) {
					((NavePlayer) fixA.getUserData()).levaDano(5);
                    ((NaveInimiga) fixB.getUserData()).levaDano(5);//((NavePlayer) fixA.getUserData());
				}
                else {
                	((NavePlayer) fixB.getUserData()).levaDano(5);
                    ((NaveInimiga) fixA.getUserData()).levaDano(5);//((NavePlayer) fixB.getUserData());
                }
                break;
			case ExGame.INIMIGO_BIT | ExGame.INIMIGO_BIT:				
				
					
                    ((NaveInimiga) fixB.getUserData()).levaDano(5);//((NavePlayer) fixA.getUserData());                
                    ((NaveInimiga) fixA.getUserData()).levaDano(5);//((NavePlayer) fixB.getUserData());
				
                break;
			case ExGame.INIMIGOLASER_BIT | ExGame.INIMIGOLASER_BIT:				
				
					
                    ((LaserInimigo) fixB.getUserData()).setToDestroy();//((NavePlayer) fixA.getUserData());                
                    ((LaserInimigo) fixA.getUserData()).setToDestroy();//((NavePlayer) fixB.getUserData());
				
                break;
			case ExGame.INIMIGO_BIT | ExGame.INIMIGOLASER_BIT:
				if(fixA.getFilterData().categoryBits == ExGame.INIMIGO_BIT){	
					if(((LaserInimigo) fixB.getUserData()).count == 1){						
						((NaveInimiga) fixA.getUserData()).levaDano(50);//((NavePlayer) fixA.getUserData());
						((LaserInimigo) fixB.getUserData()).setToDestroy();
					}   
						((LaserInimigo) fixB.getUserData()).count++;
				}
             	else{                	// checar o retorno                   
                    if(((LaserInimigo) fixA.getUserData()).count == 1){                    	
                    	((NaveInimiga) fixB.getUserData()).levaDano(50);
                    	((LaserInimigo) fixA.getUserData()).setToDestroy();                 
                    	}
                    	((LaserInimigo) fixB.getUserData()).count++;
                }
                break;
			case ExGame.PLAYER_BIT | ExGame.INIMIGOLASER_BIT:				
				if(fixA.getFilterData().categoryBits == ExGame.INIMIGOLASER_BIT) {
                    ((NavePlayer) fixB.getUserData()).levaDano(50);//((NavePlayer) fixA.getUserData());
					((LaserInimigo) fixA.getUserData()).setToDestroy();
				}
				
                else
                    ((NavePlayer) fixA.getUserData()).levaDano(50);//((NavePlayer) fixB.getUserData());
					((LaserInimigo) fixB.getUserData()).setToDestroy();
                break;
			case ExGame.PLAYERLASER_BIT | ExGame.INIMIGOLASER_BIT:				
				if(fixA.getFilterData().categoryBits == ExGame.PLAYERLASER_BIT) {
                    ((LaserPlayer) fixA.getUserData()).setToDestroy();//((NavePlayer) fixA.getUserData());
					((LaserInimigo) fixB.getUserData()).setToDestroy();
				}
                else {
                    ((LaserPlayer) fixB.getUserData()).setToDestroy();//((NavePlayer) fixB.getUserData());
                    ((LaserInimigo) fixA.getUserData()).setToDestroy();
                }
                break;
		
		
		}
		/*if(fixA.getUserData() == "shape" || fixB.getUserData() == "shape") {
		Fixture shape = fixA.getUserData() == "head" ? fixA : fixB;
		Fixture object = shape == fixA ? fixB : fixA;
		
	if(object.getUserData() instanceof ObjetoInterativo){
		((ObjetoInterativo) object.getUserData()).batida();
		}
		}*/
	}

	@Override
	public void endContact(Contact contact) {
	
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	
	}

}
