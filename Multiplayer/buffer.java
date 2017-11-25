package Multiplayer;

import java.util.concurrent.Semaphore;

public class buffer {
	private int clientes;
	private  String mensagem[];
	Semaphore sem1 = new Semaphore(1);
	Semaphore sem2 = new Semaphore(0);
	
	public buffer() {
		clientes = 0;
		mensagem = new String[20];
	}
	
	public void addClientes() {
		clientes++;
	}
	
	public int getClientes() {
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
