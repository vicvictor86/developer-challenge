package src.managers;

import src.Game;
import src.JogoDosOito;
import src.screens.Welcome;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class ScreenManager implements ActionListener {
    private GameManager gameManager;
    private final HashMap<String, JFrame> screens = new HashMap<>();
    public ScreenManager() {
        screens.put("Welcome", new Welcome(this));
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();
        JFrame welcome = screens.get("Welcome");

        switch (buttonText) {
            case "Começar":
                System.out.println("Ir para tela de jogo");

                welcome.setVisible(false);
                int boardSize = ((Welcome)welcome).getOptionSelected();
                this.gameManager = new GameManager(boardSize);

                screens.put("PlayGame", new JogoDosOito(this));
                break;
            case "Voltar":
                System.out.println("Voltar pra tela inicial");
                screens.get("PlayGame").dispose();
                welcome.setVisible(true);
                break;
            default:
                System.out.println("Default");
                break;
        }
    }
}
