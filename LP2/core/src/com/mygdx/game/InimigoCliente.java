package com.mygdx.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class InimigoCliente implements Runnable {
	private String[] entradas;
	public static String tecla;
	public static float angulo, x, y;
	public static int inimigo;
	
	public InimigoCliente(){
	}
	
	
	@Override
	public void run() {
		try{
			Socket jogador = new Socket("localhost",30000);
			DataInputStream entrada = new DataInputStream(jogador.getInputStream());
	        //Saida de bit enviados pelo socket cliente
	        
			while(true){
	            
	            entradas = entrada.readUTF().split(" ");
	            x = Float.parseFloat(entradas[0]);
	            y = Float.parseFloat(entradas[1]);
	            angulo = Float.parseFloat(entradas[2]);
	            inimigo = Integer.parseInt(entradas[3]);
	            System.out.println("Posicao x:" + x +  "Posicao y: " + y + " Angulo da nave: " + angulo
	            		+ " Nave:" + inimigo);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
