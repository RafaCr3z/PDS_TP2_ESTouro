package bloon;

import java.awt.Point;
import java.awt.image.BufferedImage;

import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import bloon.decorador.BloonImune;
import bloon.decorador.BloonArmadura;
import bloon.decorador.BloonEscudo;

/**
 * Classe respsonável por criar os vários bloons presentes no jogo
 */
public class BloonCreator {

	// Carregador de imagens para os componentes visuais dos bloons
	private ImageLoader loader = ImageLoader.getLoader();
	// Mapa que associa nomes de códigos a funções que criam os respectivos bloons
	private java.util.Map<String, java.util.function.Supplier<Bloon>> fabrica = new java.util.HashMap<>();

	/**
	 * Construtor que inicializa a fábrica com os mapeamentos de nomes para funções de criação.
	 */
	public BloonCreator() {
		fabrica.put("vermelho", this::criarVermelho);
		fabrica.put("azul", this::criarAzul);
		fabrica.put("verde", this::criarVerde);
		fabrica.put("amarelo", this::criarAmarelo);
		fabrica.put("rosa", this::criarRosa);
		fabrica.put("metal", this::criarMetal);
		fabrica.put("barro", this::criarBarro);
		fabrica.put("preto", this::criarPreto);
		fabrica.put("branco", this::criarBranco);
		fabrica.put("zep_azul", this::criarZeppelinAzul);
		fabrica.put("zep_verde", this::criarZeppelinVerde);
		fabrica.put("zep_amarelo", this::criarZeppelinAmarelo);
		fabrica.put("zep_rosa", this::criarZeppelinRosa);
		fabrica.put("zep_metal", this::criarZeppelinMetal);
		fabrica.put("zep_preto", this::criarZeppelinPreto);
	}

	/**
	 * método que cria o bloon com um determinado nome de código
	 * 
	 */
	public Bloon criarBloon(String nome) {
		if (fabrica.containsKey(nome))
			return fabrica.get(nome).get();
		return null;
	}

	private ComponenteVisual getImagem(String nome) {
		// Carrega a imagem do bloon a partir do diretório data/bloons/nome/imagem.gif
		return new ComponenteSimples(loader.getImage("data/bloons/" + nome + "/imagem.gif"));
	}

	private ComponenteVisual getImagemPop() {
		// Carrega a animação de explosão padrão
		return new ComponenteAnimado(new Point(),
				(BufferedImage) loader.getImage("data/misc/pop.png"), 2, 2);
	}

	private ComponenteVisual getImagemPopZep() {
		// Carrega a animação de explosão para zeppelins
		return new ComponenteAnimado(new Point(),
				(BufferedImage) loader.getImage("data/misc/pop_zep.png"), 2, 2);
	}

	private ComponenteVisual getImagemArmadura() {
		// Carrega a imagem da armadura
		return new ComponenteSimples(new Point(), loader.getImage("data/misc/armadura.png"));
	}

	private ComponenteVisual getImagemEscudo() {
		// Carrega a imagem do escudo
		return new ComponenteSimples(new Point(), loader.getImage("data/misc/escudo.png"));
	}

	public Bloon criarVermelho() {
		// Cria um bloon vermelho simples
		ComponenteVisual imagem = new ComponenteSimples(loader.getImage("data/bloons/vermelho/imagem.gif"));
		ComponenteVisual imagemPop = new ComponenteAnimado(new Point(),
				(BufferedImage) loader.getImage("data/misc/pop.png"), 2, 2);
		return new BloonSimples(imagem, imagemPop, 3, 1, 2);
	}

	public Bloon criarVermelhoRapido() {
		// Cria um bloon vermelho rápido
		ComponenteVisual imagem = new ComponenteSimples(loader.getImage("data/bloons_fast/vermelho/imagem.gif"));
		ComponenteVisual imagemPop = getImagemPop();
		return new BloonSimples(imagem, imagemPop, 8, 1, 2);
	}

	public Bloon createAzulRapido() {
		// Cria um bloon azul rápido
		ComponenteVisual imagem = new ComponenteSimples(loader.getImage("data/bloons_fast/azul/imagem.gif"));
		ComponenteVisual imagemPop = getImagemPop();
		return new BloonSimples(imagem, imagemPop, 10, 2, 3);
	}

