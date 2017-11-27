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
			//ServerSocket mensageiroInimigo = new ServerSocket(30000);
				System.out.println("Antes do atendente" + dados.getId() + "\n");
				Socket atendenteJogador = observadorJogador.accept();				
				//Criar uma outra sala
				if(dados.getClientes() == 4) {
					System.out.println("Buffer próxima sala");
					dados = new buffer();
				}
					System.out.println("Conversando com cliente");
					piscina.submit(new InformacoesServidor(atendenteJogador, dados.getId(), dados));
					dados.addClientes();
					System.out.println("Depois do atendente" + dados.getId() + "\n");
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

