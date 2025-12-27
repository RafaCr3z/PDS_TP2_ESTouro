package mundo;

/**
 * Classe que guarda a informação sobre um ciclo de criação de bloons.
 */
public final class CicloCriacaoInfo {

	private final int ciclo; // em que ciclo vai ser criado o bloon
	private final int caminhoIdx; // índice do caminho a que o bloon vai ser adicionado
	private final String nomeBloon; // nome do bloon a ser criado

	/**
	 * Constroi um ciclo de criação
	 * 
	 */
	public CicloCriacaoInfo(int quando, int indiceCaminho, String nomeBloon) {
		this.ciclo = quando;
		this.caminhoIdx = indiceCaminho;
		this.nomeBloon = nomeBloon;
	}

	/** devolve o ciclo de criação */
	public int getCiclo() {
		return ciclo;
	}

	/** devolve o indice do caminho ao qual o bloon será adicionado */
	public int getCaminhoIdx() {
		return caminhoIdx;
	}

	/** devolve o nome do bloon a ser criado */
	public String getNomeBloon() {
		return nomeBloon;
	}
}