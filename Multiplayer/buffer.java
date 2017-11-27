package Multiplayer;

import java.util.concurrent.Semaphore;

public class buffer {
	private int clientes;
	private int idCliente;
	private  String mensagem[];
	private boolean online[];
	Semaphore sem1 = new Semaphore(1);
	Semaphore sem2 = new Semaphore(0);
	
	public buffer() {
		idCliente = 0; //Identificador passado para aquele cliente
		clientes = 0; //Numero total de clientes online
		mensagem = new String[20];
		online = new boolean[4];
		for(int i = 0; i < online.length; i++) {
			//if(i == 0) online[i] = true; //O servidor já acha que há um jogador online sempre
			online[i] = false;
			System.out.println(i);
		}
	}
	
	public synchronized void addClientes() {
		//Primeiro procuramos a posição do identificador daquele cliente
		for(int i = 0; i < online.length; i++) {
			if(online[i] == false) {
				System.out.println("Valor de i no buffer: " + i);
				online[i] = true;
				/*
				 * Mandamos a próxima posição como aquela para settar o número de clientes
				 * 
				 */
				idCliente = ++i; //Atribuimos o valor dele assim para que não exista o cliente nulo
				break;
			}
		}

		clientes++;
	}
	
	public synchronized void removeCliente(int clienteOffline) {
		//mensagem[clienteOffline] = "";
		online[clienteOffline] = false;
		idCliente = clienteOffline;
		clientes--;
	}
	
	public synchronized int getId() {
		return idCliente;
	}
	
	
	public synchronized int getClientes() {
		return clientes;
	}
	
	public void setMensagem(int pos, String mensagem) {
		try {
			sem1.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mensagem[pos] = mensagem;
		sem2.release();
	}
	
	public String getMensagem(int pos) {
		try {
			sem2.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String aux = mensagem[pos];

		sem1.release();

		return aux;
	}
}
