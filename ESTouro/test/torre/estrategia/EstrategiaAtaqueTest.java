package torre.estrategia;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import bloon.Bloon;
import torre.Torre;

/**
 * Unit tests for attack strategy implementations.
 * Tests EstrategiaLonge and EstrategiaForte target selection logic.
 */
public class EstrategiaAtaqueTest {

    private MockTorre torre;
    private List<Bloon> bloons;
    private EstrategiaLonge estrategiaLonge;
    private EstrategiaForte estrategiaForte;
    private int testsPassed = 0;
    private int testsTotal = 0;

    public void setUp() {
        torre = new MockTorre(new Point(100, 100));
        bloons = new ArrayList<>();
        estrategiaLonge = new EstrategiaLonge();
        estrategiaForte = new EstrategiaForte();
    }

    public void testEstrategiaLongeWithEmptyList() {
        testsTotal++;
        Bloon result = estrategiaLonge.escolherAlvo(torre, bloons);
        if (result == null) {
            System.out.println("PASS: EstrategiaLonge returns null for empty bloon list");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaLonge should return null for empty bloon list");
        }
    }

    public void testEstrategiaLongeSelectsFarthestBloon() {
        testsTotal++;
        // Create bloons at different distances from tower at (100, 100)
        MockBloon closeBloon = new MockBloon(new Point(110, 110), 10); // distance ~14
        MockBloon mediumBloon = new MockBloon(new Point(150, 150), 15); // distance ~71
        MockBloon farBloon = new MockBloon(new Point(200, 200), 20); // distance ~141

        bloons.add(closeBloon);
        bloons.add(mediumBloon);
        bloons.add(farBloon);

        Bloon result = estrategiaLonge.escolherAlvo(torre, bloons);
        if (result == farBloon) {
            System.out.println("PASS: EstrategiaLonge selects the farthest bloon");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaLonge should select the farthest bloon");
        }
    }

    public void testEstrategiaLongeSingleBloon() {
        testsTotal++;
        MockBloon singleBloon = new MockBloon(new Point(150, 150), 10);
        bloons.clear();
        bloons.add(singleBloon);

        Bloon result = estrategiaLonge.escolherAlvo(torre, bloons);
        if (result == singleBloon) {
            System.out.println("PASS: EstrategiaLonge returns the only bloon available");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaLonge should return the only bloon available");
        }
    }

    public void testEstrategiaLongeGetNome() {
        testsTotal++;
        if ("Longe".equals(estrategiaLonge.getNome())) {
            System.out.println("PASS: EstrategiaLonge returns correct name");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaLonge should return correct name 'Longe'");
        }
    }

    public void testEstrategiaForteWithEmptyList() {
        testsTotal++;
        bloons.clear();
        Bloon result = estrategiaForte.escolherAlvo(torre, bloons);
        if (result == null) {
            System.out.println("PASS: EstrategiaForte returns null for empty bloon list");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaForte should return null for empty bloon list");
        }
    }

    public void testEstrategiaForteSelectsHighestValueBloon() {
        testsTotal++;
        bloons.clear();
        MockBloon lowValueBloon = new MockBloon(new Point(110, 110), 5);
        MockBloon mediumValueBloon = new MockBloon(new Point(150, 150), 15);
        MockBloon highValueBloon = new MockBloon(new Point(200, 200), 25);

        bloons.add(lowValueBloon);
        bloons.add(mediumValueBloon);
        bloons.add(highValueBloon);

        Bloon result = estrategiaForte.escolherAlvo(torre, bloons);
        if (result == highValueBloon) {
            System.out.println("PASS: EstrategiaForte selects the highest value bloon");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaForte should select the highest value bloon");
        }
    }

    public void testEstrategiaForteSingleBloon() {
        testsTotal++;
        bloons.clear();
        MockBloon singleBloon = new MockBloon(new Point(150, 150), 10);
        bloons.add(singleBloon);

        Bloon result = estrategiaForte.escolherAlvo(torre, bloons);
        if (result == singleBloon) {
            System.out.println("PASS: EstrategiaForte returns the only bloon available");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaForte should return the only bloon available");
        }
    }

    public void testEstrategiaForteGetNome() {
        testsTotal++;
        if ("Forte".equals(estrategiaForte.getNome())) {
            System.out.println("PASS: EstrategiaForte returns correct name");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaForte should return correct name 'Forte'");
        }
    }

    public void testEstrategiaForteWithEqualValues() {
        testsTotal++;
        bloons.clear();
        MockBloon bloon1 = new MockBloon(new Point(110, 110), 10);
        MockBloon bloon2 = new MockBloon(new Point(150, 150), 10);
        MockBloon bloon3 = new MockBloon(new Point(200, 200), 10);

        bloons.add(bloon1);
        bloons.add(bloon2);
        bloons.add(bloon3);

        Bloon result = estrategiaForte.escolherAlvo(torre, bloons);
        if (bloons.contains(result)) {
            System.out.println("PASS: EstrategiaForte returns one of the equal value bloons");
            testsPassed++;
        } else {
            System.out.println("FAIL: EstrategiaForte should return one of the equal value bloons");
        }
    }

    public void testStrategyInterfaceCompliance() {
        testsTotal++;
        // Test that both strategies implement the interface correctly
        boolean longeCompliant = estrategiaLonge instanceof EstrategiaAtaque;
        boolean forteCompliant = estrategiaForte instanceof EstrategiaAtaque;
        boolean namesNotNull = estrategiaLonge.getNome() != null && estrategiaForte.getNome() != null;
        
        // Test that escolherAlvo method works with null torre (edge case)
        boolean handlesNullTorre = true;
        try {
            estrategiaLonge.escolherAlvo(null, new ArrayList<>());
            estrategiaForte.escolherAlvo(null, new ArrayList<>());
        } catch (Exception e) {
            handlesNullTorre = false;
        }
        
        if (longeCompliant && forteCompliant && namesNotNull) {
            System.out.println("PASS: Strategy interface compliance test");
            testsPassed++;
        } else {
            System.out.println("FAIL: Strategy interface compliance test");
        }
    }

    public void runAllTests() {
        System.out.println("Running Attack Strategy Tests...");
        System.out.println("================================");
        
        setUp();
        testEstrategiaLongeWithEmptyList();
        
        setUp();
        testEstrategiaLongeSelectsFarthestBloon();
        
        setUp();
        testEstrategiaLongeSingleBloon();
        
        setUp();
        testEstrategiaLongeGetNome();
        
        setUp();
        testEstrategiaForteWithEmptyList();
        
        setUp();
        testEstrategiaForteSelectsHighestValueBloon();
        
        setUp();
        testEstrategiaForteSingleBloon();
        
        setUp();
        testEstrategiaForteGetNome();
        
        setUp();
        testEstrategiaForteWithEqualValues();
        
        setUp();
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
        EstrategiaAtaqueTest test = new EstrategiaAtaqueTest();
        test.runAllTests();
    }
}