	public Bloon criarAzul() {
		// Cria um bloon azul multicamada contendo um vermelho
		ComponenteVisual imagem = new ComponenteSimples(loader.getImage("data/bloons/azul/imagem.gif"));
		ComponenteVisual imagemPop = new ComponenteAnimado(new Point(),
				(BufferedImage) loader.getImage("data/misc/pop.png"), 2, 2);

		BloonMultiCamada blue = new BloonMultiCamada(imagem, imagemPop, 4, 2, 3);
		blue.addBloon(criarVermelho());
		return blue;
	}

	public Bloon criarVerde() {
		// Cria um bloon verde multicamada contendo um azul
		ComponenteVisual imagem = new ComponenteSimples(loader.getImage("data/bloons/verde/imagem.gif"));
		ComponenteVisual imagemPop = new ComponenteAnimado(new Point(),
				(BufferedImage) loader.getImage("data/misc/pop.png"), 2, 2);
		BloonMultiCamada green = new BloonMultiCamada(imagem, imagemPop, 4.5f, 3, 4);
		green.addBloon(criarAzul());
		return green;
	}

	public Bloon criarAmarelo() {
		// Cria um bloon amarelo multicamada contendo um verde e um vermelho rápido
		ComponenteVisual imagem = new ComponenteSimples(loader.getImage("data/bloons/amarelo/imagem.gif"));
		ComponenteVisual imagemPop = new ComponenteAnimado(new Point(),
				(BufferedImage) loader.getImage("data/misc/pop.png"), 2, 2);
		BloonMultiCamada yellow = new BloonMultiCamada(imagem, imagemPop, 5, 4, 5);
		yellow.addBloon(criarVerde());
		yellow.addBloon(criarVermelhoRapido());
		return yellow;
	}

	public Bloon criarRosa() {
		// Cria um bloon rosa multicamada contendo um amarelo e vermelhos rápidos
		ComponenteVisual imagem = getImagem("rosa");
		ComponenteVisual imagemPop = getImagemPop();
		BloonMultiCamada pink = new BloonMultiCamada(imagem, imagemPop, 5, 4, 6);
		pink.addBloon(criarAmarelo());
		pink.addBloon(criarVermelhoRapido());
		pink.addBloon(criarVermelhoRapido());
		pink.addBloon(createAzulRapido());
		return pink;
	}

	public Bloon criarMetal() {
		// Cria um bloon metal imune a perfurantes, contendo rosas
		ComponenteVisual imagem = getImagem("metal");
		ComponenteVisual imagemPop = getImagemPop();
		// Imune a perfurantes
		BloonMultiCamada metal = new BloonMultiCamada(imagem, imagemPop, 3, 5, 7);
		metal.addBloon(criarRosa());
		metal.addBloon(criarRosa());
		return new BloonImune(metal, true, false);
	}

	public Bloon criarBarro() {
		// Cria um bloon barro imune a perfurantes, contendo pretos
		ComponenteVisual imagem = getImagem("barro");
		ComponenteVisual imagemPop = getImagemPop();
		// Imune a perfurantes
		BloonMultiCamada barro = new BloonMultiCamada(imagem, imagemPop, 3.5f, 4, 7);
		barro.addBloon(criarPreto());
		barro.addBloon(criarPreto());
		return new BloonImune(barro, true, false);
	}

	public Bloon criarPreto() {
		// Cria um bloon preto imune a explosões, contendo rosas
		ComponenteVisual imagem = getImagem("preto");
		ComponenteVisual imagemPop = getImagemPop();
		// Imune a explosões
		BloonMultiCamada black = new BloonMultiCamada(imagem, imagemPop, 4, 6, 7);
		black.addBloon(criarRosa());
		black.addBloon(criarRosa());
		return new BloonImune(black, false, true);
	}

	public Bloon criarBranco() {
		// Cria um bloon branco imune a explosões, contendo metais
		ComponenteVisual imagem = getImagem("branco");
		ComponenteVisual imagemPop = getImagemPop();
		// Imune a explosões
		BloonMultiCamada black = new BloonMultiCamada(imagem, imagemPop, 4, 6, 7);
		black.addBloon(criarMetal());
		black.addBloon(criarMetal());
		return new BloonImune(black, false, true);
	}

