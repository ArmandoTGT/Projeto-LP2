package com.mygdx.game;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

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
    Protocolo filtroJogador;
    ReentrantLock lock;
    
	public JogadorCliente(){
		correndo = true;
		lock = new ReentrantLock(true);
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
				lock.lock();
		        String mensagem = PlayScreen.jogador.corpo.getPosition().x + " " + PlayScreen.jogador.corpo.getPosition().y 
				+ " " + PlayScreen.jogador.corpo.getAngle() + " " + PlayScreen.mandouTiro;
		        saida.writeUTF(mensagem);
		        eu = "";
		        inimigos = "";
		        String aux = "";
		        
		       //sem.acquire();
			        eu = entrada.readUTF();
			        inimigos = entrada.readUTF();
			        aux = entrada.readUTF();
				//sem.release();				
				filtroJogador = new Protocolo(eu, inimigos, aux);				
				filtroJogador.filtro();
				lock.unlock();
			}
			String mensagem = 0.0 + " " + 0.0 + " " + 0.0 + " " + "naoatirou";
			saida.writeUTF(mensagem);			
			

			entrada.close();
			saida.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
