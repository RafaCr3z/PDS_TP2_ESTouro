import java.io.File;
import javax.swing.*;

/**
 * Testa se o jogo pode ser inicializado com sucesso
 */
public class TestaInicializacao {
    
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
    
    private static boolean verificarRecursos() {
        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                System.out.println("   ❌ Directório 'data' não encontrado");
                return false;
            }
            
            File torresDir = new File("data/torres");
            File bloonsDir = new File("data/bloons");
            File niveisDir = new File("data/niveis");
            
            if (!torresDir.exists() || !bloonsDir.exists() || !niveisDir.exists()) {
                System.out.println("   ❌ Subdirectórios de recursos não encontrados");
                return false;
            }
            
            System.out.println("   ✓ data/torres encontrado");
            System.out.println("   ✓ data/bloons encontrado");
            System.out.println("   ✓ data/niveis encontrado");
            
            return true;
        } catch (Exception e) {
            System.out.println("   ❌ Erro: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean verificarClasses() {
        try {
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
    
    private static boolean inicializarJogo() {
        try {
            // Desabilitar Swing para modo headless (sem janela)
            System.setProperty("java.awt.headless", "true");
            
            // Tentar criar o painel principal
            JPanel testPanel = new JPanel();
            System.out.println("   ✓ JPanel criado");
            
            // Tentar criar creators
            torre.TorreCreator torreCreator = new torre.TorreCreator();
            System.out.println("   ✓ TorreCreator criado");
            
            bloon.BloonCreator bloonCreator = new bloon.BloonCreator();
            System.out.println("   ✓ BloonCreator criado");
            
            // Tentar criar alguns objetos
            torre.Torre t = torreCreator.criarMacaco();
            System.out.println("   ✓ Torre Macaco criada");
            
            bloon.Bloon b = bloonCreator.criarVermelho();
            System.out.println("   ✓ Bloon Vermelho criado");
            
            return true;
        } catch (Exception e) {
            System.out.println("   ❌ Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
