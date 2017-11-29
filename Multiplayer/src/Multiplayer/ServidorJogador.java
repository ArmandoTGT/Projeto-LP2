package Multiplayer;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

public class ServidorJogador {
	public static void main(String[] args) {
		//Aqui definiremos os principais atributos usados por esse servidor
		System.out.println("Servidor inicializado");
		
		//Inicializada as salas
		LSE canais = new LSE();
		canais.insere(canais.tamanho() + 1); //Adicionada sequencialmente na fila, na primeira posição
		NoBuffer dados = canais.localiza();
		
		//Inicializado o pool
		ExecutorService piscina = Executors.newCachedThreadPool();
		
		try{
		ServerSocket observadorJogador = new ServerSocket(20000);
		
		while(true){
			/*
			 * Agora nós vamos definir quem serão os próximos elementos a serem inseridos
			 * primeiro verificamos se existe algum Nó vazio na lista
			 */
				
			Socket atendenteJogador = observadorJogador.accept();
			//Cliente chegou, vamos verificar aonde ele será inserido
			dados = canais.localiza();
			
			if(dados == null) {//Caso ele não ache nenhum
				canais.insere(canais.tamanho() + 1);//Se cria um novo canal
				dados = canais.localiza();//Ele recebe esse local com as novas salas
				System.out.println("Nenhuma sala vazia\nCriada a sala: " + dados);
				}
			
			piscina.submit(new InformacoesServidor(atendenteJogador, dados.getId(), canais, dados));
			dados.addClientes();
			
			}
		}catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}		
}

