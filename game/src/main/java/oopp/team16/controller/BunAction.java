package oopp.team16.controller;

//import oopp.team16.view.GameView;

import javax.swing.*;
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
    public JButton nameButton = new JButton("Name");
    public JButton leaveButton = new JButton("Leave");
    public  JButton unoButton = new JButton("UNO");
    public JTextField portNumber = new JTextField("Enter the port");
    public JTextField name = new JTextField("Enter your name");

    private UnoController unoCC;
    public BunAction(UnoController unoCC){
        this.unoCC = unoCC;
        addActionListener();
    }

    private void addActionListener() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //GameView gameView = new GameView();
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


        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.rules();}
        });


        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.drawButton();}
        });
        playCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.playCardButton();}
        });
        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {unoCC.leaveGame();}
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

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameGet = name.getName();
                unoCC.gameName(nameGet);
            }
        });
    }


    // Makes the string only and only 5 letters
    private boolean isPortValid(String port){
        return port.matches("\\d{5}");
    }
}
