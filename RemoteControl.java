
//
// This is the invoker
//

import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JLabel;

public class RemoteControl {
	Command[] onCommands;
	Command[] offCommands;
	private Stack<Command> commandHistory = new Stack<>();
 
	////Constructor de la clase RemoteControl
	public RemoteControl() {
		onCommands = new Command[7];
		offCommands = new Command[7];
 
		Command noCommand = new NoCommand();
		for (int i = 0; i < 7; i++) {
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
	}
  
	//Método setCommand para asignar un comando a un botón
	public void setCommand(int slot, Command onCommand, Command offCommand) {
		onCommands[slot] = onCommand;
		offCommands[slot] = offCommand;
	}
 
	//Método onButtonWasPushed para ejecutar el comando de encendido
	public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        commandHistory.push(onCommands[slot]);
    }
 
	//Método offButtonWasPushed para ejecutar el comando de apagado
	public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        commandHistory.push(offCommands[slot]);
    }
  
	//Método toString para imprimir el contenido del control remoto
	public String toString() {
		StringBuffer stringBuff = new StringBuffer();
		stringBuff.append("\n------ Remote Control -------\n");
		for (int i = 0; i < onCommands.length; i++) {
			stringBuff.append("[slot " + i + "] " + onCommands[i].getClass().getName()
				+ "    " + offCommands[i].getClass().getName() + "\n");
		}
		return stringBuff.toString();
	}

    public void setGridLabels(JLabel[] labels) {
    }

    public void setGridButtons(JButton[] gridButtons) {
    }

    public void undoButtonWasPushed() {
    }

    

	
}
