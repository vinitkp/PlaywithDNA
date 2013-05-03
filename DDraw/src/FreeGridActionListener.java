import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class FreeGridActionListener implements ActionListener {
	
	static DrawCanvas canvas;
	static double brickHeight = 3; // Set default Brick Height
	static double brickWidth = 7; // Set default Brick Width
	static boolean isToBeSaved = false;
    static boolean savedFunctionCalled = false;
    static boolean defaultValueSet = true;
	JLabel praxis;
	static ArrayList<DNABrick> dnaBricks = new ArrayList<DNABrick>();

    @Override
    public void actionPerformed(ActionEvent e){
        MainFrame.mainFrame.setTitle("DNA Pen - Free Hand Drawing Grid");

        if (!MainFrame.newBrickCreated) {
            MainFrame.dnaBrick = new DNABrick(brickHeight, brickWidth);
            MainFrame.newBrickCreated = true;
        }

		if(MainFrame.currentWindow == 3 && DigitalGridActionListener.isToBeSaved && !DigitalGridActionListener.savedFunctionCalled) {
			/*
			 * Checks if you're coming from Digitised Grid,
			 * If yes, then it will ask you to save if the data is not saved.
			 */
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
            }
			DigitalGridActionListener.canvas.setVisible(false);
		}
        if (MainFrame.currentWindow == 3) {
            DigitalGridActionListener.canvas.setVisible(false);
        }

        // Set value of currentWindow to 2 to indicate that Free Draw Grid is active.
		MainFrame.currentWindow = 2;


		JPanel jPanel = new JPanel();
		MainFrame.panelLeft.add(jPanel);
		jPanel.setSize(1366, 700);
		jPanel.setLayout(new GridLayout(2, 1));
		canvas = new DrawCanvas();
		jPanel.add(canvas);
		JLabel axis = new JLabel("Coordinates Are: ");
		jPanel.add(axis);
		praxis = new JLabel("");

	}

	/*
	 * Main Draw Method.
	 * This method draws the Grid onto the screen and
	 * allows the user to draw onto the screen/canvas
	 */
	public class DrawCanvas extends Canvas{
		BasicStroke stroke;
		int X,Y,pressed=0;
		float dashes[] = { 5f, 5f };

		public DrawCanvas() {
			setBackground(Color.white);
            addMouseListener(new MyMouseListener());
            addMouseMotionListener(new MyMouseListener());
            setSize(1366, 680);
            stroke = new BasicStroke(1f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 10f, dashes, 0f);
		}

        public void update(Graphics g) {
            paint(g);
        }
		 
        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            if (pressed == 1) {
                g2D.setColor(Color.black);
                g2D.setStroke(stroke);
                g2D.fillOval( X, Y, 5, 5 );
            } else if (pressed == 2) {
                g2D.setColor(Color.white);
                g2D.fillRect(0, 0, 1366, 680);

                int constY = 0;
                int constX = 0;
                g2D.setColor(Color.black);

                while (constY < 680) {
                    g2D.draw(new Line2D.Float(0, constY, 1366, constY));
                    constY += 30;
                }

                while (constX < 1366) {
                    g2D.draw(new Line2D.Float(constX, 0, constX, 680));
                    constX += 90;
                }
            } else {
                int constY = 0;
                int constX = 0;

                g2D.setColor(Color.black);

                while (constY < 680) {
                    g2D.draw(new Line2D.Float(0, constY, 1366, constY));
                    constY += 30;
                }
                while (constX < 1366) {
                    g2D.draw(new Line2D.Float(constX, 0, constX, 680));
                    constX += 90;
                }
            }
        }

        class MyMouseListener extends MouseAdapter implements MouseMotionListener {
            int xRightBound = -1;
            int xLeftBound = -1;
            int yUpBound = -1;
            int yDownBound = -1;

            public void mousePressed(MouseEvent e) {
                isToBeSaved = true;
                savedFunctionCalled = false;
                X = e.getX();
                Y = e.getY();
		         
                if (pressed == 0) {
                    calcDom(X, Y);
                    pressed = 1;
                } else if (pressed == 2) {
                    pressed = 1;
                }
            }

            public void calcDom(int xCoordinate, int yCoordinate) {
                int i = xCoordinate;
                int j = yCoordinate;
                xRightBound = -1;
                xLeftBound = -1;
                yUpBound = -1;
                yDownBound = -1;

                while (true) {
                    if (i % 90 == 0) {
                        xRightBound = i;
                        break;
                    } else {
                        i++;
                    }
                }

                i = xCoordinate;

                while (true) {
                    if (i % 90 == 0) {
                        xLeftBound = i;
                        break;
                    } else {
                        i--;
                    }
                }

                i = yCoordinate;

                while (true) {
                    if (i % 30 == 0) {
                        yUpBound = i;
                        break;
                    } else {
                        i--;
                    }
                }

                i = yCoordinate;

                while (true) {
                    if (i % 30 == 0) {
                        yDownBound = i;
                        break;
                    } else {
                        i++;
                    }
                }
            }

            public void domChange(int xCoordinate, int yCoordinate) {
                boolean change = false;
                if (xCoordinate > xRightBound || xCoordinate < xLeftBound) {
                    change = true;
                    dnaBricks.add(new DNABrick(brickHeight, brickWidth));

                    System.out.println("x: " + xCoordinate + " added\n");

                    calcDom(xCoordinate, yCoordinate);
                } else if (yCoordinate < yUpBound || yCoordinate > yDownBound) {
                    change = true;
                    dnaBricks.add(new DNABrick(brickHeight, brickWidth));

                    System.out.println("y: " + yCoordinate + " added\n");

                    calcDom(xCoordinate, yCoordinate);
                }
            }
		        
            public void mouseReleased(MouseEvent e) {
                pressed = 0;
                xRightBound = -1;
                xLeftBound = -1;
                yUpBound = -1;
                yDownBound = -1;
            }

            public void mouseDragged(MouseEvent e) {
                X = e.getX();
                Y = e.getY();
                domChange(X, Y);
                canvas.repaint();
            }

            public void mouseMoved(MouseEvent e) {
                String string = "(" + Integer.toString(e.getX()) + ", "
                  + Integer.toString(e.getY()) + ")";
            }
        }
	}
}


