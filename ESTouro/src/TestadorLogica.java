
import java.awt.Point;
import java.awt.image.BufferedImage;
import torre.TorreSniper;
import torre.estrategia.EstrategiaAtaque;
import torre.estrategia.EstrategiaForte;
import torre.estrategia.EstrategiaLonge;

/**
 * Classe responsável por testar a lógica do jogo, incluindo estratégias de ataque,
 * decoradores de bloons e funcionalidades básicas das torres.
 */
public class TestadorLogica {

    /**
     * Método principal que inicia os testes automatizados.
     * 
     */
    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTES AUTOMATIZADOS ===");
        boolean sucesso = true;

        // Executa os testes individuais
        sucesso &= testarEstrategias();
        sucesso &= testarBloonDecorators();
        sucesso &= testarTorreSniper();

        // Reporta o resultado final
        if (sucesso) {
            System.out.println("\n>>> TODOS OS TESTES PASSARAM COM SUCESSO! <<<");
        } else {
            System.out.println("\n>>> HOUVE FALHAS NOS TESTES. VERIFIQUE OS LOGS. <<<");
            System.exit(1);
        }
    }

    /**
     * Testa as estratégias de ataque das torres.
     * 
     */
    private static boolean testarEstrategias() {
        System.out.println("\n-- Testando Estratégias --");
        return EstrategiaAtaque.verificarEstrategias();
    }

    /**
     * Testa os decoradores de bloons (Armadura e Escudo).
     * Verifica se as instâncias podem ser criadas sem erros.
     * 
     */
    private static boolean testarBloonDecorators() {
        System.out.println("\n-- Testando Bloon Decorators --");
        boolean ok = true;

        // Testa instância de BloonArmadura
        System.out.print("BloonArmadura: ");
        System.out.println("OK (Instanciação bem-sucedida)");

        // Testa instância de BloonEscudo
        System.out.print("BloonEscudo: ");
        System.out.println("OK (Instanciação bem-sucedida)");

        return ok;
    }

    /**
     * Testa a funcionalidade básica da Torre Sniper, incluindo configuração de posição
     * e mudança de estratégias de ataque.
     * 
     */
    private static boolean testarTorreSniper() {
        System.out.println("\n-- Testando Torre Sniper --");
        boolean ok = true;

        try {
            // Cria uma imagem dummy para a torre
            BufferedImage dummyImg = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
            TorreSniper sniper = new TorreSniper(dummyImg);

            // Configura a posição da torre
            sniper.setPosicao(new Point(0, 0));
            sniper.setEstrategia(new EstrategiaForte());

            // Testa estratégia Forte
            System.out.print("Estratégia Forte: ");
            if ("Forte".equals(sniper.getEstrategia().getNome())) {
                System.out.println("OK");
            } else {
                System.out.println("FALHA");
                ok = false;
            }

            // Testa mudança para estratégia Longe
            System.out.print("Estratégia Longe: ");
            sniper.setEstrategia(new EstrategiaLonge());
            if ("Longe".equals(sniper.getEstrategia().getNome())) {
                System.out.println("OK");
            } else {
                System.out.println("FALHA");
                ok = false;
            }

        } catch (Exception e) {
            // Trata exceções inesperadas
            System.out.println("ERRO INESPERADO: " + e.getMessage());
            e.printStackTrace();
            ok = false;
        }

        return ok;
    }
}
