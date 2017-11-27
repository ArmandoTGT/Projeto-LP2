package com.mygdx.game;

public class FiltroJogador{
	volatile String eu = "";
	volatile static String inimigos = "";
	String aux;
	public String[] entradas, dadosInimigos;
	public int cont = 0;
	
	public FiltroJogador(String eu, String inimigos, String aux) {
		this.eu = eu;
		this.inimigos = inimigos;
		this.aux = aux;
	}

	public void filtro(){
		entradas = aux.split(" ");
		dadosInimigos = new String[40];
        for(int i = 0; i < entradas.length; i++){
        	if(eu.equals(entradas[i])){
        		i += 4;
        	}else {
        		dadosInimigos[cont] = entradas[i];
        		cont++;
        		aux += entradas[i] + " ";
        	}		        	
        }
        cont = 0;
        dadosInimigos();
	}
	
	public void dadosInimigos(){
		for(int i = 0; i < dadosInimigos.length ; i+=5){
			try{
			/*
			 * Para o primeiro cliente, todo o array está null
			 * Para o segundo cliente
			 */
	
			switch (dadosInimigos[i]) {
				case "0":
						PlayScreen.setInimigo0(i, 
				        Float.parseFloat(dadosInimigos[i + 1]),
				       	Float.parseFloat(dadosInimigos[i + 2]), 
				       	Float.parseFloat(dadosInimigos[i + 3]),
				       	dadosInimigos[i + 4]);
					break;
				case "1":PlayScreen.setInimigo1(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);	
					break;
				case "2":PlayScreen.setInimigo2(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);
					break;
				case "3":PlayScreen.setInimigo3(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);
					break;
				case "4":PlayScreen.setInimigo4(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);
					break;
				case "5":PlayScreen.setInimigo5(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);			
					break;
				case "6":PlayScreen.setInimigo6(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);
					break;
				case "7":PlayScreen.setInimigo7(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);			
					break;
				case "8":PlayScreen.setInimigo8(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);				
					break;
				case "9":PlayScreen.setInimigo9(i, 
			    		Float.parseFloat(dadosInimigos[i + 1]),
			   			Float.parseFloat(dadosInimigos[i + 2]), 
			   			Float.parseFloat(dadosInimigos[i + 3]),
			   			dadosInimigos[i + 4]);				
					break;
				default:
					break;
				}		
			}catch(Exception ex){
				break;// Quebra o for quando tenta acessar a posição de um inimigo que não existe
				//ex.pr	
			}	
		}
	}
}