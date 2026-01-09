package torre.estrategia;

import bloon.Bloon;
import java.util.List;
import torre.Torre;

/**
 * Interface que define a estratégia de ataque de uma torre.
 */
public interface EstrategiaAtaque {
    /**
     * Escolhe um alvo de dentro de uma lista de bloons (já filtrada pelo alcance).
     */
    Bloon escolherAlvo(Torre t, List<Bloon> bloons);

    /**
     * Retorna o nome da estratégia (para uso na interface gráfica ou debug).
     */
    String getNome();

    /**
     * Método estático para verificar o funcionamento das estratégias de ataque.
     * 
     */
    public static boolean verificarEstrategias() {
        System.out.println("=== Verificação das Estratégias de Ataque ===");

        //Verificar se as estratégias implementam a interface corretamente
        EstrategiaLonge longe = new EstrategiaLonge();
        EstrategiaForte forte = new EstrategiaForte();

        boolean interfaceOk = (longe instanceof EstrategiaAtaque) &&
                (forte instanceof EstrategiaAtaque) &&
                "Longe".equals(longe.getNome()) &&
                "Forte".equals(forte.getNome());

        System.out.println(" Conformidade com interface: " + (interfaceOk ? "PASS" : "FAIL"));

        //Verificar comportamento com listas vazias
        java.util.List<Bloon> listaVazia = new java.util.ArrayList<>();
        boolean vaziaOk = (longe.escolherAlvo(null, listaVazia) == null) &&
                (forte.escolherAlvo(null, listaVazia) == null);

        System.out.println(" Listas vazias: " + (vaziaOk ? "PASS" : "FAIL"));

        System.out.println("=== Verificação Concluída ===");
        System.out.println("Resultado: "
                + (interfaceOk && vaziaOk ? "TODAS AS VERIFICAÇÕES PASSARAM" : "ALGUMAS VERIFICAÇÕES FALHARAM"));

        return interfaceOk && vaziaOk;
    }
}
