package com.michael.educationaltictactoe;

import android.graphics.Color;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by cdu on 2016-08-05.
 */
public class ColorFunctions {

    static int sortCode = 2;
    static int sortCode0 = 0;
    static int sortCode1 = 1;
    static int sortCode2 = 2;
    static ArrayList<Integer> randomColorsArrayList = new ArrayList<Integer>(9);


    private static float[][] arrayHSVColors = new float[450][3];
    private static int count = 0;
    private static double contrastRatio = 0.0f;
    private static double minContrastRatio = 3.0;
    private static int textR = 255;
    private static int textG = 255;
    private static int textB = 255;

    private static final double RGB_THRESHOLD = 0.03928;
    private static final double RGB_NUMERATOR_1 = 12.92;
    private static final double RGB_OFFSET = 0.055;
    private static final double RGB_NUMERATOR_2 = 1.055;
    private static final double RGB_EXPONENT = 2.4;

    private static final double R_FACTOR = 0.2126;
    private static final double G_FACTOR = 0.7152;
    private static final double B_FACTOR = 0.0722;

    private static final double RGB_MAX = 255;


    private static double luminance(double valueR, double valueG, double valueB){
        double sRBG_R = valueR / RGB_MAX;
        double sRGB_R_base = (sRBG_R + RGB_OFFSET) / RGB_NUMERATOR_2;
        double sRBG_G = valueG / RGB_MAX;
        double sRGB_G_base = (sRBG_G + RGB_OFFSET) / RGB_NUMERATOR_2;
        double sRBG_B = valueB / RGB_MAX;
        double sRGB_B_base = (sRBG_B + RGB_OFFSET) / RGB_NUMERATOR_2;

        double R = sRBG_R <= RGB_THRESHOLD ? sRBG_R / RGB_NUMERATOR_1 : Math.pow(sRGB_R_base, RGB_EXPONENT);

        double G = sRBG_G <= RGB_THRESHOLD ? sRBG_G / RGB_NUMERATOR_1 : Math.pow(sRGB_G_base, RGB_EXPONENT);

        double B = sRBG_B <= RGB_THRESHOLD ? sRBG_B / RGB_NUMERATOR_1 : Math.pow(sRGB_B_base, RGB_EXPONENT);

        double luminance = R_FACTOR * R + G_FACTOR * G + B_FACTOR * B;
        return luminance;
    }


    //minimum contrastRatio is 7:1 (https://www.w3.org/TR/WCAG20-TECHS/G17.html)
    private static double contrastRatio(double textR, double textG, double textB, double backgroundR, double backgroundG, double backgroundB){

        double contrastRatio = (luminance(textR, textG, textB) + 0.05) / (luminance(backgroundR, backgroundG, backgroundB) + 0.05);
        contrastRatio = contrastRatio < 1 ? 1 / contrastRatio : contrastRatio;
        return contrastRatio;
    }



    private static void makeColorArray(){


        for(int i = 0; i < arrayHSVColors.length; i++){
            for(int j = 0; j < 3; j++){
                arrayHSVColors[i][j] = 0.0f;
            }
        }

        count = 0;
        for(short i = 0; i <= 255; i += 30){
            for(short j = 0; j <= 255; j += 10){
                for(short k = 0; k <= 255; k += 60){
                    contrastRatio = contrastRatio(textR,textG,textB, i,j,k);
                    if(contrastRatio >= minContrastRatio && contrastRatio <= 7.0){
                        Color.colorToHSV(Color.rgb(i,j,k), arrayHSVColors[count]);
                        count++;
                    }
                }
            }
        }
        sortColorArray();
    }


    private static void sortColorArray(){
        switch(sortCode){
            case 0: sortCode0 = 0; sortCode1 = 2; sortCode2 = 1; break;
            case 1: sortCode0 = 1; sortCode1 = 2; sortCode2 = 0; break;
            case 2: sortCode0 = 2; sortCode1 = 1; sortCode2 = 0; break;
            default: break;
        }

        java.util.Arrays.sort(arrayHSVColors, new java.util.Comparator<float[]>() {
            public int compare(float [] a, float [] b) {
                if(a[sortCode0] < b[sortCode0]){
                    return 1;
                }else if(a[sortCode0] > b[sortCode0]){
                    return -1;
                }else if(a[sortCode1] < b[sortCode1]){
                    return 1;
                }else if(a[sortCode1] > b[sortCode1]){
                    return -1;
                }else if(a[sortCode2] < b[sortCode2]){
                    return 1;
                }else if(a[sortCode2] > b[sortCode2]){
                    return -1;
                }
                return 0;
            }
        });
    }


    public static void setTextColors(GridLayout gameBoard){
        Random r = new Random(System.currentTimeMillis());
        makeColorArray();

        int color;

        randomColorsArrayList.add(r.nextInt(46));
        randomColorsArrayList.add(r.nextInt(46) + 46);
        randomColorsArrayList.add(r.nextInt(46) + 92);
        randomColorsArrayList.add(r.nextInt(46) + 138);
        randomColorsArrayList.add(r.nextInt(46) + 184);
        randomColorsArrayList.add(r.nextInt(46) + 230);
        randomColorsArrayList.add(r.nextInt(46) + 276);
        randomColorsArrayList.add(r.nextInt(46) + 322);
        randomColorsArrayList.add(r.nextInt(46) + 403);

        Collections.shuffle(randomColorsArrayList);

        for(int i = 0; i < 9; i++){
            color = Color.HSVToColor(arrayHSVColors[randomColorsArrayList.get(i)]);
            ((TextView)gameBoard.getChildAt(i)).setTextColor(color);
        }
    }



}
