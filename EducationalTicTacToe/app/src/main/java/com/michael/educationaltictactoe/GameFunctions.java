package com.michael.educationaltictactoe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.drawable.Drawable;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Random;


public class GameFunctions {



    static Bitmap b;
    static Canvas c;
    static Paint paint;

    static private void createBitmap(){
        b = Bitmap.createBitmap(270, 270, Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
    }


    //returns either X, O, DRAW, or NO for use in a switch statement
    public static String checkWin(GridLayout gameBoardGrid, ImageView imageGameBoard){


        String[][] textCells = new String[3][3];
        String result = "NO";

        int countText = 0;
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                textCells[row][column] = ((TextView) gameBoardGrid.getChildAt(countText)).getText().toString();
                countText++;
            }
        }


        for(int row = 0; row < 3; row++){
            if(!textCells[row][0].equals(" ") && textCells[row][0].equals(textCells[row][1])  && textCells[row][0].equals(textCells[row][2])){
                result = textCells[row][0];
                createBitmap();
                switch(row){
                    case 0: c.drawLine(0, 45, 270, 45, paint);
                        break;
                    case 1: c.drawLine(0, 135, 270, 135, paint);
                        break;
                    case 2: c.drawLine(0, 225, 270, 225, paint);
                        break;
                    default: break;
                }

                imageGameBoard.setImageBitmap(b);

                return result;
            }
        }

        for(int column = 0; column < 3; column++){
            if(!textCells[0][column].equals(" ") && textCells[0][column].equals(textCells[1][column]) && textCells[0][column].equals(textCells[2][column])){
                result = textCells[0][column];
                createBitmap();
                switch(column){
                    case 0: c.drawLine(45, 0, 45, 270, paint);
                        break;
                    case 1: c.drawLine(135, 0, 135, 270, paint);
                        break;
                    case 2: c.drawLine(225, 0, 225, 270, paint);
                        break;
                    default: break;
                }

                imageGameBoard.setImageBitmap(b);

                return result;
            }
        }


        if(!textCells[0][0].equals(" ") && textCells[0][0].equals(textCells[1][1])  && textCells[0][0].equals(textCells[2][2])){
            result = textCells[0][0];
            createBitmap();
            c.drawLine(0, 0, 270, 270, paint);
            imageGameBoard.setImageBitmap(b);

            return result;
        }

        if(!textCells[0][2].equals(" ") && textCells[0][2].equals(textCells[1][1]) && textCells[0][2].equals(textCells[2][0])){
            result = textCells[0][2];
            createBitmap();
            c.drawLine(270, 0, 0, 270, paint);
            imageGameBoard.setImageBitmap(b);

            return result;
        }

        if(isDraw(textCells)){
            result = "DRAW";
            return result;
        }

