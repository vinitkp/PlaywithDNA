import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SaveBrickDataListener implements ActionListener {

    JFileChooser jFileChooser;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(MainFrame.newBrickCreated) {
            jFileChooser = new JFileChooser();
            int returnVal = jFileChooser.showSaveDialog(null);
            File file = jFileChooser.getSelectedFile();
            BufferedWriter bufferedWriter = null;
            MainFrame.saveBrickFunctionCalled = true;
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath() + ".csv"));
                    bufferedWriter.write(MainFrame.dnaBrick.toString());
                    bufferedWriter.close();

                    // TODO: Add some few more lines to the Output File

                    JOptionPane.showMessageDialog(null, "File Saved Successfully !",
                            "Success!", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "The File could not be Saved!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No Brick has been created. Use 'New' to create a Brick first",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
}
