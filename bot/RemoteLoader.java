package bot;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class RemoteLoader {
    private static Stack<Integer> commandHistory = new Stack<>();
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createCombinedGUI();
            }
        });
    }

    private static void createCombinedGUI() {

        JFrame frame = new JFrame("Combined Interface");
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

        String[] buttonNames = {
            "Luz Cocina", "Luz Recámara", "Luz Sala", "Luz Baño", "Luz Garaje"
        };
        
        for (int i = 0; i < 5; i++) {
            int num = i;
            JButton button = new JButton(buttonNames[i]);
            button.setPreferredSize(new Dimension(120, 30));
            button.setOpaque(true);
            
            JButton gridButton = new JButton(buttonNames[i]);
            gridButton.setPreferredSize(new Dimension(120, 30));
            gridButton.setOpaque(true);
            
            button.addActionListener(new ActionListener() {
                
                public void actionPerformed(ActionEvent e) {
                    Color currentColor = gridButton.getBackground();
                    if (currentColor.equals(Color.YELLOW)) {
                        gridButton.setBackground(Color.WHITE);
                        ButtonWasPushed(num);
                        System.out.println(commandHistory.peek());
                    } else {
                        gridButton.setBackground(Color.YELLOW);
                        ButtonWasPushed(num);
                        System.out.println(commandHistory.peek());
                        
                    }
                }
            });

            gridButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color currentColor = gridButton.getBackground();
                    if (currentColor.equals(Color.YELLOW)) {
                        //gridButton.setBackground(Color.WHITE);
                        
                    } else {
                        //gridButton.setBackground(Color.YELLOW);
                        
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
                System.out.println(labelPanel.getComponent(1).getBackground());
                System.out.println(labelPanel.getComponent(0).getName());
                if (labelPanel.getComponent(commandHistory.peek()).getBackground() == Color.white) {
                labelPanel.getComponent(commandHistory.peek()).setBackground(Color.yellow);
                }else{
                    labelPanel.getComponent(commandHistory.peek()).setBackground(Color.white);
                }
                commandHistory.pop();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(undoButton, gbc);

        frame.setResizable(false);
        frame.setVisible(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(labelPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(buttonPanel, gbc);

        frame.setResizable(false);
        frame.setVisible(true);

    }

    public static void ButtonWasPushed(int slot) {
        commandHistory.push(slot);
    }
}
