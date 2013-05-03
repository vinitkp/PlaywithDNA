import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ExitItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (MainFrame.currentWindow == 1 && MainFrame.newBrickCreated) {
            if (MainFrame.saveBrickFunctionCalled) {
                System.exit(0);
            }
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
                System.out.println(userChoice);
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
            } else if (userChoice == 1) {
                System.exit(0);
            }
        } else if (MainFrame.currentWindow == 2 && FreeGridActionListener.isToBeSaved && !FreeGridActionListener.savedFunctionCalled) {
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
            } else if (userChoice == 1) {
                if (MainFrame.newBrickCreated) {
                    Object[] options2 = {"Yes",
                            "No",
                            "Cancel"};

                    int userChoice2 = JOptionPane.showOptionDialog(null,
                            "Would you like to save the Brick Data before leaving?",
                            "Save Resource",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options2,
                            options2[2]);

                    if (userChoice2 == 0) {
                        System.out.println(userChoice2);
                        JFileChooser jFileChooser = new JFileChooser();

                        int returnVal = jFileChooser.showSaveDialog(null);
                        File file = jFileChooser.getSelectedFile();
                        BufferedWriter bufferedWriter = null;
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            if(MainFrame.currentWindow == 2) {
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
                    } else if (userChoice2 == 2) {
                        return;
                    } else if (userChoice2 == 1) {
                        System.exit(0);
                    }
                }
                System.exit(0);
            }
        } else if (MainFrame.currentWindow == 3 && DigitalGridActionListener.isToBeSaved && !DigitalGridActionListener.savedFunctionCalled) {
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
                    if(MainFrame.currentWindow == 3) {
                        try {
                            FreeGridActionListener.savedFunctionCalled = true;
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
                        catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "The File could not be Saved!",
                                    "Error!", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } else if (userChoice == 2) {
                return;
            } else if (userChoice == 1) {
                if (MainFrame.newBrickCreated) {
                    Object[] options2 = {"Yes",
                            "No",
                            "Cancel"};

                    int userChoice2 = JOptionPane.showOptionDialog(null,
                            "Would you like to save the Brick Data before leaving?",
                            "Save Resource",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options2,
                            options2[2]);

                    if (userChoice2 == 0) {
                        System.out.println(userChoice2);
                        JFileChooser jFileChooser = new JFileChooser();

                        int returnVal = jFileChooser.showSaveDialog(null);
                        File file = jFileChooser.getSelectedFile();
                        BufferedWriter bufferedWriter = null;
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            if(MainFrame.currentWindow == 3) {
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
                    } else if (userChoice2 == 2) {
                        return;
                    } else if (userChoice2 == 1) {
                        System.exit(0);
                    }
                }
                System.exit(0);
            }
        }
    }
}
