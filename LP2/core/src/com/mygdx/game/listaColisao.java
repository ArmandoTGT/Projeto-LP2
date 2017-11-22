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
				if(fixA.getFilterData().categoryBits == ExGame.PLAYER_BIT)
                    ((ObjetoInterativo) fixB.getUserData()).batida();//((NavePlayer) fixA.getUserData());
                else
                    ((ObjetoInterativo) fixA.getUserData()).batida();//((NavePlayer) fixB.getUserData());
                break;
			case ExGame.PLAYERLASER_BIT | ExGame.INIMIGO_BIT:
				if(fixA.getFilterData().categoryBits == ExGame.PLAYERLASER_BIT)
                    ((NaveInimiga) fixB.getUserData()).levaDano();//((NavePlayer) fixA.getUserData());
                else
                    ((NaveInimiga) fixA.getUserData()).levaDano();//((NavePlayer) fixB.getUserData());
                break;
			case ExGame.PLAYER_BIT | ExGame.INIMIGO_BIT:
				System.out.println("BATEU MORAL");
				//if(fixA.getFilterData().categoryBits == ExGame.PLAYERLASER_BIT)
                 //   ((NaveInimiga) fixB.getUserData()).levaDano();//((NavePlayer) fixA.getUserData());
              //  else
                //    ((NaveInimiga) fixA.getUserData()).levaDano();//((NavePlayer) fixB.getUserData());
                break;
			case ExGame.PLAYER_BIT | ExGame.INIMIGOLASER_BIT:
				System.out.println("LEVOU TIRO");
				//if(fixA.getFilterData().categoryBits == ExGame.PLAYERLASER_BIT)
                 //   ((NaveInimiga) fixB.getUserData()).levaDano();//((NavePlayer) fixA.getUserData());
              //  else
                //    ((NaveInimiga) fixA.getUserData()).levaDano();//((NavePlayer) fixB.getUserData());
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
