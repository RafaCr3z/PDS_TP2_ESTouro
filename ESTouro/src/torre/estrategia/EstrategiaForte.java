package torre.estrategia;

import java.util.List;

import bloon.Bloon;
import torre.Torre;

/**
 * Estratégia de ataque que escolhe o bloon com o maior valor (mais forte).
 */
public class EstrategiaForte implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> alvos) {
        // Se não há alvos, retorna null
        if (alvos.isEmpty())
            return null;

        // Inicia com o primeiro bloon
        Bloon forte = alvos.get(0);
        // Procura o bloon com maior valor
        for (Bloon b : alvos) {
            if (b.getValor() > forte.getValor()) {
                forte = b;
            }
        }
        return forte;
    }

    @Override
    public String getNome() {
        // Retorna o nome da estratégia
        return "Forte";
    }

}
