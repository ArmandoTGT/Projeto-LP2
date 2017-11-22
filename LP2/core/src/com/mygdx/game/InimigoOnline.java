package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class InimigoOnline extends Thread {

	public void run(int i) {
		System.out.println(i);
		PlayScreen.inimigo[i].corpo.setTransform(
				new Vector2( JogadorCliente.x - 0.50f, JogadorCliente.y - 0.50f), 
				PlayScreen.jogador.corpo.getAngle());
		
	}

}