	public Bloon criarZeppelinAzul() {
		// Cria um zeppelin azul que produz vermelhos e azuis
		ComponenteVisual imagem = getImagem("zep_azul");
		ComponenteVisual imagemPop = getImagemPopZep();
		BloonFabricante azulZep = new BloonFabricante(imagem, imagemPop, 2f, 18, 12, 30);
		azulZep.addBloonProvavel(criarVermelho());
		azulZep.addBloonProvavel(criarAzul());
		return azulZep;
	}

	public Bloon criarZeppelinVerde() {
		// Cria um zeppelin verde que produz vermelhos, azuis e verdes
		ComponenteVisual imagem = getImagem("zep_verde");
		ComponenteVisual imagemPop = getImagemPopZep();
		BloonFabricante verdeZep = new BloonFabricante(imagem, imagemPop, 2.3f, 22, 20, 30);
		verdeZep.addBloonProvavel(criarVermelho());
		verdeZep.addBloonProvavel(criarAzul());
		verdeZep.addBloonProvavel(criarVerde());
		verdeZep.addBloonProvavel(criarVerde());
		return verdeZep;
	}

	public Bloon criarZeppelinAmarelo() {
		// Cria um zeppelin amarelo que produz azuis, verdes e amarelos
		ComponenteVisual imagem = getImagem("zep_amarelo");
		ComponenteVisual imagemPop = getImagemPopZep();
		BloonFabricante verdeZep = new BloonFabricante(imagem, imagemPop, 2.8f, 24, 25, 30);
		verdeZep.addBloonProvavel(criarAzul());
		verdeZep.addBloonProvavel(criarVerde());
		verdeZep.addBloonProvavel(criarAmarelo());
		return verdeZep;
	}

	public Bloon criarZeppelinRosa() {
		// Cria um zeppelin rosa que produz azuis, verdes, amarelos e rosas
		ComponenteVisual imagem = getImagem("zep_rosa");
		ComponenteVisual imagemPop = getImagemPopZep();
		BloonFabricante verdeZep = new BloonFabricante(imagem, imagemPop, 3.4f, 28, 30, 30);
		verdeZep.addBloonProvavel(criarAzul());
		verdeZep.addBloonProvavel(criarVerde());
		verdeZep.addBloonProvavel(criarAmarelo());
		verdeZep.addBloonProvavel(criarAmarelo());
		verdeZep.addBloonProvavel(criarRosa());
		verdeZep.addBloonProvavel(criarRosa());
		return verdeZep;
	}

	public Bloon criarZeppelinMetal() {
		// Cria um zeppelin metal imune a perfurantes, produzindo verdes e amarelos com armadura
		ComponenteVisual imagem = getImagem("zep_metal");
		ComponenteVisual imagemPop = getImagemPopZep();

		// Zeppelin metal imune a perfurantes
		BloonFabricante metalZepBase = new BloonFabricante(imagem, imagemPop, 2.3f, 30, 45, 30);
		Bloon metalZep = new BloonImune(metalZepBase, true, false);

		// verde e amarelo com armadura que rebenta ao fim de 8 contactos (8 de dano)
		Bloon verde = new BloonArmadura(criarVerde(), 8);
		metalZepBase.addBloonProvavel(verde);
		Bloon amarelo = new BloonArmadura(criarAmarelo(), 8);
		metalZepBase.addBloonProvavel(amarelo);
		return metalZep;
	}

	public Bloon criarZeppelinPreto() {
		// Cria um zeppelin preto imune a explosões, produzindo amarelos e rosas com escudo
		ComponenteVisual imagem = getImagem("zep_preto");
		ComponenteVisual imagemPop = getImagemPopZep();

		// Zeppelin preto imune a explosões
		BloonFabricante pretoZepBase = new BloonFabricante(imagem, imagemPop, 2.7f, 30, 45, 30);
		Bloon pretoZep = new BloonImune(pretoZepBase, false, true);

		// amarelo e rosa com escudo (armadura) que parte ao fim de 12 contactos
		Bloon amarelo = new BloonEscudo(criarAmarelo(), 12);
		pretoZepBase.addBloonProvavel(amarelo);
		Bloon rosa = new BloonEscudo(criarRosa(), 12);
		pretoZepBase.addBloonProvavel(rosa);
		return pretoZep;
	}
}
