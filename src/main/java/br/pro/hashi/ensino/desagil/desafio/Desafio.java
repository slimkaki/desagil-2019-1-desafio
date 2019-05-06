package br.pro.hashi.ensino.desagil.desafio;

import br.pro.hashi.ensino.desagil.desafio.model.HumanPlayer;
import br.pro.hashi.ensino.desagil.desafio.model.Model;
import br.pro.hashi.ensino.desagil.desafio.model.Player;

import javax.swing.*;
import java.awt.*;

public class Desafio {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);

        // Estrutura básica de um programa Swing.
        SwingUtilities.invokeLater(() -> {

            // Constrói a janela.
            JFrame frame = new JFrame();

            // Adiciona a visão à janela.
            frame.setContentPane(view);

            // Adiciona o controlador à lista de observadores
            // das ações de teclado detectadas pela janela.
            frame.addKeyListener(controller);

            // Configura a janela para encerrar o programa quando for fechada.
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Configura a janela para não ser manualmente redimensionável.
            frame.setResizable(false);

            // Redimensiona a janela de acordo com o tamanho de seu conteúdo.
            frame.pack();

            // Exibe a janela.
            frame.setVisible(true);

            // Constrói um relógio de 100 milissegundos e adiciona
            // o controlador à lista de observadores desse relógio.
            Timer timer = new Timer(100, controller);

            // Inicia o relógio.
            timer.start();
        });
        if (model.getWinner() != null) {
            view.drawWinner(Graphics g,model.getWinner());
        }
    }
}
