import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class RemoteLoader {
    static Command[] onCommands1;
	static Command[] offCommands1;
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createCombinedGUI(remoteControl);
            }
        });
    }

    private static void createCombinedGUI(RemoteControl remoteControl) {

        CommandsStack commandStack = new CommandsStack();
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
        Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen");
		CeilingFan ceilingFan= new CeilingFan("Living Room");
		GarageDoor garageDoor = new GarageDoor("");
		Stereo stereo = new Stereo("Living Room");
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        
        onCommands1 = new Command[7];
		offCommands1 = new Command[7];

        onCommands1[0] = livingRoomLightOn;
		LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        offCommands1[0] = livingRoomLightOff;

		LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        onCommands1[1] = kitchenLightOn;
		LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);
        offCommands1[1] = kitchenLightOff;
        
		CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
        onCommands1[2] = ceilingFanOn;
		CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);
        offCommands1[0] = ceilingFanOff;
        
		GarageDoorUpCommand garageDoorUp =new GarageDoorUpCommand(garageDoor);
        onCommands1[3] = garageDoorUp;
		GarageDoorDownCommand garageDoorDown =new GarageDoorDownCommand(garageDoor);
        offCommands1[0] = garageDoorDown;
        
		StereoOnWithCDCommand stereoOnWithCD =new StereoOnWithCDCommand(stereo);
        onCommands1[4] = stereoOnWithCD;
		StereoOffCommand  stereoOff =new StereoOffCommand(stereo);
        offCommands1[4] = stereoOff;

        for (int i = 0; i < 5; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setPreferredSize(new Dimension(120, 30));
            button.setOpaque(true);

            JButton gridButton = new JButton(buttonNames[i]);
            gridButton.setPreferredSize(new Dimension(120, 30));
            gridButton.setOpaque(true);

            final int buttonIndex = i;

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color currentColor = gridButton.getBackground();
                    if (currentColor.equals(Color.YELLOW)) {
                        gridButton.setBackground(Color.WHITE);
                    } else {
                        gridButton.setBackground(Color.YELLOW);
                    }
                    remoteControl.onButtonWasPushed(buttonIndex);
                    remoteControl.setCommand(buttonIndex, onCommands1[buttonIndex], offCommands1[buttonIndex]);
                    commandStack.push(remoteControl.onCommands[buttonIndex]);
                    System.out.println(commandStack);
                }
            });

            gridButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color currentColor = gridButton.getBackground();
                    if (currentColor.equals(Color.YELLOW)) {
                        gridButton.setBackground(Color.WHITE);
                    } else {
                        gridButton.setBackground(Color.YELLOW);
                    }
                    remoteControl.onButtonWasPushed(buttonIndex);
                }
            });

            labelPanel.add(gridButton);
            buttonPanel.add(button);
        }
        JButton undoButton = new JButton("Undo");
        undoButton.setPreferredSize(new Dimension(80, 30));
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                commandStack.pop().undo();
                System.out.println(commandStack);
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

}
