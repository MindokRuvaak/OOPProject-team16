package oopp.team16.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
public class StartView {

    private JFrame frame = new JFrame();
    private BunAction bunAc;

     public StartView(){
        this.bunAc = new BunAction();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setTitle("UNO - Starting screen");
        frame.setSize(540,300);

        frame.add(bunAc.startButton);
        bunAc.startButton.setBounds(100,100,100,100);
        bunAc.startButton.setFocusable(false);
     }

    public void hide() {
        frame.setVisible(false);
    }
    public void show(){
         frame.setVisible(true);
    }


}
