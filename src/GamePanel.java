import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    InputHandler ih;
    boolean GameOver = false;


    public GamePanel() {
        System.loadLibrary("UTP_Project_cpp");
        ih = new InputHandler(this);
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setOpaque(true);
        addKeyListener(ih);
        addMouseListener(ih);
        setFocusable(true);

    }
    public void paintComponent(Graphics g) {
        renderBoard(g);
        renderFigures(g);
    }

    private void renderBoard(Graphics g) {
        int x = 0;
        int y = 0;
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        setBackground(Constants.color1);

        for (int i = 0; i < Constants.COLUMNS; i++) {
            for (int j = 0; j < Constants.ROWS/2; j++) {
                g2.setColor(Constants.color0);
                g2.fillRect(x, y, Constants.CELL_SIZE, Constants.CELL_SIZE);
                x+=2* Constants.CELL_SIZE;
            }
            if (i%2==0) x= Constants.CELL_SIZE;
            else x=0;
            y+= Constants.CELL_SIZE;
        }

    }

    private void renderFigures(Graphics g) {
        int padding = 10;
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < Constants.COLUMNS; i++) {
            for (int j = 0; j < Constants.ROWS; j++) {
                switch(getFigureFromArray(i, j)) {
                    case 0, 1 -> drawEmptyCell(g2, i, j);
                    case 2 -> drawBlackFigure(g2, i, j, padding);
                    case 3 -> drawWhiteFigure(g2, i, j, padding);
                    case 4 -> {
                        drawWhiteFigure(g2, i, j, padding);
                        drawSelection(g2, i, j);
                    }
                    case 5 -> {
                        drawBlackFigure(g2, i, j, padding);
                        drawSelection(g2, i, j);
                    }
                }

            }
        }

    }

    private static void drawBlackFigure(Graphics2D g2, int i, int j, int padding) {
        g2.setColor(Constants.color2);
        g2.fillOval(j* Constants.CELL_SIZE+padding, i* Constants.CELL_SIZE+padding,
                Constants.CELL_SIZE-(2*padding), Constants.CELL_SIZE-(2*padding));
    }
    private static void drawWhiteFigure(Graphics2D g2, int i, int j, int padding) {
        g2.setColor(Constants.color3);
        g2.fillOval(j* Constants.CELL_SIZE+padding, i* Constants.CELL_SIZE+padding,
                Constants.CELL_SIZE-(2*padding), Constants.CELL_SIZE-(2*padding));
    }
    private static void drawEmptyCell(Graphics2D g2, int i, int j) {
        g2.setColor(Constants.transparentColor);
        g2.drawRect(j* Constants.CELL_SIZE, i* Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);

    }
    private static void drawSelection(Graphics2D g2, int i, int j) {
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(Constants.selectionColor);
        g2.drawRect(j*Constants.CELL_SIZE, i*Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }



    public static native int[][] getFiguresArray();
    public static native int getFigureFromArray(int i, int j);
    public static native int[][] getInitArray();
    public static native boolean gameOver();
}
