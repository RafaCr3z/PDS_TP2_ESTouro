import java.io.File;
import javax.swing.*;
import torre.TorreCreator;
import bloon.BloonCreator;

/**
 * Testa se o jogo pode ser inicializado com sucesso
 */
public class TestaInicializacao {
<<<<<<< HEAD
    
    /**
     * Método principal que executa os testes de inicialização.
     * Verifica recursos, classes e inicialização do jogo em modo headless.
     * 
     */
=======

>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
    public static void main(String[] args) {
        System.out.println("=== TESTE DE INICIALIZAÇÃO DO ESTOURO ===\n");

        // 1. Verificar recursos
        System.out.println("1. Verificando ficheiros de recursos...");
        boolean recursosOK = verificarRecursos();
        System.out.println(recursosOK ? "   ✅ Recursos encontrados\n" : "   ❌ Recursos não encontrados\n");

        // 2. Tentar carregar classes principais
        System.out.println("2. Verificando carregamento de classes...");
        boolean classesOK = verificarClasses();
        System.out.println(classesOK ? "   ✅ Classes carregadas com sucesso\n" : "   ❌ Erro ao carregar classes\n");

        // 3. Tentar inicializar o jogo (headless mode para testes)
        System.out.println("3. Inicializando jogo em modo headless...");
        boolean jogoOK = inicializarJogo();
        System.out.println(jogoOK ? "   ✅ Jogo inicializado com sucesso\n" : "   ❌ Erro ao inicializar jogo\n");
<<<<<<< HEAD
        
        // Avaliar resultado geral
=======

>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
        if (recursosOK && classesOK && jogoOK) {
            System.out.println(">>> JOGO PRONTO PARA USAR! <<<");
            System.out.println("\nPara executar o jogo completo com interface gráfica:");
            System.out.println("  java -cp bin game.EstouroJogo");
            System.exit(0);
        } else {
            System.out.println(">>> PROBLEMAS DETECTADOS. VERIFIQUE OS ERROS ACIMA. <<<");
            System.exit(1);
        }
    }
<<<<<<< HEAD
    
    /**
     * Verifica se os diretórios e arquivos de recursos necessários existem.
     * 
     */
=======

>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
    private static boolean verificarRecursos() {
        try {
            // Verificar diretório principal de dados
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                System.out.println("   ❌ Directório 'data' não encontrado");
                return false;
            }
<<<<<<< HEAD
            
            // Verificar subdiretórios específicos
=======

>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
            File torresDir = new File("data/torres");
            File bloonsDir = new File("data/bloons");
            File niveisDir = new File("data/niveis");

            if (!torresDir.exists() || !bloonsDir.exists() || !niveisDir.exists()) {
                System.out.println("   ❌ Subdirectórios de recursos não encontrados");
                return false;
            }
<<<<<<< HEAD
            
            // Confirmar diretórios encontrados
=======

>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
            System.out.println("   ✓ data/torres encontrado");
            System.out.println("   ✓ data/bloons encontrado");
            System.out.println("   ✓ data/niveis encontrado");

            return true;
        } catch (Exception e) {
            System.out.println("   ❌ Erro: " + e.getMessage());
            return false;
        }
    }
<<<<<<< HEAD
    
    /**
     * Verifica se as classes principais do jogo podem ser carregadas.
     * 
     */
=======

>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
    private static boolean verificarClasses() {
        try {
            // Tentar carregar classes principais
            Class.forName("game.EstouroJogo");
            System.out.println("   ✓ EstouroJogo");

            Class.forName("torre.TorreCreator");
            System.out.println("   ✓ TorreCreator");

            Class.forName("bloon.BloonCreator");
            System.out.println("   ✓ BloonCreator");

            Class.forName("mundo.GestorNivel");
            System.out.println("   ✓ GestorNivel");

            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("   ❌ Classe não encontrada: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("   ❌ Erro: " + e.getMessage());
            return false;
        }
    }
<<<<<<< HEAD
    
    /**
     * Tenta inicializar o jogo em modo headless (sem interface gráfica).
     * Cria instâncias básicas dos componentes principais.
     * 
     */
=======

>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
    private static boolean inicializarJogo() {
        try {
            // Desabilitar Swing para modo headless (sem janela)
            System.setProperty("java.awt.headless", "true");

            // Tentar criar o painel principal
            new JPanel();
            System.out.println("   ✓ JPanel criado");

            // Tentar criar creators
            TorreCreator torreCreator = new TorreCreator();
            System.out.println("   ✓ TorreCreator criado");

            BloonCreator bloonCreator = new BloonCreator();
            System.out.println("   ✓ BloonCreator criado");
<<<<<<< HEAD
            
            // Tentar criar alguns objetos de exemplo
            torre.Torre t = torreCreator.criarMacaco();
=======

            // Tentar criar alguns objetos
            torreCreator.criarMacaco();
>>>>>>> cdb46ae3b6236264dce0df8fc26503757feda409
            System.out.println("   ✓ Torre Macaco criada");

            bloonCreator.criarVermelho();
            System.out.println("   ✓ Bloon Vermelho criado");

            return true;
        } catch (Exception e) {
            System.out.println("   ❌ Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
