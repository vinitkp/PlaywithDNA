import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame {
	public static JFrame mainFrame;
	static JPanel panelRight;
	static JPanel panelLeft;
	static DNABrick dnaBrick;
	static boolean newBrickCreated = false;
    static boolean saveBrickFunctionCalled = false;

    static int currentWindow = 0;
	// 1 = File --> New.
	// 2 = Free Hand Grid.
	// 3 = Digital Grid

	public void drawFrame(){
		mainFrame = new JFrame("DNA Pen - Welcome !");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.print(screenSize);
		mainFrame.setSize(screenSize);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                doExit();
            }
        };
        mainFrame.addWindowListener(exitListener);

		mainFrame.setLayout(new GridLayout(1,1));
		panelLeft=new JPanel();
		panelLeft.setSize(1366,700);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);       // to make file menu drop down show over canvas
		mainFrame.setContentPane(panelLeft);

        // Draw Menu Bar
		JMenuBar jMenuBar = new JMenuBar();
		mainFrame.setJMenuBar(jMenuBar);

        // Add Menus
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu toolMenu = new JMenu("Tools");
        JMenu helpMenu = new JMenu("Help");

		jMenuBar.add(fileMenu);
		jMenuBar.add(editMenu);
		jMenuBar.add(toolMenu);
        jMenuBar.add(helpMenu);

		JMenuItem newItem = new JMenuItem("Create New Brick");
		JMenuItem saveBrickItem = new JMenuItem("Save Brick Data");
        JMenuItem saveDrawSeqItem = new JMenuItem("Save Draw Data");
		JMenuItem blankLineItem = new JMenuItem("");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(newItem);
		fileMenu.add(saveBrickItem);
        fileMenu.add(saveDrawSeqItem);
		fileMenu.add(blankLineItem);
		fileMenu.add(exitItem);
        blankLineItem.invalidate();
		
		JMenuItem changeDimensionsItem = new JMenuItem("Edit Dimensions");
		JMenuItem clearItem = new JMenuItem("Clear");

		editMenu.add(changeDimensionsItem);
		editMenu.add(clearItem);
		
		JMenuItem gridDrawItem = new JMenuItem("Free Hand Draw Grid");
		JMenuItem digitalDrawItem = new JMenuItem("Digitised Draw Grid");

		toolMenu.add(gridDrawItem);
		toolMenu.add(digitalDrawItem);
		
		JMenuItem userManualItem = new JMenuItem("User Manual");
        JMenuItem productDemoItem = new JMenuItem("Product Demo");
        JMenuItem aboutItem = new JMenuItem("About");

        helpMenu.add(userManualItem);
        helpMenu.add(productDemoItem);
        helpMenu.add(aboutItem);

        mainFrame.setVisible(true);
		
		// Defining Action Listeners for every Menu Item
		// Respective listeners will be called whenever a function is selected.
		
		newItem.addActionListener(new NewItemListener());
        saveBrickItem.addActionListener(new SaveBrickDataListener());
        saveDrawSeqItem.addActionListener(new SaveItemActionListener());
        exitItem.addActionListener(new ExitItemListener());

		changeDimensionsItem.addActionListener(new EditDimensionListener());
        clearItem.addActionListener(new ClearActionListener());

		gridDrawItem.addActionListener(new FreeGridActionListener());
		digitalDrawItem.addActionListener(new DigitalGridActionListener() );
	}

	public static void main(String [] srgs)
	{
        MainFrame mainFrame1 = new MainFrame();
		mainFrame1.drawFrame();
	}

    public void doExit() {
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

