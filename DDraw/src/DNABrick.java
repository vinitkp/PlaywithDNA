/* This class creates dnaBricks DNA Brick as described in the referred Paper.
 * It takes the Height and Width as inputted by the user and creates dnaBricks DNA Brick
 * A couple of mathematical assumptions have been taken.
 * These assumptions have been drawn from the Brick Structure given in the paper
 * for the default 3nm * 7nm Brick and have been used to create bricks of any other dimension
 *
 * Note: 3nm * 7nm is the smallest possible dimension.
 */

import java.util.Arrays;

public class DNABrick {
    public double brickWidth;
    public double brickHeight;
    public int minWidth;
    public int minHeight;
    public DNADomains dnaDomains;
    public HalfTile[] topL;
    public HalfTile[] bottomL;
    public Object[][] middleU;

    public DNABrick(double brickHeight,double brickWidth) {
        this.brickHeight = brickHeight;
        this.brickWidth = brickWidth;

        minWidth = (int)(brickWidth / 1.75);
        minHeight = (int)(brickHeight / .6);

        topL = new HalfTile[minWidth];
        bottomL = new HalfTile[minWidth];
        middleU = new Object[minHeight][minWidth + 1];

        dnaDomains = new DNADomains();
        
        createBrick();
    }

    public void createBrick() {
        for (int i = 0; i < minWidth; i++) {
            topL[i] = new HalfTile(dnaDomains.getDomainSeqOne(), dnaDomains.getDomainSeqTwo());
            bottomL[i] = new HalfTile(dnaDomains.getDomainSeqFour(), dnaDomains.getDomainSeqThree());
        }

        for (int i = 0; i < minHeight; i++) {
            int j = 0;
            if (i % 2 ==  0) {
                middleU[i][j] = new HalfTile(dnaDomains.getDomainSeqThree(), dnaDomains.getDomainSeqTwo());

                for (j = 1; j < minWidth; j++) {
                    middleU[i][j] = new FullTile(dnaDomains.getDomainSeqFour(), dnaDomains.getDomainSeqThree(),
                            dnaDomains.getDomainSeqTwo(), dnaDomains.getDomainSeqOne());
                }
                middleU[i][j] = new HalfTile(dnaDomains.getDomainSeqFour(), dnaDomains.getDomainSeqOne());
            }

            for (j = 0; j < minWidth; j++) {
                middleU[i][j] = new FullTile(dnaDomains.getDomainSeqFour(), dnaDomains.getDomainSeqThree(),
                        dnaDomains.getDomainSeqTwo(), dnaDomains.getDomainSeqOne());
            }
            middleU[i][j] = null;
        }
    }

    @Override
    public String toString() {
        String finalDNASeq = "";
        for (int i = 0; i < minWidth; i++) {
            finalDNASeq += topL[i];
        }
        
        finalDNASeq += "\n";

        for (int i = 0; i < minHeight; i++) {
            for(int j = 0; j < minWidth + 1; j++) {
                if (middleU[i][j] != null) {
                    finalDNASeq += middleU[i][j];
                }
            }
            finalDNASeq += "\n";
        }

        for (int i = 0; i < minWidth; i++) {
            finalDNASeq += bottomL[i];
        }

        return finalDNASeq;
    }

    // TODO: After create brick, Call dnaBricks Graphic Function which would draw the brick onto the screen.
}
