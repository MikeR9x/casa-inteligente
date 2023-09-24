package bot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class RemoteLoader {
    private static Stack<Integer> commandHistory = new Stack<>();
    private static Timer delayTimer; 
    private static JLabel timerLabel;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createCombinedGUI();
            }
        });
    }

    private static void createCombinedGUI() {

        JFrame frame = new JFrame("Smart-House Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        frame.add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(50, 50, 50, 50);

        JPanel labelPanel = new JPanel(new GridLayout(1, 5));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        JPanel buttonPanel1 = new JPanel(new GridLayout(1, 2));

        String[] buttonNames = {
            "Luz Cocina", "Luz Recámara", "Luz Sala", "Luz Baño", "Luz Garaje"
        };

        String[] labelNames = {
            "Cocina", "Recámara", "Sala", "Baño", "Garaje"
        };

        for (int i = 0; i < 5; i++) {
            int num = i;
            JButton button = new JButton(buttonNames[i]);
            button.setPreferredSize(new Dimension(120, 30));
            button.setOpaque(true);

            JButton gridButton = new JButton(labelNames[i]);
            gridButton.setPreferredSize(new Dimension(120, 30));
            gridButton.setOpaque(true);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (delayTimer == null || !delayTimer.isRunning()) {
                        Color currentColor = gridButton.getBackground();
                        if (currentColor.equals(Color.YELLOW)) {
                            gridButton.setBackground(Color.WHITE);
                            ButtonWasPushed(num);
                        } else  if (currentColor.equals(Color.white)){
                            gridButton.setBackground(Color.YELLOW);
                            ButtonWasPushed(num);
                        }else {
                            return;
                        }
                        delayTimer = new Timer(1000, new ActionListener() {
                            int count = 10; 
                            public void actionPerformed(ActionEvent e) {
                                if (count > 0) {
                                    timerLabel.setText("Tiempo restante: " + count + " segundos");
                                    count--;
                                } else {
                                    timerLabel.setText(""); 
                                    delayTimer.stop(); 
                                }
                            }
                        });
                        delayTimer.setRepeats(true); 
                        delayTimer.start();
                    }
                }
            });

            labelPanel.add(gridButton);
            buttonPanel.add(button);
        }

        JButton undoButton = new JButton("Undo");
        undoButton.setPreferredSize(new Dimension(80, 30));
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!commandHistory.isEmpty()) {
                    int lastIndex = commandHistory.peek();
                    if (labelPanel.getComponent(lastIndex).getBackground() == Color.WHITE) {
                        labelPanel.getComponent(lastIndex).setBackground(Color.YELLOW);
                    } else {
                        labelPanel.getComponent(lastIndex).setBackground(Color.WHITE);
                    }
                    commandHistory.pop();
                    if (delayTimer != null && delayTimer.isRunning()) {
                        delayTimer.stop();
                        timerLabel.setText("Tiempo restante: 0 segundos");
                    }
                }
            }
        });

        JButton vacationButton = new JButton("Modo de vacaciones");
        vacationButton.setPreferredSize(new Dimension(160, 30));
        vacationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                commandHistory.clear(); 
            }
        });

        JButton outButton = new JButton("Fuera de Casa");
        outButton.setPreferredSize(new Dimension(160, 30));
        outButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                labelPanel.getComponent(0).setBackground(Color.black);
                labelPanel.getComponent(1).setBackground(Color.black);
                labelPanel.getComponent(2).setBackground(Color.black);
                labelPanel.getComponent(3).setBackground(Color.black);
                labelPanel.getComponent(4).setBackground(Color.black);
                commandHistory.clear();
            }
        });

        JButton welcomeButton = new JButton("Activar la  Casa");
        welcomeButton.setPreferredSize(new Dimension(160, 30));
        welcomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                labelPanel.getComponent(0).setBackground(Color.white);
                labelPanel.getComponent(1).setBackground(Color.white);
                labelPanel.getComponent(2).setBackground(Color.white);
                labelPanel.getComponent(3).setBackground(Color.white);
                labelPanel.getComponent(4).setBackground(Color.white);
                commandHistory.clear();
            }
        });
        
        buttonPanel1.add(undoButton);
        buttonPanel1.add(vacationButton);
        buttonPanel1.add(outButton);
        buttonPanel1.add(welcomeButton);

        timerLabel = new JLabel(); 
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(timerLabel, gbc);

        frame.setResizable(false);
        frame.setVisible(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(labelPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(buttonPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(buttonPanel1, gbc);
        
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void ButtonWasPushed(int slot) {
        commandHistory.push(slot);
    }
}
