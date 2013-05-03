import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClearActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(MainFrame.currentWindow == 2) { // Free Style Grid
			FreeGridActionListener.dnaBricks.clear();
			FreeGridActionListener.canvas.setBackground(Color.white);
			FreeGridActionListener.canvas.pressed = 2;
			FreeGridActionListener.canvas.repaint();

			FreeGridActionListener.isToBeSaved = false; // Reset Save Flag
		}
		
		if(MainFrame.currentWindow == 3) { // Digital Grid
			DigitalGridActionListener.dnaDigitalTiles.clear();
			DigitalGridActionListener.canvas.setBackground(Color.white);
			DigitalGridActionListener.canvas.pressed = 2;
			DigitalGridActionListener.canvas.repaint();

			DigitalGridActionListener.isToBeSaved = false; // Reset Save Flag
		}
    }
}
