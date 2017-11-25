package Multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.Semaphore;

public class InformacoesServidor implements Runnable {

	private Socket atendente;
	public String mensagemRecebida;
	int cliente;
	buffer dados;
    Semaphore sem = new Semaphore(1);
	
	public InformacoesServidor(Socket atendente, int clientes, buffer dados){
		this.atendente = atendente;
		this.cliente = clientes;
		this.dados = dados;
		System.out.println(dados);
	}
	@Override
	
	public void run() {
	try{

		DataInputStream entrada = new DataInputStream(atendente.getInputStream());
        //Saida de bit enviados pelo socket cliente
        DataOutputStream saida = new DataOutputStream(atendente.getOutputStream());
		//Saida de bits enviados pelo socket servidor
		while(true){
			String aux = "";
             mensagemRecebida = cliente + " " + entrada.readUTF() + " ";
             /*
              * Primeiro servidor recebe valor
              * Segundo o cliente recebe o valor, e recebe quem ele é
              */
             //Pacote está sendo mandado para a porta em que o inimigoCliente está esperando respostas
 			for(int i = 0; i < dados.getClientes(); i++){
 	  			dados.setMensagem(cliente, mensagemRecebida);
 				aux += dados.getMensagem(i);
 			}
           // for(int i = 0; i < dados.getClientes(); i++){
            //		if(dados.getMensagem(i).equals(mensagemRecebida)) continue;
 			try {
				sem.acquire();
 	            saida.writeUTF(String.valueOf(cliente));
 	            saida.writeUTF(String.valueOf(dados.getClientes() - 1));
 	            saida.writeUTF(aux);
 	            sem.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
