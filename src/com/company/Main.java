package com.company;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem pauseGreenMenuItem;
    private JMenuItem resumeMenuItem;
// Поле, по которому прыгают мячи

    private Field field = new Field();
    // Конструктор главного окна приложения
    public Main() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // Отцентрировать окно приложения на экране

        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);
        // Установить начальное состояние окна развѐрнутым на весь экран
        setExtendedState(MAXIMIZED_BOTH);
        // Создать меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() &&
                        !resumeMenuItem.isEnabled() &&
                        !pauseGreenMenuItem.isEnabled())
                // Ни один из пунктов меню не являются
// доступными - сделать доступным "Паузу"

                {
                    pauseMenuItem.setEnabled(true);
                    pauseGreenMenuItem.setEnabled(true);

                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);

        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение") {
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                pauseGreenMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);

        Action pauseActionGreen = new AbstractAction("Приостановить движение зеленых шаров") {
            public void actionPerformed(ActionEvent event) {
                field.pauseGreen();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseGreenMenuItem = controlMenu.add(pauseActionGreen);
        pauseGreenMenuItem.setEnabled(false);

        Action resumeAction = new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                pauseGreenMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);
        getContentPane().add(field, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}