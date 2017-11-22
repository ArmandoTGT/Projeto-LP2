package com.mygdx.game;

import java.io.*;
import java.net.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class JogadorCliente implements Runnable {
	static float  x;
	static float y;
	
	public JogadorCliente(){
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

          
			while(true){
		        String mensagem = PlayScreen.jogador.corpo.getPosition().x + " " + PlayScreen.jogador.corpo.getPosition().y 
				+ " " + PlayScreen.jogador.corpo.getAngle();
		        saida.writeUTF(mensagem); 
		        String entradas[] = entrada.readUTF().split(" ");
	            x = Float.parseFloat(entradas[0]);
	            y = Float.parseFloat(entradas[1]);
	            float angulo = Float.parseFloat(entradas[2]);
	            //System.out.println("x:" + x +  "y: " + y + " angulo: " + angulo);
	            PlayScreen.setnClientes(Integer.parseInt(entradas[3]));
	           
	            
	            /*if(i >= Integer.parseInt(entradas[3])){
	            	System.out.println("entrou aqui");
	            	PlayScreen.setnClientes();
	            	i++;
	            	}*/
	      
	            
			}
		} catch(Exception ex){
		System.out.println(ex.toString());	
		}
	}

}
