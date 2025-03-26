
#include <iostream>
#include <jni.h>
#include "GamePanel.h"
#include "InputHandler.h"


    /*
     * 0 - white field, 1 - black field
     * 2 - black figure, 3 white figure
     * 4 - white figure selected, 5 - black figure selected
     */
    int figuresArr[10][10] = {    {0,3,0,3,0,3,0,3,0,3},
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
        int initFiguresArr[10][10] =  {  {0,3,0,3,0,3,0,3,0,3},
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
    enum Turn {
        WHITE,
        BLACK
    };
    Turn turn = WHITE;
    int whitePices = 12;
    int blackPices = 12;


JNIEXPORT jobjectArray JNICALL Java_GamePanel_getInitArray
  (JNIEnv *env, jclass cl) {
    int rows = 10;
    int cols = 10;

    jclass intArrayClass = env->FindClass("[I");
    jobjectArray outerArray = env->NewObjectArray(rows, intArrayClass, nullptr);
    for (int i = 0; i < rows; i++) {
        jintArray innerArray = env->NewIntArray(cols);
        env->SetIntArrayRegion(innerArray, 0, cols, initFiguresArr[i]);
        env->SetObjectArrayElement(outerArray, i, innerArray);
        env->DeleteLocalRef(innerArray);
    }

    return outerArray;

}


JNIEXPORT jobjectArray JNICALL Java_GamePanel_getFiguresArray
(JNIEnv *env, jclass jcl)
{
    int rows = 10;
    int cols = 10;

    jclass intArrayClass = env->FindClass("[I");
    jobjectArray outerArray = env->NewObjectArray(rows, intArrayClass, nullptr);
    for (int i = 0; i < rows; i++) {
        jintArray innerArray = env->NewIntArray(cols);
        env->SetIntArrayRegion(innerArray, 0, cols, figuresArr[i]);
        env->SetObjectArrayElement(outerArray, i, innerArray);
        env->DeleteLocalRef(innerArray);
    }

    return outerArray;
}

JNIEXPORT jint JNICALL Java_GamePanel_getFigureFromArray
  (JNIEnv * env, jclass cl, jint i, jint j) {
    return figuresArr[i][j];

}



JNIEXPORT void JNICALL Java_InputHandler_passCellXY(JNIEnv *env, jclass cl, jint x, jint y) {
    static int selectedX = -1;
    static int selectedY = -1;
    static int previousSelection = -4;  // Track which figure was selected (white=3, black=2)

    for (auto & i : figuresArr) {
        for (int & j : i) {
            if (j == 4) {
                j = 3;
            } else if (j == 5) {
                j = 2;
            }
        }
    }


    if ((figuresArr[y][x] == 3 && turn == WHITE) || (figuresArr[y][x] == 2 && turn == BLACK)) {
        if (selectedX != -1 && selectedY != -1) {
            if (figuresArr[selectedY][selectedX] == 4) {
                figuresArr[selectedY][selectedX] = 3;
            } else if (figuresArr[selectedY][selectedX] == 5) {
                figuresArr[selectedY][selectedX] = 2;
            }
        }

        previousSelection = figuresArr[y][x];
        selectedX = x;
        selectedY = y;


        if (figuresArr[y][x] == 3) {
            figuresArr[y][x] = 4;
        } else if (figuresArr[y][x] == 2) {
            figuresArr[y][x] = 5;
        }

        std::cout << "Figure selected at x: " << selectedX << " y: " << selectedY << std::endl;
        return;
    }


    if (selectedX != -1 && selectedY != -1) {
        if (figuresArr[y][x] == 1) {
            int dx = abs(x - selectedX);
            int dy = y - selectedY;

            if (((previousSelection == 3 || previousSelection == 4) && dy > 0) ||
                ((previousSelection == 2 || previousSelection == 5) && dy < 0)) {


                if (dx == 1 && abs(dy) == 1) {
                    figuresArr[y][x] = figuresArr[selectedY][selectedX];
                    figuresArr[selectedY][selectedX] = 1;


                    if (figuresArr[y][x] == 4) figuresArr[y][x] = 3;
                    else if (figuresArr[y][x] == 5) figuresArr[y][x] = 2;

                    previousSelection = -4;
                    selectedX = -1;
                    selectedY = -1;

                    turn = (turn == WHITE) ? BLACK : WHITE;
                    std::cout << "Turn changed. It's now " << (turn == WHITE ? "White's" : "Black's") << " turn." << std::endl;
                }

                else if (dx == 2 && abs(dy) == 2) {
                    int midX = (selectedX + x) / 2;
                    int midY = (selectedY + y) / 2;

                    // Check if the middle cell contains an opponent piece
                    if ((figuresArr[midY][midX] == 2 && previousSelection == 3) ||
                        (figuresArr[midY][midX] == 3 && previousSelection == 2)) {
                        if (figuresArr[midY][midX] == 2 && previousSelection == 3) blackPices--;
                        else if (figuresArr[midY][midX] == 3 && previousSelection == 2) whitePices--;
                        std::cout << "Black pieces: "<< blackPices << " white pieces: "<< whitePices << std::endl;

                        std::cout << "Capture move! Jumping over opponent's piece.\n";
                        figuresArr[y][x] = figuresArr[selectedY][selectedX];
                        figuresArr[selectedY][selectedX] = 1;
                        figuresArr[midY][midX] = 1;
                        previousSelection = -4;
                        selectedX = -1;
                        selectedY = -1;

                        turn = (turn == WHITE) ? BLACK : WHITE;
                        std::cout << "Turn changed. It's now " << (turn == WHITE ? "White's" : "Black's") << " turn." << std::endl;
                    }
                }
            } else {
                std::cout << "Invalid move: You can only move forward.\n";
            }
        }

        selectedX = -1;
        selectedY = -1;
        previousSelection = -4;
    }
}
JNIEXPORT jboolean JNICALL Java_GamePanel_gameOver
  (JNIEnv *env, jclass cl) {
    if (blackPices == 0 || whitePices == 0) {
        return JNI_FALSE;
    }
    else {
        return JNI_TRUE;
    }
}







