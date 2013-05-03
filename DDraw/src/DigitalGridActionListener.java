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

import javax.swing.*;


public class DigitalGridActionListener implements ActionListener {
	
	static DrawCanvas canvas;
	static boolean isToBeSaved = false;
	JLabel praxis;
	static ArrayList<DNABrick> dnaDigitalTiles = new ArrayList<DNABrick>();
    static boolean savedFunctionCalled = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.mainFrame.setTitle("DNA Pen - Digitised Drawing Grid");

		if(MainFrame.currentWindow == 2 && FreeGridActionListener.isToBeSaved && !FreeGridActionListener.savedFunctionCalled) {
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
		        		} catch (IOException e1) {
		        			JOptionPane.showMessageDialog(null, "The File could not be Saved!",  
		                        "Error!", JOptionPane.INFORMATION_MESSAGE);  
		        		}
		        	} 
		        }
			} else if (userChoice == 2) {
                return;
			}
			FreeGridActionListener.canvas.setVisible(false);
		}
        if (MainFrame.currentWindow == 2) {
            FreeGridActionListener.canvas.setVisible(false);
        }

        // Set value of currentWindow to 3 to indicate that Digital Draw Grid is active.
        MainFrame.currentWindow = 3;

		JPanel jPanel = new JPanel();
		MainFrame.panelLeft.add(jPanel);
		jPanel.setSize(1366, 700);
		jPanel.setLayout(new GridLayout(2, 1));

		canvas = new DrawCanvas();
		jPanel.add(canvas);
		JLabel jLabel = new JLabel("Coordinates Are: ");
		jPanel.add(jLabel);
		praxis = new JLabel("");
	}

	// Draw Method

	public class DrawCanvas extends Canvas {
        BasicStroke basicStroke;
		int X, Y, pressed = 0;
		float dashes[] = { 5f, 5f };

		public DrawCanvas() {
            setBackground(Color.white);
            addMouseListener(new MyMouseListener());
            addMouseMotionListener(new MyMouseListener());
            setSize(1366, 680);
            basicStroke = new BasicStroke(1f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 10f, dashes, 0f);
		}

        public void update(Graphics g) {
            paint(g);
        }
		 
        public void paint(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g;
            if(pressed == 1) {
                graphics2D.setColor(Color.black);
                graphics2D.setStroke(basicStroke);
		 
		        // Erase the currently existing line
		        /************
		         * code to color particular line 
		         ************/
		        int i = Y;    // draw horizontal line
		        int j = Y;
		        int count = 5;
		        int x1, x2;
		        x1 = X - X % 90;
		        x2 = x1 + 90;
		        while (count-- > 0) {
                    if (i % 30 == 0) {
                        paintHorLine(graphics2D, i, x1, x2);
		        		break;
		        	} else if (j % 30 == 0) {
                        paintHorLine(graphics2D, j, x1, x2);
		        		break;
		        	} else {
                        i--;
                        j++;
                    }
		        }
		        // end of code to draw horizontal line

                j = i = X;
		        count = 5;
		        int y1, y2;

		        y1 = Y - Y % 30;
		        y2 = y1 + 30;

                while (count-- > 0) {
		        	if (i % 90 == 0) {
		        		paintVerLine(graphics2D, i, y1, y2);
		        		break;
		        	} else if (j % 90 == 0) {
		        		paintVerLine(graphics2D, j, y1, y2);
		        		break;
		        	} else {
                        i--;
                        j++;
                    }
		        }
            } else if (pressed == 2) {
                graphics2D.setColor(Color.white);
                graphics2D.fillRect(0, 0, 1366, 680);
			        
                int constY = 0;
                int constX = 0;

                graphics2D.setColor(Color.black);

                while (constY < 680) {
                    graphics2D.draw(new Line2D.Float(0, constY, 1366, constY));
                    constY += 30;
                }
                while (constX < 1366) {
                    graphics2D.draw(new Line2D.Float(constX, 0, constX, 680));
                    constX += 90;
                }
            } else {
                int constY = 0;
                int constX = 0;
                while (constY < 680) {
                    graphics2D.draw(new Line2D.Float(0, constY, 1366, constY));
                    constY += 30;
                }
                while (constX < 1366) {
                    graphics2D.draw(new Line2D.Float(constX, 0, constX, 680));
                    constX += 90;
                }
            }
        }

        // Paint Horizontal Line
        public void paintHorLine(Graphics2D g2D, int y, int x1, int x2) {
            g2D.draw(new Line2D.Float(x1, y-1, x2, y-1));
            g2D.draw(new Line2D.Float(x1, y-2, x2, y-2));
            g2D.draw(new Line2D.Float(x1, y, x2, y));
            g2D.draw(new Line2D.Float(x1, y+1, x2, y+1));
            g2D.draw(new Line2D.Float(x1, y+2, x2, y+2));
            System.out.println("Horizontal");
        }

        // Paint Vertical line
        public void paintVerLine(Graphics2D g2D, int x, int y1, int y2) {
            g2D.draw(new Line2D.Float(x-1, y1, x, y2));
            g2D.draw(new Line2D.Float(x, y1, x-1, y2));
            g2D.draw(new Line2D.Float(x-2, y1, x-2, y2));
            g2D.draw(new Line2D.Float(x+1, y1, x+1, y2));
            g2D.draw(new Line2D.Float(x+2, y1, x+2, y2));
            System.out.println("Vertical");
        }


        class MyMouseListener extends MouseAdapter implements MouseMotionListener {

            public void mousePressed(MouseEvent e) {
                isToBeSaved = true;
                savedFunctionCalled = false;
                pressed = 1;
                X = e.getX();
                Y = e.getY();
                canvas.repaint();
            }

            public void mouseReleased(MouseEvent e) {
                pressed = 0;
                canvas.repaint();
            }

            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
                String string = "(" + Integer.toString(e.getX()) + ", "
                        + Integer.toString(e.getY()) + ")";

            }
        }
	}
}