package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class InimigoOnline extends Thread {

	int inimigo;
	
	public InimigoOnline(int i) {		
		inimigo = i;
	}

	public void run() {		
		PlayScreen.inimigo[inimigo].corpo.setTransform(
		new Vector2( PlayScreen.x[inimigo], PlayScreen.y[inimigo]), PlayScreen.ang[inimigo]); 
				}

}
