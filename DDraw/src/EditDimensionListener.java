import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class EditDimensionListener implements ActionListener {

    JFrame brickDimInputFrame;
    JTextField inputHeight;
    JTextField inputWidth;

    public void showInputDialogue() {
        brickDimInputFrame = new JFrame("Input Brick Dimensions");
        brickDimInputFrame.setSize(400, 150);

        JLabel jLabelDimension = new JLabel("Input Brick Dimensions");
        JLabel width = new JLabel("Width: ");
        JLabel height= new JLabel("Height: ");

        inputHeight = new JTextField();
        inputHeight.setText("3"); // Set default value to 3

        inputWidth = new JTextField();
        inputWidth.setText("7"); // Set default value to 7

        brickDimInputFrame.setLayout(new GridLayout(3, 1));
        brickDimInputFrame.add(jLabelDimension);
        JPanel jPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        JButton submitButton = new JButton("Submit");
        jPanel1.add(submitButton);
        jPanel.setLayout(new GridLayout(1, 4));
        jPanel.add(width);
        jPanel.add(inputWidth);
        jPanel.add(height);
        jPanel.add(inputHeight);
        brickDimInputFrame.add(jPanel);
        brickDimInputFrame.add(jPanel1);
        brickDimInputFrame.setVisible(true);
        brickDimInputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submitButton.addActionListener(new SubmitActionListener());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (MainFrame.currentWindow == 3) {
            JOptionPane.showMessageDialog(null, "Function Unavailable for Digital Grid.",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!MainFrame.newBrickCreated) {
            JOptionPane.showMessageDialog(null, "No Brick has been created. Use 'New' to create a Brick first",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            if (MainFrame.currentWindow == 1) {
                Object[] options = {"Yes",
                        "No",
                        "Cancel"};

                int userChoice = JOptionPane.showOptionDialog(null,
                        "Would you like to save the Brick Data before leaving?",
                        "Save Resource",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (userChoice == 0) {
                    JFileChooser jFileChooser = new JFileChooser();

                    int returnVal = jFileChooser.showSaveDialog(null);
                    File file = jFileChooser.getSelectedFile();
                    BufferedWriter bufferedWriter = null;
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        if(MainFrame.currentWindow == 1) {
                            try {
                                bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath() + ".csv"));
                                bufferedWriter.write(MainFrame.dnaBrick.toString());
                                bufferedWriter.close();

                                JOptionPane.showMessageDialog(null, "The file has been Saved Successfully!",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                            }
                            catch (IOException e1) {
                                JOptionPane.showMessageDialog(null, "The File could not be Saved!",
                                        "Error!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else if (userChoice == 2) {
                    return;
                }
                showInputDialogue();
            } else if (MainFrame.currentWindow == 2) {
                if (FreeGridActionListener.isToBeSaved && !FreeGridActionListener.savedFunctionCalled) {
                    Object[] options = {"Yes",
                            "No",
                            "Cancel"};

                    int userChoice = JOptionPane.showOptionDialog(null,
                            "Would you like to save the DNA Seq before leaving?",
                            "Save Resource",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[2]);

                    if (userChoice == 0) {
                        JFileChooser jFileChooser = new JFileChooser();

                        int returnVal = jFileChooser.showSaveDialog(null);
                        File file = jFileChooser.getSelectedFile();
                        BufferedWriter bufferedWriter = null;
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            if(MainFrame.currentWindow == 2) {
                                try {
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
                                catch (IOException e1) {
                                    JOptionPane.showMessageDialog(null, "The File could not be Saved!",
                                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    } else if (userChoice == 2) {
                        return;
                    }
                }
                showInputDialogue();
            }
        }
    }

    class SubmitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (MainFrame.currentWindow == 1) {
                brickDimInputFrame.setVisible(false);

                MainFrame.dnaBrick = new DNABrick(Double.parseDouble(inputHeight.getText()), Double.parseDouble(inputWidth.getText()));
                MainFrame.newBrickCreated = true;
                MainFrame.saveBrickFunctionCalled = false; // Reset Save Flag

                // TODO Delete the Existing Image. Draw dnaBricks New One.
            } else if (MainFrame.currentWindow == 2) {
                brickDimInputFrame.setVisible(false);

                FreeGridActionListener.brickWidth = Double.parseDouble(inputWidth.getText());
                FreeGridActionListener.brickHeight = Double.parseDouble(inputHeight.getText());

                MainFrame.dnaBrick = new DNABrick(Double.parseDouble(inputHeight.getText()), Double.parseDouble(inputWidth.getText()));
                MainFrame.newBrickCreated = true;
                MainFrame.saveBrickFunctionCalled = false; // Reset Save Flag

                // Clear Canvas
                FreeGridActionListener.dnaBricks.clear();
                FreeGridActionListener.canvas.setBackground(Color.white);
                FreeGridActionListener.canvas.pressed = 2;
                FreeGridActionListener.canvas.repaint();
                FreeGridActionListener.isToBeSaved = false; // Reset Save Flag
            }
        }
    }
}
