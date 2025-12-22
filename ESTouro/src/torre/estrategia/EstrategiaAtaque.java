package torre.estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Interface que define a estratégia de ataque de uma torre.
 * O padrão Strategy é usado aqui para encapsular os diferentes algoritmos de
 * escolha de alvo.
 */
public interface EstrategiaAtaque {
    /**
     * Escolhe um alvo de dentro de uma lista de bloons (já filtrada pelo alcance).
     * 
     * @param t      a torre que está a atacar (para acesso a posição, etc)
     * @param bloons lista de bloons disponiveis (dentro do alcance)
     * @return o bloon escolhido ou null se nenhum for adequado
     */
    Bloon escolherAlvo(Torre t, List<Bloon> bloons);

    /**
     * Retorna o nome da estratégia (para uso na interface gráfica ou debug).
     */
    String getNome();
}
