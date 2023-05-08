package src.screens;

import src.managers.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Welcome extends JFrame implements KeyListener {
    private JComboBox<Integer> sizeOptions;

    public Welcome(ScreenManager screenManager) {
        super("Welcome");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel welcomelabel = new JLabel("Bem vindo ao jogo dos oito");
        welcomelabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(welcomelabel, c);

        JLabel boardSizeLabel = new JLabel("Tamanho do jogo");
        c.weightx = 0.75;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        add(boardSizeLabel, c);

        Integer[] sizeOptions = {3, 4, 5, 6, 7, 8, 9};
        this.sizeOptions = new JComboBox<>(sizeOptions);

        c.weightx = 0.25;
        c.gridx = 1;
        c.gridy = 1;
        add(this.sizeOptions, c);

        JButton startButton = new JButton();
        startButton.setText("Começar");
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.addActionListener(screenManager);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        add(startButton, c);

        addKeyListener(this);
        setFocusable(true);

        setVisible(true);
    }

    public Integer getOptionSelected() {
        return Integer.valueOf(String.valueOf(sizeOptions.getSelectedItem()));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
