package Multiplayer;

import java.util.concurrent.Semaphore;

public class LSE {
	private NoBuffer cabeca;
	private int nElementos;

	public LSE(){
		cabeca = null;
		nElementos = 0;
	}
	
	/*
	 * Os métodos da quantidade de canais  não tem nenhum momento problema de sincronização, pois a alocação
	 * de novas salas e alocação de um thread atedente para cada cliente é feita de maneira sequencial
	 */
	
	//Com esse método se não há nenhuma sala registrada
	public boolean vazia() {
	    if (nElementos == 0)
	        return true;
	    else
	        return false;
	}

	//Conseguimos o tamanho total da lista
	public int tamanho() {
	    //return nElementos;
		NoBuffer aux = cabeca;
		int cont = 0;
		while(aux != null){
			aux = aux.getProx();
			cont++;
		}
		return cont;
	}

	//Através desse método, vamos recolher o elemento salvo em uma posição da lista
	public NoBuffer elemento (int pos) {
	    if (vazia()) {
	        return null; // Se a lista estiver vazia retorna null
	    }

	    if ((pos < 1) || (pos > tamanho())){
	        return null; // Se a posição passada for inválida
	    }
	    
	    NoBuffer aux = cabeca;
	    // Percorre a lista do 1o elemento até uma posição anterior
	    for (int i =1; i < pos - 1; i++){
	        // modifica "aux" para apontar para o proximo elemento da lista 
	        aux = aux.getProx();
	    }

	    return aux.getProx(); //Retornamos o próximo elemento, que é referente a sala em que queremos acesso
	}

	/** Insere nÃ³ em lista vazia */
	private boolean insereInicioLista() {
	    // Aloca memoria para novo no 
	    NoBuffer novoNo = new NoBuffer();
	    
	    // Agora vamos fazer o add do novo nó
	    
	    novoNo.setProx(cabeca);
	    cabeca = novoNo;
	    nElementos++;
	    return true;
	}

	/** Insere no meio da lista */
	private boolean insereMeioLista(int pos){
	    
	    // Aloca memoria para novo no
	    NoBuffer novoNo = new NoBuffer();

	    // Localiza a pos. ANTERIOR onde será inserido o novo nó
	    NoBuffer aux = cabeca;
	    for (int i =1; i < pos-1; i++){
	        // modifica "aux" para apontar ao proximo elemento da lista 
	        aux = aux.getProx();
	    }
	    
	    NoBuffer p = aux.getProx(); //Guardamos o elemento que estaria na posição posterior
	    novoNo.setProx(p); //Apontamos para essa posição
	    aux.setProx(novoNo); //Fazemos a posição anterior apontar para o que tem agora
	    
	    nElementos++;
	    return true;
	}

	/** Insere no fim da lista */
	private boolean insereFimLista(){
	    // Aloca memoria para novo no 
	    NoBuffer novoNo = new NoBuffer();

	    // Procura o final da lista 
	    NoBuffer aux = this.cabeca;
	    while(aux.getProx() != null){
	        aux = aux.getProx();
	    }

	    novoNo.setProx(null); //Faz o novo nó apontar para o nulo
	    aux.setProx(novoNo); //O que estava no fim anteriormente passa a apontar para ele

	    this.nElementos++;
	    return true;
	}

	/**Insere um elemento em uma determinada posição
	    Retorna true se conseguir inserir e 
	    false caso contrario */
	public boolean insere(int pos) {
		if ((vazia()) && (pos != 1)){
	        return false; /* lista vazia mas posicao inv*/
	    }

	 	/* inserÃ§Ã£o no inÃ­cio da lista (ou lista vazia)*/
	    if (pos == 1){
	        return insereInicioLista();
	    }
	    /* inserÃ§Ã£o no fim da lista */
	    else if (pos == nElementos+1){
	        return insereFimLista();
	   }
	   /* inserÃ§Ã£o no meio da lista */
	   else{
	        return insereMeioLista(pos);
	   }
	}

	/** Remove elemento do inicio da lista */
	private NoBuffer localizaInicioLista(){
	    NoBuffer p = cabeca;
	    return p;
	}

	/** Remove elemento no meio da lista */
	private NoBuffer localizaNaLista(int pos){
	     NoBuffer atual = null, antecessor = null;
	     int dado = -1;
	     int cont = 1;

	     /* Localiza o nó que será removido*/
	     atual = cabeca;
	     while((cont < pos) && (atual != null)){
	           antecessor = atual;
	           atual = atual.getProx();
	           cont++;
	     }

	     if (atual == null) { /* pos. inválida */
	        return null;
	     }

	    return atual;
	}

	/*
	 * Procura uma sala que tenha uma posição vazia de jogadores
	 * para que o cliente venha a ser inserido
	 */
	public NoBuffer localiza() {

		/*
		 * Vamos fazer um for, de modo que esse for verifique numa função em cada nó se ele tem uma vaga
		 * para novos jogadores
		 */
		NoBuffer aux = cabeca;
		while( aux != null) {
			if(aux.posVazia()) {
				System.out.println("Achou sala com pos vazia: " + aux);
				return aux;
			}
			else aux = aux.getProx();
		}
		
		return null; //Caso não ache nenhuma sala vazia, retorna null
	}
	
	
	
}

