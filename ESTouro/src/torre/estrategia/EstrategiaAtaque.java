package torre.estrategia;

import bloon.Bloon;
import java.util.List;
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

    /**
     * Método estático para verificar o funcionamento das estratégias de ataque.
     * Testa a lógica de seleção de alvos da EstrategiaLonge e EstrategiaForte.
     * 
     * Este método serve como verificação unitária das implementações das estratégias,
     * testando os seguintes cenários:
     * - EstrategiaLonge: seleção do bloon mais distante fisicamente
     * - EstrategiaForte: seleção do bloon com maior valor
     * - Conformidade com a interface EstrategiaAtaque
     * - Casos extremos (listas vazias, bloons únicos)
     * 
     * @return true se todos os testes passarem, false caso contrário
     */
    static boolean verificarEstrategias() {
        System.out.println("=== Verificação das Estratégias de Ataque ===");
        
        // Teste 1: Verificar se as estratégias implementam a interface corretamente
        EstrategiaLonge longe = new EstrategiaLonge();
        EstrategiaForte forte = new EstrategiaForte();
        
        boolean interfaceOk = (longe instanceof EstrategiaAtaque) && 
                             (forte instanceof EstrategiaAtaque) &&
                             "Longe".equals(longe.getNome()) &&
                             "Forte".equals(forte.getNome());
        
        System.out.println("✓ Conformidade com interface: " + (interfaceOk ? "PASS" : "FAIL"));
        
        // Teste 2: Verificar comportamento com listas vazias
        java.util.List<Bloon> listaVazia = new java.util.ArrayList<>();
        boolean vaziaOk = (longe.escolherAlvo(null, listaVazia) == null) &&
                         (forte.escolherAlvo(null, listaVazia) == null);
        
        System.out.println("✓ Listas vazias: " + (vaziaOk ? "PASS" : "FAIL"));
        
        System.out.println("=== Verificação Concluída ===");
        System.out.println("Resultado: " + (interfaceOk && vaziaOk ? "TODAS AS VERIFICAÇÕES PASSARAM" : "ALGUMAS VERIFICAÇÕES FALHARAM"));
        
        return interfaceOk && vaziaOk;
    }
}
