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
	 * Os m�todos da quantidade de canais  n�o tem nenhum momento problema de sincroniza��o, pois a aloca��o
	 * de novas salas e aloca��o de um thread atedente para cada cliente � feita de maneira sequencial
	 */
	
	//Com esse m�todo se n�o h� nenhuma sala registrada
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

	//Atrav�s desse m�todo, vamos recolher o elemento salvo em uma posi��o da lista
	public NoBuffer elemento (int pos) {
	    if (vazia()) {
	        return null; // Se a lista estiver vazia retorna null
	    }

	    if ((pos < 1) || (pos > tamanho())){
	        return null; // Se a posi��o passada for inv�lida
	    }
	    
	    NoBuffer aux = cabeca;
	    // Percorre a lista do 1o elemento at� uma posi��o anterior
	    for (int i =1; i < pos - 1; i++){
	        // modifica "aux" para apontar para o proximo elemento da lista 
	        aux = aux.getProx();
	    }

	    return aux.getProx(); //Retornamos o pr�ximo elemento, que � referente a sala em que queremos acesso
	}

	/** Insere nó em lista vazia */
	private boolean insereInicioLista() {
	    // Aloca memoria para novo no 
	    NoBuffer novoNo = new NoBuffer();
	    
	    // Agora vamos fazer o add do novo n�
	    
	    novoNo.setProx(cabeca);
	    cabeca = novoNo;
	    nElementos++;
	    return true;
	}

	/** Insere no meio da lista */
	private boolean insereMeioLista(int pos){
	    
	    // Aloca memoria para novo no
	    NoBuffer novoNo = new NoBuffer();

	    // Localiza a pos. ANTERIOR onde ser� inserido o novo n�
	    NoBuffer aux = cabeca;
	    for (int i =1; i < pos-1; i++){
	        // modifica "aux" para apontar ao proximo elemento da lista 
	        aux = aux.getProx();
	    }
	    
	    NoBuffer p = aux.getProx(); //Guardamos o elemento que estaria na posi��o posterior
	    novoNo.setProx(p); //Apontamos para essa posi��o
	    aux.setProx(novoNo); //Fazemos a posi��o anterior apontar para o que tem agora
	    
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

	    novoNo.setProx(null); //Faz o novo n� apontar para o nulo
	    aux.setProx(novoNo); //O que estava no fim anteriormente passa a apontar para ele

	    this.nElementos++;
	    return true;
	}

	/**Insere um elemento em uma determinada posi��o
	    Retorna true se conseguir inserir e 
	    false caso contrario */
	public boolean insere(int pos) {
		if ((vazia()) && (pos != 1)){
	        return false; /* lista vazia mas posicao inv*/
	    }

	 	/* inserção no início da lista (ou lista vazia)*/
	    if (pos == 1){
	        return insereInicioLista();
	    }
	    /* inserção no fim da lista */
	    else if (pos == nElementos+1){
	        return insereFimLista();
	   }
	   /* inserção no meio da lista */
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

	     /* Localiza o n� que ser� removido*/
	     atual = cabeca;
	     while((cont < pos) && (atual != null)){
	           antecessor = atual;
	           atual = atual.getProx();
	           cont++;
	     }

	     if (atual == null) { /* pos. inv�lida */
	        return null;
	     }

	    return atual;
	}

	/*
	 * Procura uma sala que tenha uma posi��o vazia de jogadores
	 * para que o cliente venha a ser inserido
	 */
	public NoBuffer localiza() {

		/*
		 * Vamos fazer um for, de modo que esse for verifique numa fun��o em cada n� se ele tem uma vaga
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
		
		return null; //Caso n�o ache nenhuma sala vazia, retorna null
	}
	
	
	
}

