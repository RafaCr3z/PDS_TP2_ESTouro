package game;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.LinkedHashMap;
import torre.Torre;
import torre.estrategia.*;

import javax.swing.border.TitledBorder;

/**
 * Classe que define o painel de escolha do modo de ataque
 */
public class ConfiguradorTorres extends JPanel {

	private static final long serialVersionUID = 1L;

	// botões usados para escolher o modo de ataque
	private ButtonGroup btGrp = new ButtonGroup();
	
	// torre que está a ser controlada por este painel
	private Torre escolhida;

	/** mapeia os modos de ataque ao respetivo botão */
	private LinkedHashMap<String, JToggleButton> botoes = new LinkedHashMap<>();

	/**
	 * Cria os vários botões para os vários modos de ataque
	 */
	private void criarBotoesAtaques(JPanel painelAtaques) {
		// cria os botões para os modos de ataque
		painelAtaques.add(criarBotaoAtaque(new EstrategiaPrimeiro()));
		painelAtaques.add(criarBotaoAtaque(new EstrategiaUltimo()));
		painelAtaques.add(criarBotaoAtaque(new EstrategiaPerto()));
		painelAtaques.add(criarBotaoAtaque(new EstrategiaLonge()));
		painelAtaques.add(criarBotaoAtaque(new EstrategiaForte()));
		painelAtaques.add(criarBotaoAtaque(new EstrategiaJuntos()));
	}

	/**
	 * método utilizado para definir qual a torre escolhida, ou seja, qual a que
	 * está a ser modificada
	 * 
	 * @param t a torre a ser modificada
	 */
	public void setSelecionada(Torre t) {
		escolhida = t;

		JToggleButton bt = botoes.get(t.getEstrategia().getNome());
		if (bt != null)
			bt.setSelected(true);
		else if (!botoes.isEmpty())
			botoes.values().iterator().next().setSelected(true);
	}

	/**
	 * Cria um botão para um modo de ataque
	 * 
	 * @param texto      o texto a colocar no botão
	 * @param modoAtaque o modo de ataque
	 * @return o botão criado
	 */
	private JToggleButton criarBotaoAtaque(EstrategiaAtaque est) {
		JToggleButton button = new JToggleButton(est.getNome());
		button.setPreferredSize(new Dimension(60, 18));
		button.setMargin(new Insets(0, 0, 0, 0));
		button.addActionListener(e -> {
			if (escolhida != null) {
				escolhida.setEstrategia(est);
			}
		});
		btGrp.add(button);
		botoes.put(est.getNome(), button);
		return button;
	}

	/**
	 * cria o configurador
	 */
	public ConfiguradorTorres() {
		super();
		setupInterface();
	}

	/**
	 * Prepara a zona de interface
	 */
	private void setupInterface() {
		JPanel painelAtaques = new JPanel();
		painelAtaques.setBorder(new TitledBorder("Modo de Ataque"));
		painelAtaques.setBounds(new Rectangle(2, 5, 145, 90));
		criarBotoesAtaques(painelAtaques);
		this.setLayout(null);
		this.setBounds(new Rectangle(0, 0, 140, 200));
		this.add(painelAtaques);
	}
}
