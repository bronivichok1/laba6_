package com.company;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Field extends JPanel {

    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
// При создании его экземпляра используется анонимный класс,
// реализующий интерфейс ActionListener

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            Field.this.repaint();
        }
    });

    public Field(){
        setBackground(Color.BLACK);
        repaintTimer.start();
    }

    public void addBall(){
        //Заключается в добавлении в список нового экземпляра BouncingBall
// Всю инициализацию положения, скорости, размера, цвета
// BouncingBall выполняет сам в конструкторе

        balls.add(new BouncingBall(this));
    }

    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D canvas = (Graphics2D) gr;
        for (BouncingBall ball: balls) {
            ball.paint(canvas);
        }
    }

    public synchronized void pause() {
        for (BouncingBall ball: balls){
            ball.setPaused();
        }
    }
    public synchronized void resume() {
        for (BouncingBall ball: balls){
            ball.resumePaused();
            notify();
        }
    }
    public synchronized void pauseGreen() {
        for (BouncingBall ball: balls) {
            if(ball.getColor().getGreen() >= 2*(ball.getColor().getBlue() + ball.getColor().getRed())) {
                ball.setPaused();
            }
        }
    }
    public synchronized void canMove(BouncingBall ball)
            throws InterruptedException{
        if (ball.isPaused()) {
            // Если режим паузы включен, то поток, зашедший
// внутрь данного метода, засыпает
            wait();
        }
    }

}