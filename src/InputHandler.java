import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {
    int cellX;
    int cellY;
    GamePanel gp;
    private boolean figureSelected = false;
    private int selectedX = -1;  // X coordinate of the selected figure
    private int selectedY = -1;
    public InputHandler(GamePanel gp) {
        this.gp = gp;
        cellX = -1;
        cellY = -1;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (cellX == -1 && cellY == -1) {
            cellX = 5;
            cellY = 1;
            passCellXY(cellX, cellY);
        }

        int code = e.getKeyCode();


        switch (code) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                if (cellY > 0) cellY--;  // Move up
            }
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                if (cellY < Constants.COLUMNS - 1) cellY++;  // Move down
            }
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                if (cellX > 0) cellX--;  // Move left
            }
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                if (cellX < Constants.ROWS - 1) cellX++;  // Move right
            }


        }
        passCellXY(cellX, cellY);
        gp.repaint();//
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
            gp.repaint();
            cellX = e.getX()/Constants.CELL_SIZE;
            cellY = e.getY()/Constants.CELL_SIZE;
            passCellXY(cellX, cellY);
            gp.repaint();
        }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public static native void passCellXY(int cellX, int cellY);

    public static native boolean selectFigure(int cellX, int cellY);
    public static native boolean moveFigure(int selectedX, int selectedY, int cellX, int cellY);

}
