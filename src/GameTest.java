import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    GamePanel gp = new GamePanel();
    InputHandler ih = new InputHandler(gp);
    int[][] board;
    int[][] expectedBoard;

    static {
        System.loadLibrary("UTP_Project_cpp");
    }

    @Test
    public void testBoardInit() {
        expectedBoard = new int[][]{            {0,3,0,3,0,3,0,3,0,3},
                                                {3,0,3,0,3,0,3,0,3,0},
                                                {0,3,0,3,0,3,0,3,0,3},
                                                {1,0,1,0,1,0,1,0,1,0},
                                                {0,1,0,1,0,1,0,1,0,1},
                                                {1,0,1,0,1,0,1,0,1,0},
                                                {0,1,0,1,0,1,0,1,0,1},
                                                {2,0,2,0,2,0,2,0,2,0},
                                                {0,2,0,2,0,2,0,2,0,2},
                                                {2,0,2,0,2,0,2,0,2,0},
        };
        board = GamePanel.getInitArray();
        assertArrayEquals(expectedBoard, board);
        }
    @Test
    public void testWhiteMove() {
        expectedBoard = new int[][]{            {0,3,0,3,0,3,0,3,0,3},
                                                {3,0,3,0,3,0,3,0,3,0},
                                                {0,3,0,1,0,3,0,3,0,3},
                                                {1,0,1,0,3,0,1,0,1,0},
                                                {0,1,0,1,0,1,0,1,0,1},
                                                {1,0,1,0,1,0,1,0,1,0},
                                                {0,1,0,1,0,1,0,1,0,1},
                                                {2,0,2,0,2,0,2,0,2,0},
                                                {0,2,0,2,0,2,0,2,0,2},
                                                {2,0,2,0,2,0,2,0,2,0},
        };
        InputHandler.passCellXY(3, 2);
        InputHandler.passCellXY(4, 3);
        board = GamePanel.getFiguresArray();
        assertEquals(board[3][4], 3);
        }
    @Test
    public void testBlackMove() {
        expectedBoard = new int[][]{            {0,3,0,3,0,3,0,3,0,3},
                                                {3,0,3,0,3,0,3,0,3,0},
                                                {0,3,0,3,0,3,0,3,0,3},
                                                {1,0,1,0,1,0,1,0,1,0},
                                                {0,1,0,1,0,1,0,1,0,1},
                                                {1,0,1,0,1,0,1,0,1,0},
                                                {0,1,0,2,0,1,0,1,0,1},
                                                {2,0,2,0,1,0,2,0,2,0},
                                                {0,2,0,2,0,2,0,2,0,2},
                                                {2,0,2,0,2,0,2,0,2,0},
        };
        InputHandler.passCellXY(4, 7);
        InputHandler.passCellXY(3, 6);
        board = GamePanel.getFiguresArray();
        assertEquals(board[6][3], 2);
    }
    @Test
    public void testCapture(){
        expectedBoard = new int[][]{        {0,3,0,3,0,3,0,3,0,3},
                                            {3,0,3,0,3,0,3,0,3,0},
                                            {0,3,0,3,0,3,0,3,0,3},
                                            {1,0,1,0,1,0,1,0,1,0},
                                            {0,1,0,3,0,1,0,1,0,1},
                                            {1,0,1,0,2,0,1,0,1,0},
                                            {0,1,0,2,0,1,0,1,0,1},
                                            {2,0,2,0,1,0,2,0,2,0},
                                            {0,2,0,2,0,2,0,2,0,2},
                                            {2,0,2,0,2,0,2,0,2,0},
        };
        InputHandler.passCellXY(3, 4);
        InputHandler.passCellXY(5, 6);
        board = GamePanel.getFiguresArray();
        assertEquals(board[4][5], 1);
    }
}