        return result;
    }

    public static boolean isDraw(String[][] textCells){
        boolean result = true;
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                if(textCells[row][column].equals(" ")){
                    result = false;
                }
            }
        }
        return result;
    }


    public static void playNextMove(GridLayout gameBoardGrid, GridLayout gameBoardImageGrid, Drawable imageX, Drawable imageO, boolean isPlayerX, TextView turnTextView, int gameLevel) {
        String characterToPlay;
        String opponentCharacter;



        int[] boardValues = new int[9];


        if (isPlayerX) {
            characterToPlay = "X";
            opponentCharacter = "O";
            turnTextView.setText("O's turn");
        } else {
            characterToPlay = "O";
            opponentCharacter = "X";
            turnTextView.setText("X's turn");
        }

        Drawable imageToPlay = characterToPlay.equals("X") ? imageX : imageO;

        int boardSum = 0;
        for(int i = 0; i < 9; i++){
            if(((TextView) gameBoardGrid.getChildAt(i)).getText().toString().equals(characterToPlay)){
                boardValues[i] = 1;
            }else if(((TextView) gameBoardGrid.getChildAt(i)).getText().toString().equals(opponentCharacter)){
                boardValues[i] = -2;
            }else{
                boardValues[i] = 0;
            }
            boardSum += boardValues[i];
        }



        //stop loss

        if ((boardValues[0] + boardValues[4] + boardValues[8]) == 2 ) {
            if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                return;
            }
        }


        if ((boardValues[2] + boardValues[4] + boardValues[6]) == 2 ) {
            if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                return;
            }
        }




        if ((boardValues[0] + boardValues[1] + boardValues[2]) == 2 ) {
            if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(1)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(1)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(1)).setBackground(imageToPlay);
                return;
            }
        }



        if ((boardValues[3] + boardValues[4] + boardValues[5]) == 2 ) {
            if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(3)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(3)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(3)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(5)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(5)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(5)).setBackground(imageToPlay);
                return;
            }
        }


        if ((boardValues[6] + boardValues[7] + boardValues[8]) == 2 ) {
            if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(7)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(7)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(7)).setBackground(imageToPlay);
                return;
            }
        }



        if ((boardValues[0] + boardValues[3] + boardValues[6]) == 2 ) {
            if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(3)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(3)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(3)).setBackground(imageToPlay);
                return;
            }
        }


        if ((boardValues[1] + boardValues[4] + boardValues[7]) == 2 ) {
            if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(1)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(1)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(1)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(7)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(7)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(7)).setBackground(imageToPlay);
                return;
            }
        }


        if ((boardValues[2] + boardValues[5] + boardValues[8]) == 2) {
            if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                return;
            } else if (((TextView) gameBoardGrid.getChildAt(5)).getText().toString().equals(" ")) {
                ((TextView) gameBoardGrid.getChildAt(5)).setText(characterToPlay);
                (gameBoardImageGrid.getChildAt(5)).setBackground(imageToPlay);
                return;
            }
        }


        if(gameLevel != 0) {

                //stop loss
                if ((boardValues[0] + boardValues[4] + boardValues[8]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                        return;
                    }
                }


                if ((boardValues[2] + boardValues[4] + boardValues[6]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                        return;
                    }
                }



                if ((boardValues[0] + boardValues[1] + boardValues[2]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(1)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(1)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(1)).setBackground(imageToPlay);
                        return;
                    }
                }



                if ((boardValues[3] + boardValues[4] + boardValues[5]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(3)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(3)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(3)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(5)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(5)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(5)).setBackground(imageToPlay);
                        return;
                    }
                }


                if ((boardValues[6] + boardValues[7] + boardValues[8]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(7)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(7)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(7)).setBackground(imageToPlay);
                        return;
                    }
                }



                if ((boardValues[0] + boardValues[3] + boardValues[6]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(3)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(3)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(3)).setBackground(imageToPlay);
                        return;
                    }
                }


                if ((boardValues[1] + boardValues[4] + boardValues[7]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(1)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(1)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(1)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(7)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(7)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(7)).setBackground(imageToPlay);
                        return;
                    }
                }


                if ((boardValues[2] + boardValues[5] + boardValues[8]) == -4) {
                    if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(5)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(5)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(5)).setBackground(imageToPlay);
                        return;
                    }
                }
            }


        if(gameLevel != 1) {

            if ((boardValues[0] + boardValues[4] + boardValues[8] == 1) && boardValues[4] == 0) {
                if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                    return;
                }
            }

            if ((boardValues[2] + boardValues[4] + boardValues[6] == 1) && boardValues[4] == 0) {
                if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                    return;
                }
            }



            if (boardSum == -1 && ((boardValues[0] + boardValues[4] + boardValues[8]) == -1)) {
                if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                    return;
                }
            }


            if ((boardValues[2] + boardValues[4] + boardValues[6]) == -1) {
                if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                    return;
                }
            }
        }


            if (boardValues[4] == 0) {
                Random r = new Random(System.currentTimeMillis());
                if (r.nextInt(2) == 1) {
                    ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                    return;
                }
            }



            if ((boardValues[0] + boardValues[4] + boardValues[8]) == -3 || (boardValues[2] + boardValues[4] + boardValues[6]) == -3
                    || boardValues[4] == 0) {

                if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                    return;
                }
            }



            if ((boardValues[0] != 0 && (boardValues[0] + boardValues[4] + boardValues[8]) == 0)) {
                if (boardValues[2] + boardValues[1] + boardValues[0]
                        + boardValues[2] + boardValues[4] + boardValues[6]
                        + boardValues[2] + boardValues[5] + boardValues[8]
                        >
                        boardValues[6] + boardValues[3] + boardValues[0]
                                + boardValues[6] + boardValues[4] + boardValues[2]
                                + boardValues[6] + boardValues[7] + boardValues[8]) {

                    if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                        return;
                    }

                } else {
                    if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                        return;
                    }
                }

            }


            if ((boardValues[2] != 0 && (boardValues[2] + boardValues[4] + boardValues[6]) == 0)) {
                if (boardValues[0] + boardValues[1] + boardValues[2]
                        + boardValues[0] + boardValues[4] + boardValues[8]
                        + boardValues[0] + boardValues[3] + boardValues[6]
                        >
                        boardValues[8] + boardValues[5] + boardValues[2]
                                + boardValues[8] + boardValues[4] + boardValues[0]
                                + boardValues[8] + boardValues[7] + boardValues[6]) {

                    if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                        return;
                    }

                } else {
                    if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                        return;
                    }
                }



        }

        //try to win
        for(int i = 2; i >= -2; i--) {

            if ((boardValues[0] + boardValues[4] + boardValues[8]) == i) {
                if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                    return;
                }
            }


            if ((boardValues[2] + boardValues[4] + boardValues[6]) == i) {
                if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                    return;
                }
            }


            if ((boardValues[0] + boardValues[1] + boardValues[2]) == i) {
                if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(1)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(1)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(1)).setBackground(imageToPlay);
                    return;
                }
            }


            if ((boardValues[3] + boardValues[4] + boardValues[5]) == i) {
                if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(3)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(3)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(3)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(5)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(5)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(5)).setBackground(imageToPlay);
                    return;
                }
            }


            if ((boardValues[6] + boardValues[7] + boardValues[8]) == i) {
                if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(7)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(7)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(7)).setBackground(imageToPlay);
                    return;
                }
            }



            for(int j= 0; j < 3; j++) {
                if ((boardValues[j] + boardValues[j+3] + boardValues[j+6]) == i) {
                    if (((TextView) gameBoardGrid.getChildAt(j)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(j)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(j)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(j+3)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(j+3)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(j+3)).setBackground(imageToPlay);
                        return;
                    } else if (((TextView) gameBoardGrid.getChildAt(j+6)).getText().toString().equals(" ")) {
                        ((TextView) gameBoardGrid.getChildAt(j+6)).setText(characterToPlay);
                        (gameBoardImageGrid.getChildAt(j+6)).setBackground(imageToPlay);
                        return;
                    }
                }
            }



            if ((boardValues[0] + boardValues[3] + boardValues[6]) == i ) {
                if (((TextView) gameBoardGrid.getChildAt(0)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(0)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(0)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(6)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(6)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(6)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(3)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(3)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(3)).setBackground(imageToPlay);
                    return;
                }
            }


            if ((boardValues[1] + boardValues[4] + boardValues[7]) == i) {
                if (((TextView) gameBoardGrid.getChildAt(4)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(4)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(4)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(1)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(1)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(1)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(7)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(7)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(7)).setBackground(imageToPlay);
                    return;
                }
            }


            if ((boardValues[2] + boardValues[5] + boardValues[8]) == i) {
                if (((TextView) gameBoardGrid.getChildAt(2)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(2)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(2)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(8)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(8)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(8)).setBackground(imageToPlay);
                    return;
                } else if (((TextView) gameBoardGrid.getChildAt(5)).getText().toString().equals(" ")) {
                    ((TextView) gameBoardGrid.getChildAt(5)).setText(characterToPlay);
                    (gameBoardImageGrid.getChildAt(5)).setBackground(imageToPlay);
                    return;
                }
            }

        }

    }




}
