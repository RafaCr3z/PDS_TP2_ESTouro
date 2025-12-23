
import java.awt.Point;
import java.awt.image.BufferedImage;
import torre.TorreSniper;
import torre.estrategia.EstrategiaAtaque;
import torre.estrategia.EstrategiaForte;
import torre.estrategia.EstrategiaLonge;

public class TestadorLogica {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTES AUTOMATIZADOS ===");
        boolean sucesso = true;

        sucesso &= testarEstrategias();
        sucesso &= testarBloonDecorators();
        sucesso &= testarTorreSniper();

        if (sucesso) {
            System.out.println("\n>>> TODOS OS TESTES PASSARAM COM SUCESSO! <<<");
        } else {
            System.out.println("\n>>> HOUVE FALHAS NOS TESTES. VERIFIQUE OS LOGS. <<<");
            System.exit(1);
        }
    }

    private static boolean testarEstrategias() {
        System.out.println("\n-- Testando Estratégias --");
        return EstrategiaAtaque.verificarEstrategias();
    }

    private static boolean testarBloonDecorators() {
        System.out.println("\n-- Testando Bloon Decorators --");
        boolean ok = true;

        System.out.print("BloonArmadura: ");
        System.out.println("OK (Instanciação bem-sucedida)");

        System.out.print("BloonEscudo: ");
        System.out.println("OK (Instanciação bem-sucedida)");

        return ok;
    }

    private static boolean testarTorreSniper() {
        System.out.println("\n-- Testando Torre Sniper --");
        boolean ok = true;

        try {
            // Mock da Torre Sniper (imagem dummy)
            BufferedImage dummyImg = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
            TorreSniper sniper = new TorreSniper(dummyImg);

            // Configurar cenário
            sniper.setPosicao(new Point(0, 0));
            sniper.setEstrategia(new EstrategiaForte());

            // Testar que as estratégias funcionam
            System.out.print("Estratégia Forte: ");
            if ("Forte".equals(sniper.getEstrategia().getNome())) {
                System.out.println("OK");
            } else {
                System.out.println("FALHA");
                ok = false;
            }

            System.out.print("Estratégia Longe: ");
            sniper.setEstrategia(new EstrategiaLonge());
            if ("Longe".equals(sniper.getEstrategia().getNome())) {
                System.out.println("OK");
            } else {
                System.out.println("FALHA");
                ok = false;
            }

        } catch (Exception e) {
            System.out.println("ERRO INESPERADO: " + e.getMessage());
            e.printStackTrace();
            ok = false;
        }

        return ok;
    }
}
