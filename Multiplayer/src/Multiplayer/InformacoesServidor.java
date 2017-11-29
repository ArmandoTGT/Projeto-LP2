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
	LSE canais;
	NoBuffer dados;
    Semaphore sem = new Semaphore(1);
	
	public InformacoesServidor(Socket atendente, int clientes, LSE canais, NoBuffer dados){
		this.atendente = atendente;
		this.cliente = clientes;
		this.dados = dados;
		this.canais = canais;
	}
	@Override
	
	public void run() {
    boolean correndo = true;	
	try{

		DataInputStream entrada = new DataInputStream(atendente.getInputStream());
        //Saida de bit enviados pelo socket cliente
        DataOutputStream saida = new DataOutputStream(atendente.getOutputStream());
		//Saida de bits enviados pelo socket servidor
		while(correndo){
			String aux = "";
            mensagemRecebida = cliente + " " + entrada.readUTF() + " ";
            //Pacote est� sendo mandado para a porta em que o inimigoCliente est� esperando respostas
 			for(int i = 0; i < dados.getClientes(); i++){
 				//A gente setta a cada rodada do for para garantir que o aux vai ser colocada naquela
 	  			dados.setMensagem(cliente, mensagemRecebida); 
 				aux += dados.getMensagem(i);
 			}
 			//System.out.println("Valores no servidor" + aux + "Qtde de clientes" + dados.getClientes());
 			try {
				sem.acquire();
 	            saida.writeUTF(String.valueOf(cliente));
 	            saida.writeUTF(String.valueOf(dados.getClientes() - 1));
 	            saida.writeUTF(aux);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			sem.release();
 		}//Caso saia do while, fecha as portas de comunica��o
		entrada.close();
		saida.close();
	} catch (Exception e) {
		System.out.println("Conex�o desfeita");
		dados.removeCliente(cliente);//Primeiro removemos aquele cliente online
		//Ent�o fazemos uma fila de verifica��o para ver se quando aquele cliente sai, ele � o �ltimo da sala
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
		}
		canais.salaVazia(dados);
        sem.release();
		correndo = false;
	} 
	}

}
