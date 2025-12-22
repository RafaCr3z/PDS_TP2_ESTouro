package torre.estrategia;

import java.awt.Point;
import java.util.List;

import bloon.Bloon;
import torre.Torre;

public class EstrategiaLonge implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> alvos) {
        if (alvos.isEmpty())
            return null;

        Bloon longe = alvos.get(0);
        double maxDist = -1;
        Point pTorre = t.getComponente().getPosicaoCentro();

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
        return "Longe";
    }

}
