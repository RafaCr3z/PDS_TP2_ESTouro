package torre.estrategia;

import java.awt.Point;
import java.util.List;

import bloon.Bloon;
import torre.Torre;

/**
 * Estratégia de ataque que escolhe o bloon mais distante da torre.
 */
public class EstrategiaLonge implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> alvos) {
        // Se não há alvos, retorna null
        if (alvos.isEmpty())
            return null;

        // Inicia com o primeiro bloon
        Bloon longe = alvos.get(0);
        double maxDist = -1;
        Point pTorre = t.getComponente().getPosicaoCentro();

        // Procura o bloon mais distante
        for (Bloon b : alvos) {
            double dist = pTorre.distanceSq(b.getComponente().getPosicaoCentro());
            if (dist > maxDist) {
                maxDist = dist;
                longe = b;
            }
        }
        return longe;
    }

    @Override
    public String getNome() {
        // Retorna o nome da estratégia
        return "Longe";
    }

}
