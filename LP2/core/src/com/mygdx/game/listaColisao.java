package com.mygdx.game;

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

		
		if(fixA.getUserData() == "shape" || fixB.getUserData() == "shape") {
		Fixture shape = fixA.getUserData() == "head" ? fixA : fixB;
		Fixture object = shape == fixA ? fixB : fixA;
		
	if(object.getUserData() instanceof ObjetoInterativo){
		((ObjetoInterativo) object.getUserData()).batida();
		}
		}
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
