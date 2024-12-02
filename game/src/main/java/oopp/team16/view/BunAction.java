package oopp.team16.view;

import javax.swing.*;

import oopp.team16.controller.UnoController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BunAction {
    public JButton startButton = new JButton("Start");
    public JButton playButton = new JButton("Play");
    public JButton exitButton = new JButton("Exit");
    public JButton connectButton = new JButton("Connect");
    public JButton rulesButton = new JButton("UNO rules");
    public JButton drawButton = new JButton("Draw card");
    public JButton playCardButton = new JButton("Play a Card");
    public JTextField portNumber = new JTextField("Enter the port");
    private UnoController unoCC;
    public BunAction(){
    //    this.unoCC = unoCC;
        addActionListener();
    }

    private void addActionListener() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameView gameView = new GameView();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.playGame();}
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.exitGame();}
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String port = portNumber.getText();
                if(isPortValid(port)){
                    unoCC.connectGame(Integer.parseInt(port));
                }
                else System.out.println("Error, Write a 5 digit code");
            }
        });

        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.rules();}
        });

        playCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.playCardButton();}
        });

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.drawButton();}
        });
    }


    // Makes the string only and only 5 letters
    private boolean isPortValid(String port){
        return port.matches("\\d{5}");
    }

}
