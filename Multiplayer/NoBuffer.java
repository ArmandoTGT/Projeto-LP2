package Multiplayer;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NoBuffer {
	//Settamos primeiro os dados b�sicos de cada sala
	private int clientes;
	private int idCliente;
	private  String mensagem[];
	private boolean online[];

	//Como faremos uma lista de salas, a pr�xima posi��o ser� null
	private NoBuffer prox;

	//Usaremos sem�foros para resolver o problema de escritor-leitor que tem em nosso c�digo
	private Semaphore mutex;
	private Semaphore escritorTrava;
	private Semaphore filaJusta;
	private int nLeitores;
	
	//M�todo construtor que vai definir os par�metros que cada sala dever� manter registrado
	public NoBuffer() {
		idCliente = 0; //Identificador passado para aquele cliente
		clientes = 0; //Numero total de clientes online
		mensagem = new String[10];
		online = new boolean[3];
		for(int i = 0; i < online.length; i++) {
			//if(i == 0) online[i] = true; //O servidor j� acha que h� um jogador online sempre
			online[i] = false;
			System.out.println(i);
		}
		
		setProx(null);
		
		mutex = new Semaphore(1);
		escritorTrava = new Semaphore(1);
		filaJusta = new Semaphore(1);
		nLeitores = 0;
	}
	
	/*
	 * Todos os m�todos de cada canal precisam ser sincronizados, pois o acesso as informa��o daquela sala
	 * precisa mandar a mesma coisa para cada um dos seus clientes
	 */
	
	//Essa fun��o vai funcional para que verifiquemos se alguma sala(ponteiro) possui alguma posi��o vazia
	public synchronized boolean posVazia() {
		/*
		 * Vamos varrer o array, vendo se possui alguma posi��o de players online como false 
		 * nesse buffer 
		 */
		for(int i = 0; i < online.length; i++) {
			if(online[i] == false) {
				return true; //Caso ache alguma posi��o como false, retorna true
			}
		}
		return false; //Caso varre aquele for sem achar nenhuma posi��o de players online vazia, ele retorna false
	}
	
	//M�todo que adiconar� o n�mero total de clientes existentes naquela sala
	//N�o precisa ser sincronizado, pois n�o � usado em nenhuma parte concorrente
	public void addClientes() {
		System.out.println("adicionou");
		//Primeiro procuramos a posi��o do identificador daquele cliente
		for(int i = 0; i < online.length; i++) {
			if(online[i] == false) {
				online[i] = true;
				/*
				 * Mandamos a pr�xima posi��o como aquela para settar o n�mero de clientes
				 */
				idCliente = ++i; //Atribuimos o valor dele assim para que n�o exista o cliente nulo
				break;
			}
		}

		clientes++;
	}
	
	//M�todo que remove o total de clientes
	//Foi sincronizado, para o caso de dois clientes sairem ao mesmo tempo e um sobreescrever o outro na hora que for computar
	public synchronized void removeCliente(int clienteOffline) {
		//mensagem[clienteOffline] = "";
		online[clienteOffline] = false;
		idCliente = clienteOffline;
		//clientes--;
	}
	
	//M�todo que retorna o identificador do cliente
	/*
	 * O id do cliente � algo �nico dele que � passado sequencialmente no momento em que
	 * alocamos um atendente para se comunicar, por isso n�o precisa ser sincronizado
	 */
	public int getId() {
		return idCliente;
	}
	
	//M�todo que retorna o n�mero total de clientes
	/*
	 * N�mero de clientes pode ir mudando conforme um for saindo e entrando,
	 * portando, esse m�todo s� pode retornar no instante em que n�o h� nenhum outro
	 * m�todo dentro desse buffer mexendo nele
	 */
	public synchronized int getClientes() {
		return clientes;
	}
	
	/*
	 *Nos pr�ximos dois m�todos prefirimos utilizar uma fila com o uso de sem�foros
	 *esse array cada cliente acessar� apenas a se��o de mem�ria que lhe diz respeito
	 *logo temos um problema de escritor-leitor com mensagem simples, nesse caso
	 *o mais intuitivo dos m�todos � o do sem�foro.
	 */
	
	//M�todo que escreve a mensagem que ser� mandada
	public void setMensagem(int pos, String mensagem) {
		try {
			filaJusta.acquire();
			escritorTrava.acquire();
			filaJusta.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mensagem[pos] = mensagem;
		escritorTrava.release();
	}
	
	//M�todo que pegar� os dados totais daquela sala
	public String getMensagem(int pos) {
		String aux;
		try {
			filaJusta.acquire();
			mutex.acquire();
			nLeitores++;
			if(nLeitores == 1) escritorTrava.acquire();
			mutex.release();
			filaJusta.release();
		} catch (InterruptedException e) {
			System.out.println("Leitura interrompida");
		}
		aux = mensagem[pos];

		try {
			mutex.acquire();
			nLeitores--;
			if(nLeitores == 0) escritorTrava.release();
			mutex.release();
		} catch (InterruptedException e) {
			System.out.println("Leitura interrompida");
		}

		return aux;
	}

	public NoBuffer getProx() {
		return prox;
	}

	public void setProx(NoBuffer prox) {
		this.prox = prox;
	}
	
	
}

