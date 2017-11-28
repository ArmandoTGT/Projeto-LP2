package com.mygdx.game;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class JogadorCliente implements Runnable {
	volatile float  x;
	volatile float y;
	static volatile boolean correndo;
	volatile String eu = "";
    volatile static String inimigos = "";
    Semaphore sem = new Semaphore(1);
    FiltroJogador filtroJogador;
    
	public JogadorCliente(){
		correndo = true;
	}
	@Override
	
	public void run() {
		System.out.println("Cliente inicializado");
		try {

			Socket jogador = new Socket("localhost",20000);
			DataInputStream entrada = new DataInputStream(jogador.getInputStream());
	        //Saida de bit enviados pelo socket cliente
	        DataOutputStream saida = new DataOutputStream(jogador.getOutputStream());
	        //Vamos usar as mensagens de entrada e saida do servidor:
	        
          
			while(correndo){
		        String mensagem = PlayScreen.jogador.corpo.getPosition().x + " " + PlayScreen.jogador.corpo.getPosition().y 
				+ " " + PlayScreen.jogador.corpo.getAngle() + " " + PlayScreen.mandouTiro;
		        saida.writeUTF(mensagem);
		        eu = "";
		        inimigos = "";
		        String aux = "";
		        
		        sem.acquire();
			        eu = entrada.readUTF();
			        inimigos = entrada.readUTF();
			        aux = entrada.readUTF();
				sem.release();				
				filtroJogador = new FiltroJogador(eu, inimigos, aux);
				filtroJogador.filtro();
			}
			String mensagem = 0.0 + " " + 0.0 + " " + 0.0 + " " + "naoatirou";
			saida.writeUTF(mensagem);
			
			System.out.println("Catapulta");
			/*if (eu.equals("0")) {
				System.out.println("if 1");
				PlayScreen.setInimigo0(0, 0.0f, 0.0f, 0.0f, "naoatirou");
			}
			if (eu.equals("1")) {
				PlayScreen.setInimigo1(0, 0.0f, 0.0f, 0.0f, "naoatirou");
				System.out.println("if 2");
			}*/
				

			entrada.close();
			saida.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
