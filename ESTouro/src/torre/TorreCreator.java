package torre;

import java.awt.image.BufferedImage;

import prof.jogos2D.util.ImageLoader;

/**
 * Classe que trata da criação das várias torres. Esta classe existe que lidar
 * com a criação de todas as torres e assim impedir que as outras classes tenham
 * de ser alteradas quando se criam novas torres.
 */
public class TorreCreator {
	private java.util.Map<String, java.util.function.Supplier<Torre>> fabrica = new java.util.HashMap<>();
	private ImageLoader loader = ImageLoader.getLoader();

	public TorreCreator() {
		fabrica.put("macaco", this::criarMacaco);
		fabrica.put("octo", this::criarOctogonal);
		fabrica.put("canhao", this::criarCanhao);
		fabrica.put("morteiro", this::criarMorteiro);
		fabrica.put("balista", this::criarBalista);
		fabrica.put("ninja", this::criarNinja);
		fabrica.put("sniper", this::criarSniper);
	}

	/**
	 * cria a torre com um dado nome
	 * 
	 */
	public Torre criarTorrePorNome(String nome) {
		if (fabrica.containsKey(nome))
			return fabrica.get(nome).get();
		return null;
	}

	/** Cria uma torre sniper */
	public Torre criarSniper() {
		// Reusing macaco image if sniper image doesn't exist, but attempting sniper
		// path
		java.awt.Image img = loader.getImage("data/torres/sniper/imagem.gif");
		if (img == null)
			img = loader.getImage("data/torres/macaco/imagem.gif");
		return new TorreSniper((BufferedImage) img);
	}

	public Torre criarOctogonal() {
		java.awt.Image img = loader.getImage("data/torres/octo/imagem.gif");
		return new TorreOctogonal((BufferedImage) img);
	}

	/** Cria uma torre macaco */
	public Torre criarMacaco() {
		java.awt.Image img = loader.getImage("data/torres/macaco/imagem.gif");
		return new TorreMacaco((BufferedImage) img);
	}

	/** Cria uma torre canhão */
	public Torre criarCanhao() {
		java.awt.Image img = loader.getImage("data/torres/canhao/imagem.gif");
		return new TorreCanhao((BufferedImage) img);
	}

	/** Cria uma torre morteiro */
	public Torre criarMorteiro() {
		java.awt.Image img = loader.getImage("data/torres/morteiro/imagem.gif");
		return new TorreMorteiro((BufferedImage) img);
	}

	/** Cria uma torre balista */
	public Torre criarBalista() {
		java.awt.Image img = loader.getImage("data/torres/balista/imagem.gif");
		return new TorreBalista((BufferedImage) img);
	}

	/** Cria uma torre ninja */
	public Torre criarNinja() {
		java.awt.Image img = loader.getImage("data/torres/ninja/imagem.gif");
		return new TorreNinja((BufferedImage) img);
	}

}
