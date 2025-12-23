import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

// Import the strategy classes
import torre.estrategia.EstrategiaAtaque;
import torre.estrategia.EstrategiaLonge;
import torre.estrategia.EstrategiaForte;

// Import interfaces
import bloon.Bloon;
import torre.Torre;

/**
 * Simple unit tests for attack strategy implementations.
 * Tests EstrategiaLonge and EstrategiaForte target selection logic.
 */
public class EstrategiaAtaqueTestSimple {

    private int testsPassed = 0;
    private int testsTotal = 0;

    // Simple mock implementations
    static class SimpleTorre implements Torre {
        private Point posicao;
        
        public SimpleTorre(Point pos) { this.posicao = pos; }
        
        public prof.jogos2D.image.ComponenteMultiAnimado getComponente() {
            return new prof.jogos2D.image.ComponenteMultiAnimado() {
                public Point getPosicaoCentro() { return posicao; }
                public void setPosicaoCentro(Point p) { posicao = p; }
                public java.awt.Rectangle getBounds() { return new java.awt.Rectangle(posicao.x-10, posicao.y-10, 20, 20); }
                public void desenhar(java.awt.Graphics2D g) {}
                public prof.jogos2D.image.ComponenteMultiAnimado clone() { return this; }
            };
        }
        
        // Minimal implementations for other methods
        public void setEstrategia(torre.estrategia.EstrategiaAtaque e) {}
        public torre.estrategia.EstrategiaAtaque getEstrategia() { return null; }
        public void setPosicao(Point p) { posicao = p; }
        public void setMundo(mundo.Mundo m) {}
        public mundo.Mundo getMundo() { return null; }
        public torre.projetil.Projetil[] atacar(List<Bloon> bloons) { return new torre.projetil.Projetil[0]; }
        public void setRaioAcao(int raio) {}
        public int getRaioAcao() { return 100; }
        public Point getPontoDisparo() { return posicao; }
        public void desenhar(java.awt.Graphics2D g) {}
        public void desenhaRaioAcao(java.awt.Graphics2D g) {}
        public Torre clone() { return new SimpleTorre(posicao); }
        public void accept(torre.TorreVisitor v) {}
    }

    static class SimpleBloon implements Bloon {
        private Point posicao;
        private int valor;
        
        public SimpleBloon(Point pos, int val) { 
            this.posicao = pos; 
            this.valor = val; 
        }
        
        public prof.jogos2D.image.ComponenteVisual getComponente() {
            return new prof.jogos2D.image.ComponenteVisual() {
                public Point getPosicaoCentro() { return posicao; }
                public void setPosicaoCentro(Point p) { posicao = p; }
                public java.awt.Rectangle getBounds() { return new java.awt.Rectangle(posicao.x-5, posicao.y-5, 10, 10); }
                public void desenhar(java.awt.Graphics2D g) {}
                public prof.jogos2D.image.ComponenteVisual clone() { return this; }
            };
        }
        
        public int getValor() { return valor; }
        public void setValor(int val) { this.valor = val; }
        
        // Minimal implementations for other methods
        public void desenhar(java.awt.Graphics2D g) {}
        public void mover() {}
        public prof.jogos2D.image.ComponenteVisual getPopComponente() { return getComponente(); }
        public void setCaminho(mundo.Caminho rua) {}
        public mundo.Caminho getCaminho() { return null; }
        public int getPosicaoNoCaminho() { return 0; }
        public void setPosicaoNoCaminho(int pos) {}
        public void setVelocidade(float veloc) {}
        public float getVelocidade() { return 1.0f; }
        public void setMundo(mundo.Mundo w) {}
        public mundo.Mundo getMundo() { return null; }
        public void setPosicao(Point p) { posicao = p; }
        public java.awt.Rectangle getBounds() { return getComponente().getBounds(); }
        public int pop(int estrago) { return 0; }
        public void explode(int estrago) {}
        public int getResistencia() { return 1; }
        public void addBloonObserver(bloon.BloonObserver bo) {}
        public void removeBloonObserver(bloon.BloonObserver bo) {}
        public Bloon clone() { return new SimpleBloon(posicao, valor); }
    }

