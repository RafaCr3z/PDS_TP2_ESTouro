package torre.estrategia;

import java.util.List;

import bloon.Bloon;
import torre.Torre;

public class EstrategiaForte implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> alvos) {
        if (alvos.isEmpty())
            return null;

        Bloon forte = alvos.get(0);
        for (Bloon b : alvos) {
            if (b.getValor() > forte.getValor()) {
                forte = b;
            }
        }
        return forte;
    }

    @Override
    public String getNome() {
        return "Forte";
    }

}
