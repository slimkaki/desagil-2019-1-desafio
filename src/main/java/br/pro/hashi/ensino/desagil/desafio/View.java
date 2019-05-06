package br.pro.hashi.ensino.desagil.desafio;

import br.pro.hashi.ensino.desagil.desafio.model.Board;
import br.pro.hashi.ensino.desagil.desafio.model.Element;
import br.pro.hashi.ensino.desagil.desafio.model.Model;
import br.pro.hashi.ensino.desagil.desafio.model.Player;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;

// Estender a classe JPanel e reescrever o método
// paintComponent é um jeito tradicional de criar
// uma componente cujo visual você mesmo inventa.
public class View extends JPanel {

    // Constante que representa o tamanho,
    // em pixels, da célula do tabuleiro.
    private static final int CELL_SIZE = 50;


    private final Model model;
    private final Map<Element, Image> elementsToImages;


    public View(Model model) {
        this.model = model;

        // Esse jeito de construir um dicionário só pode
        // ser usado se você não pretende mudá-lo depois.
        elementsToImages = Map.of(
                model.getTarget(), getImage("target.png"),
                model.getHumanPlayer(), getImage("human-player.png"),
                model.getCpuPlayer(), getImage("cpu-player.png")
        );

        Board board = model.getBoard();

        int width = board.getNumCols() * CELL_SIZE;
        int height = board.getNumRows() * CELL_SIZE;

        // Define o tamanho da componente a partir do
        // tamanho do tabuleiro e da constante acima.
        setPreferredSize(new Dimension(width, height));
    }


    // Método que desenha a interface gráfica do jogo. A ideia é simples: O objeto g é como
    // um pincel que desenha o que você mandar ele desenhar. Para saber o que é possível, veja
    // https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/Graphics.html.
    // Você nunca deve chamar esse método diretamente. O certo é chamar o método repaint.
    @Override
    public void paintComponent(Graphics g) {
        Board board = model.getBoard();

        for (int i = 0; i < board.getNumRows(); i++) {
            for (int j = 0; j < board.getNumCols(); j++) {
                if (board.isWall(i, j)) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        elementsToImages.forEach((element, image) -> {
            int row = element.getRow();
            int col = element.getCol();

            g.drawImage(image, col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, this);
        });

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }


    // Método de conveniência que carrega uma imagem a partir de um nome
    // de arquivo. Espera-se que esse arquivo esteja em src/main/resources.
    private Image getImage(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        return getToolkit().getImage(url);
    }

    public void drawWinner(Graphics g, Player winner) {
        if (model.getWinner() == model.getHumanPlayer()) {
            g.drawString("Human Player Wins!", 0, 0);
        } else {
            g.drawString("CPU Player Wins!", 0, 0);
        }
    }
}