    public void testEstrategiaLongeWithEmptyList() {
        testsTotal++;
        EstrategiaLonge estrategia = new EstrategiaLonge();
        Torre torre = new SimpleTorre(new Point(100, 100));
        List<Bloon> bloons = new ArrayList<>();
        
        Bloon result = estrategia.escolherAlvo(torre, bloons);
        if (result == null) {
            System.out.println("PASS: EstrategiaLonge returns null for empty bloon list");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaLonge should return null for empty bloon list");
        }
    }

    public void testEstrategiaLongeSelectsFarthestBloon() {
        testsTotal++;
        EstrategiaLonge estrategia = new EstrategiaLonge();
        Torre torre = new SimpleTorre(new Point(100, 100));
        List<Bloon> bloons = new ArrayList<>();
        
        // Create bloons at different distances from tower at (100, 100)
        SimpleBloon closeBloon = new SimpleBloon(new Point(110, 110), 10); // distance ~14
        SimpleBloon mediumBloon = new SimpleBloon(new Point(150, 150), 15); // distance ~71
        SimpleBloon farBloon = new SimpleBloon(new Point(200, 200), 20); // distance ~141

        bloons.add(closeBloon);
        bloons.add(mediumBloon);
        bloons.add(farBloon);

        Bloon result = estrategia.escolherAlvo(torre, bloons);
        if (result == farBloon) {
            System.out.println("PASS: EstrategiaLonge selects the farthest bloon");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaLonge should select the farthest bloon");
        }
    }

    public void testEstrategiaForteSelectsHighestValueBloon() {
        testsTotal++;
        EstrategiaForte estrategia = new EstrategiaForte();
        Torre torre = new SimpleTorre(new Point(100, 100));
        List<Bloon> bloons = new ArrayList<>();
        
        SimpleBloon lowValueBloon = new SimpleBloon(new Point(110, 110), 5);
        SimpleBloon mediumValueBloon = new SimpleBloon(new Point(150, 150), 15);
        SimpleBloon highValueBloon = new SimpleBloon(new Point(200, 200), 25);

        bloons.add(lowValueBloon);
        bloons.add(mediumValueBloon);
        bloons.add(highValueBloon);

        Bloon result = estrategia.escolherAlvo(torre, bloons);
        if (result == highValueBloon) {
            System.out.println("PASS: EstrategiaForte selects the highest value bloon");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaForte should select the highest value bloon");
        }
    }

    public void testStrategyNames() {
        testsTotal++;
        EstrategiaLonge longe = new EstrategiaLonge();
        EstrategiaForte forte = new EstrategiaForte();
        
        boolean longeNameCorrect = "Longe".equals(longe.getNome());
        boolean forteNameCorrect = "Forte".equals(forte.getNome());
        
        if (longeNameCorrect && forteNameCorrect) {
            System.out.println("PASS: Strategy names are correct");
            testsPassed++;
        } else {
            System.out.println("FAIL: Strategy names are incorrect");
        }
    }

    public void testStrategyInterfaceCompliance() {
        testsTotal++;
        EstrategiaLonge longe = new EstrategiaLonge();
        EstrategiaForte forte = new EstrategiaForte();
        
        // Test that both strategies implement the interface correctly
        boolean longeCompliant = longe instanceof EstrategiaAtaque;
        boolean forteCompliant = forte instanceof EstrategiaAtaque;
        
        if (longeCompliant && forteCompliant) {
            System.out.println("PASS: Strategy interface compliance test");
            testsPassed++;
        } else {
            System.out.println("FAIL: Strategy interface compliance test");
        }
    }

    public void runAllTests() {
        System.out.println("Running Attack Strategy Tests...");
        System.out.println("================================");
        
        testEstrategiaLongeWithEmptyList();
        testEstrategiaLongeSelectsFarthestBloon();
        testEstrategiaForteSelectsHighestValueBloon();
        testStrategyNames();
        testStrategyInterfaceCompliance();
        
        System.out.println("================================");
        System.out.println("Tests passed: " + testsPassed + "/" + testsTotal);
        if (testsPassed == testsTotal) {
            System.out.println("ALL TESTS PASSED!");
        } else {
            System.out.println("Some tests failed.");
        }
    }

    public static void main(String[] args) {
        EstrategiaAtaqueTestSimple test = new EstrategiaAtaqueTestSimple();
        test.runAllTests();
    }
}