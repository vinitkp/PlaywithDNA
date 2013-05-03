import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;


public class SaveItemActionListener implements ActionListener {

	JFileChooser jFileChooser;

    @Override
    public void actionPerformed(ActionEvent e) {
		jFileChooser = new JFileChooser();
          
        int returnVal = jFileChooser.showSaveDialog(null);
        File file = jFileChooser.getSelectedFile();
        BufferedWriter bufferedWriter = null;
        if (returnVal == JFileChooser.APPROVE_OPTION)  
        {  
        	if (MainFrame.currentWindow == 2) {
                // Checks if you're calling this function from Free Draw Grid
                // And there are changes to be saved.
        		try  
        		{
                    FreeGridActionListener.savedFunctionCalled = true;
        			ArrayList<DNABrick> dnaBrickList = FreeGridActionListener.dnaBricks;
        			bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath() + ".csv"));
        			
        			for (int i = 0; i < dnaBrickList.size(); i++) {
        				bufferedWriter.write(dnaBrickList.get(i).toString());
        				bufferedWriter.write("\n");
        			}

                    // TODO: Don't simply output the Sequences. Add some more lines to the Excel File.

        			bufferedWriter.close();
        			JOptionPane.showMessageDialog(null, "File Saved Successfully !",
                        "Success!", JOptionPane.INFORMATION_MESSAGE);  
        		}  
        		catch (IOException e1)  
        		{  
        			JOptionPane.showMessageDialog(null, "The File could not be Saved!",  
                        "Error!", JOptionPane.INFORMATION_MESSAGE);  
        		}
        	} else if (MainFrame.currentWindow == 3) {
        		try {
                    DigitalGridActionListener.savedFunctionCalled = true;
                    ArrayList<DNABrick> dnaDigitalTiles = DigitalGridActionListener.dnaDigitalTiles;
                    bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath() + ".csv"));

                    for (int i = 0; i < dnaDigitalTiles.size(); i++) {
                        bufferedWriter.write(dnaDigitalTiles.get(i).toString());
                        bufferedWriter.write("\n");
                    }

                    // TODO: Don't simply output the Sequences. Add some more lines to the Excel File.

                    bufferedWriter.close();
                    JOptionPane.showMessageDialog(null, "File Saved Successfully !",
                            "Success!", JOptionPane.INFORMATION_MESSAGE);
        		}  
        		catch (IOException e1)  
        		{  
        			JOptionPane.showMessageDialog(null, "The File could not be Saved!",  
                        "Error!", JOptionPane.INFORMATION_MESSAGE);  
        		}
        	} else if (MainFrame.currentWindow == 1) {
        		try {
        			bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile() + ".csv"));
        			bufferedWriter.write(MainFrame.dnaBrick.toString());
        			bufferedWriter.write("\n\n");

                    // TODO: Add some more lines to Output File
        			bufferedWriter.close();
        			
        			JOptionPane.showMessageDialog(null, "The file has been Saved Successfully!",  
	                        "Success!", JOptionPane.INFORMATION_MESSAGE);
        		} catch (Exception e1) {
        			JOptionPane.showMessageDialog(null, "The File could not be Saved!",  
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
        		}
        	}
        }
	}
}
