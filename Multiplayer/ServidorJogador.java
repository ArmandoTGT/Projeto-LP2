package Multiplayer;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

public class ServidorJogador {
	
	public static void main(String[] args) {
		System.out.println("Servidor inicializado");
		
		buffer dados = new buffer();
		ExecutorService piscina = Executors.newCachedThreadPool();
		try{
		ServerSocket observadorJogador = new ServerSocket(20000);
		while(true){
				if(dados.getClientes() == 2) {
					System.out.println("Buffer próxima sala");
					dados = new buffer();
				}
				//ServerSocket mensageiroInimigo = new ServerSocket(30000);
					Socket atendenteJogador = observadorJogador.accept();
					piscina.submit(new InformacoesServidor(atendenteJogador, dados.getClientes(), dados));
					System.out.println("Conversando com cliente");
				
					/*if(dados.getClientes() > 0) {
						Socket distribuidorInimigo = mensageiroInimigo.accept();
						piscina.submit(new InformacoesInimigo(distribuidorInimigo, dados.getClientes(), dados));
						i++;
						System.out.println("Quantidade atual de inimigos: " + i);
					}*/
					dados.addClientes();
					System.out.println(dados.getClientes());
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

