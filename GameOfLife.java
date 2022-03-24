package life;

import javax.swing.*;
import java.awt.*;
import static java.lang.Thread.sleep;

public class GameOfLife extends JFrame {

    private JLabel generationLabel;
    private JLabel aliveLabel;
    private JPanel grid;
    private JButton resetButton;
    private JToggleButton pauseButton;
    private World world;

    public GameOfLife() {
        super();
        setTitle("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1240,1060);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(0, 18, 25));
        world = new World(100,100);
        initializeComponents();
        setVisible(true);
        Thread cycle = new Thread(() -> {
            while(true) {
                if(pauseButton.isSelected()) {
                    world.nextGen();
                    generationLabel.setText("Generation #" + world.getGeneration());
                    aliveLabel.setText("Alive: " + world.getPopulation());
                    grid.repaint();
                }
                try {
                    sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        cycle.setDaemon(true);
        cycle.start();
    }

    private void initializeComponents() {
        pauseButton = new JToggleButton();
        pauseButton.setName("PlayToggleButton");
        pauseButton.setBounds(10,10,60,40);
        pauseButton.setBackground(new Color(10, 147, 150));
        pauseButton.setIcon(new ImageIcon("C:\\Users\\Kacper\\Desktop\\pause.png"));

        resetButton = new JButton();
        resetButton.setName("ResetButton");
        resetButton.setBounds(80,10,60,40);
        resetButton.setIcon(new ImageIcon("C:\\Users\\Kacper\\Desktop\\rotate-right.png"));
        resetButton.setBackground(new Color(10, 147, 150));
        resetButton.addActionListener(e -> {
            world = new World(100,100);
        });

        generationLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        generationLabel.setBounds(10,60,100,20);
        generationLabel.setForeground(new Color(10, 147, 150));
        generationLabel.setText("Generation #" + world.getGeneration());

        aliveLabel = new JLabel();
        aliveLabel.setName("AliveLabel");
        aliveLabel.setBounds(10,80,100,20);
        aliveLabel.setForeground(new Color(10, 147, 150));
        aliveLabel.setText("Alive: " + world.getPopulation());

        grid = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                boolean[][] state = world.getState();
                for (int i = 0; i < state.length; i++) {
                    for (int j = 0; j < state[0].length; j++) {
                        if(state[i][j]) {
                            g.setColor(new Color(0, 18, 25));
                            g.fillRect(j * 10, i * 10, 10, 10);
                        } else {
                            g.setColor(new Color(10, 147, 150));
                            g.fillRect(j * 10, i * 10, 10, 10);
                        }
                    }
                }
            }
        };
        grid.setBounds(220,10,1000,1000);
        grid.setLayout(null);

        add(pauseButton);
        add(resetButton);
        add(generationLabel);
        add(aliveLabel);
        add(grid);
        grid.repaint();
    }
}
