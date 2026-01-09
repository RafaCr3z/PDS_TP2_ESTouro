package io;

import java.io.*;
import java.util.List;

import torre.*;
import mundo.Mundo;

/**
 * Classe responsável pela gravação dos ficheiros de jogo
 */
public class GameWriter {

	/**
	 * grava o jogo no seu estado atual
	 * 
	 * @param nomeFicheiro ficheiro onde guardar o jogo
	 * @param round        nível a que diz respeito o jogo gravado
	 * @param dinheiro     quanto dinheiro se tem
	 * @param vidas        as vidas que existiam
	 * @param m            o mundo tal como estava
	 * @throws IOException quando acontece algum erro de gravação
	 */
	public static void gravarJogo(String nomeFicheiro, int pista, int round, int dinheiro, int vidas, Mundo m)
			throws IOException {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(nomeFicheiro)))) {
			out.println(pista);
			out.println(round);
			out.println(dinheiro);
			out.println(vidas);

			List<Torre> torres = m.getTorres();
			out.println(torres.size());
			// guardar as torres
			GameWriterVisitor v = new GameWriterVisitor(out);
			// percorrer a lista de torres e guardar cada uma
			for (Torre t : torres) {
				t.accept(v);
			}
		}
	}
}
