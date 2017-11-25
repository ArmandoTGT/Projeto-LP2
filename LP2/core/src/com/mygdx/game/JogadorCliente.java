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
	volatile String eu = "";
    volatile static String inimigos = "";
    Semaphore sem = new Semaphore(1);
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
		        String entradas[] = aux.split(" ");
		        String dadosInimigo[]  =  new String[40];
		        int cont = 0;
	           // x = Float.parseFloat(entradas[0]);
	            //y = Float.parseFloat(entradas[1]);
	           // float angulo = Float.parseFloat(entradas[2]);
	            //System.out.println("x:" + x +  "y: " + y + " angulo: " + angulo);
	            //PlayScreen.setnClientes(Integer.parseInt(entradas[3]));
		        //String aux[] = entrada.readUTF().split(" ");
		        //System.out.println("\n" + "antes: " + aux + "\n");
		        aux = " ";
		        //Filtro
		        for(int i = 0; i < entradas.length; i++){
		        	if(eu.equals(entradas[i])){
		        		i += 4;
		        	}else {
		        		dadosInimigo[cont] = entradas[i];
		        		cont++;
		        		aux += entradas[i] + " ";
		        	}		        	
		        	
		        }
		        cont = 0;
		     
		      // System.out.println("\n" +" depois: " + aux + "\n");
				//sincronizar com o render
				
		        for(int i = 0; i < dadosInimigo.length ; i+=5){
		        	try{
		        //	if(i == 0){
		        //		PlayScreen.setInimigo0(i, 
		        //			Float.parseFloat(dadosInimigo[cont + 1]),
		        //			Float.parseFloat(dadosInimigo[cont + 2]), 
		        //			Float.parseFloat(dadosInimigo[cont + 3]))
		        //			;
		        //			cont += 3;
		       // 	}
		        	/*
		        	 * Para o primeiro cliente, todo o array está nukk
		        	 * Para o segundo cliente
		        	 */
		        	
							
						
		        	switch (dadosInimigo[i]) {
						case "0":PlayScreen.setInimigo0(i, 
						        Float.parseFloat(dadosInimigo[i + 1]),
						       	Float.parseFloat(dadosInimigo[i + 2]), 
						       	Float.parseFloat(dadosInimigo[i + 3]),
						       	dadosInimigo[i + 4]);
						//System.out.println("\nCliente 0: " + dadosInimigo[i + 1] + " " + dadosInimigo[i + 2] + " " + dadosInimigo[i + 3] + " " + dadosInimigo[i + 4]);
							break;
						case "1":PlayScreen.setInimigo1(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);	
						//System.out.println("\nCliente 1: " + dadosInimigo[i + 1] + " " + dadosInimigo[i + 2] + " " + dadosInimigo[i + 3] + " " + dadosInimigo[i + 4]);
							break;
						case "2":PlayScreen.setInimigo2(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						//System.out.println("\nCliente 2: " + dadosInimigo[i + 1] + " " + dadosInimigo[i + 2] + " " + dadosInimigo[i + 3] + " " + dadosInimigo[i + 4]);
							break;
						case "3":PlayScreen.setInimigo3(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						
							break;
						case "4":PlayScreen.setInimigo4(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						
							break;
						case "5":PlayScreen.setInimigo5(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						
							break;
						case "6":PlayScreen.setInimigo6(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						
							break;
						case "7":PlayScreen.setInimigo7(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						
							break;
						case "8":PlayScreen.setInimigo8(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						
							break;
						case "9":PlayScreen.setInimigo9(i, 
				        		Float.parseFloat(dadosInimigo[i + 1]),
				       			Float.parseFloat(dadosInimigo[i + 2]), 
				       			Float.parseFloat(dadosInimigo[i + 3]),
				       			dadosInimigo[i + 4]);
						
							break;

						default:
							break;
						}
		        		
		        	//System.out.println(i + ": " + dadosInimigo[i]);
		        	}catch(Exception ex){
						break;// Quebra o for quando tenta acessar a posição de um inimigo que não existe
						//ex.printStackTrace();
					}
		        }
				
				
	           //System.out.println(
	        	//	   "Dados Recebidos: " + aux);
	          // System.out.println(
	    	   //     		   "Eu: " + eu);
	          // System.out.println(
	        	//	   "Inimigos: " + inimigos + "\n");
	            			}
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
