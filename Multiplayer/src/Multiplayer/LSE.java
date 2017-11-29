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

	/** Insere em lista vazia */
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
	
	
	/** Remove elemento do início da lista */
	private void removeInicioLista(){
		NoBuffer p = cabeca;

	    // Retira o 1o elemento da lista (p)
	    cabeca = p.getProx();
	    nElementos--;

	    // Sugere ao garbage collector que libere a memoria
	    //  da regiao apontada por p
	    System.out.println("Excluido o canal: " + p);
	    p = null;
	}

	/** Remove elemento no meio da lista */
	private void removeNaLista(int pos){
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
	        return;
	     }

	    /* retira o elemento da lista */
	    antecessor.setProx(atual.getProx());
	    nElementos--;

	    /* sugere ao garbage collector que libere a memoria
	     *  da regiao apontada por p*/
	    System.out.println("Excluido o canal: " + atual);
	    atual = null;
	}
	
	/**Remove um elemento de uma determinada posição
	    Retorna o valor a ser removido. 
	    -1 se a posição for inválida ou a lista estiver vazia*/
	public void remove(int pos) {
		// Lista vazia 
	    if (vazia()) {
	    		return;
	    }

	    // Remoção do elemento da cabeça da lista 
	    if (pos == 1){
			System.out.println("\nRemovida da posi��o inicial");
	    	removeInicioLista();
	    	return;
	    }
	    // Remoção em outro lugar da lista
	    else{
			System.out.println("\nRemovida da posi��o " + pos);
	    	removeNaLista(pos);
	        return;
	    }
	}

	//Recebemos a refer�ncia para a sala
	public void salaVazia(NoBuffer verifica){
		
		
		System.out.println("\nSala achada para esvaziar:" + verifica);
		//Agora verificamos se aquele NoBuffer est� vazio, se sim acontece a remo��o, se n�o segue o jogo
		if(verifica.canalVazio()) {
			System.out.println("\nEsvaziada");
			remove(canal(verifica) + 1); //Remove o n�mero daquele canal atualmente registrado no servidor
		}
		else {
			return;//Se n�o o m�todo como � void, n�o faz nada
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
	 * Vamos procurar em todos os n�s a partir de uma fila de n�s come�ando em 0
	 * at� achar uma sala com uma posi��o vazia e retornar� o ponteiro dessa sala
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
	
	
	//Primeiro pegamos a refer�ncia desse canal
	public int canal(NoBuffer canal){
		NoBuffer aux = cabeca;
		int nCanal = 0;
		
		//Ent�o vamos subindo um contador at� chegar no canal
		while(aux != canal){
			if(aux == null) return -1; //Caso n�o achemos a sala passada, vamos retonar um valor inv�lido(apenas para mais robustes)
			nCanal++;
			aux = aux.getProx();
		}
		
		return nCanal;		
	}
	
	
	
}

