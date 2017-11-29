package Multiplayer;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NoBuffer {
	//Settamos primeiro os dados básicos de cada sala
	private int clientes;
	private int idCliente;
	private  String mensagem[];
	private boolean online[];

	//Como faremos uma lista de salas, a próxima posição será null
	private NoBuffer prox;

	//Usaremos semáforos para resolver o problema de escritor-leitor que tem em nosso código
	private Semaphore mutex;
	private Semaphore escritorTrava;
	private Semaphore filaJusta;
	private int nLeitores;
	
	//Método construtor que vai definir os parâmetros que cada sala deverá manter registrado
	public NoBuffer() {
		idCliente = 0; //Identificador passado para aquele cliente
		clientes = 0; //Numero total de clientes online
		mensagem = new String[10];
		online = new boolean[3];
		for(int i = 0; i < online.length; i++) {
			//if(i == 0) online[i] = true; //O servidor já acha que há um jogador online sempre
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
	 * Todos os métodos de cada canal precisam ser sincronizados, pois o acesso as informação daquela sala
	 * precisa mandar a mesma coisa para cada um dos seus clientes
	 */
	
	//Essa função vai funcional para que verifiquemos se alguma sala(ponteiro) possui alguma posição vazia
	public synchronized boolean posVazia() {
		/*
		 * Vamos varrer o array, vendo se possui alguma posição de players online como false 
		 * nesse buffer 
		 */
		for(int i = 0; i < online.length; i++) {
			if(online[i] == false) {
				return true; //Caso ache alguma posição como false, retorna true
			}
		}
		return false; //Caso varre aquele for sem achar nenhuma posição de players online vazia, ele retorna false
	}
	
	//Método que adiconará o número total de clientes existentes naquela sala
	//Não precisa ser sincronizado, pois não é usado em nenhuma parte concorrente
	public void addClientes() {
		System.out.println("adicionou");
		//Primeiro procuramos a posição do identificador daquele cliente
		for(int i = 0; i < online.length; i++) {
			if(online[i] == false) {
				online[i] = true;
				/*
				 * Mandamos a próxima posição como aquela para settar o número de clientes
				 */
				idCliente = ++i; //Atribuimos o valor dele assim para que não exista o cliente nulo
				break;
			}
		}

		clientes++;
	}
	
	//Método que remove o total de clientes
	//Foi sincronizado, para o caso de dois clientes sairem ao mesmo tempo e um sobreescrever o outro na hora que for computar
	public synchronized void removeCliente(int clienteOffline) {
		//mensagem[clienteOffline] = "";
		online[clienteOffline] = false;
		idCliente = clienteOffline;
		//clientes--;
	}
	
	//Método que retorna o identificador do cliente
	/*
	 * O id do cliente é algo único dele que é passado sequencialmente no momento em que
	 * alocamos um atendente para se comunicar, por isso não precisa ser sincronizado
	 */
	public int getId() {
		return idCliente;
	}
	
	//Método que retorna o número total de clientes
	/*
	 * Número de clientes pode ir mudando conforme um for saindo e entrando,
	 * portando, esse método só pode retornar no instante em que não há nenhum outro
	 * método dentro desse buffer mexendo nele
	 */
	public synchronized int getClientes() {
		return clientes;
	}
	
	/*
	 *Nos próximos dois métodos prefirimos utilizar uma fila com o uso de semáforos
	 *esse array cada cliente acessará apenas a seção de memória que lhe diz respeito
	 *logo temos um problema de escritor-leitor com mensagem simples, nesse caso
	 *o mais intuitivo dos métodos é o do semáforo.
	 */
	
	//Método que escreve a mensagem que será mandada
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
	
	//Método que pegará os dados totais daquela sala
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

