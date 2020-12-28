package com.nth.game;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by NguyenTienHoa on 12/27/2020
 */

public class FileManagers {

    public static String readFile(Context context,String fileName){
        try{
            InputStream inputStream = context.getAssets().open(fileName+".txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return Arrays.toString(buffer);
        }catch (IOException e){}
        return "";
    }

    public static int[][] getMap(Context context,String fileName) {
       String map = readFile(context,fileName);
        try {
            String[] items = map.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
            int size = items.length;
            int [] arr = new int [size];
            int row = 0;
            for(int i=0; i<size; i++) {
                arr[i] = Integer.parseInt(items[i]);
                if (arr[i] == 10){
                    row ++;
                }
            }
            int col = size - row*2;
            int maps[][] = new int[row+1][col];
            row = 0;
            col = 0;
            for (int i = 0 ; i < size ; i ++) {
                if (arr[i] == 13 || arr[i] == 10) {
                    if (arr[i] == 10){
                        row++;
                    }
                } else {
                    maps[row][col] = arr[i] - 48;
                    col++;
                }
            }
            return maps;
        }catch (Exception e){
            e.printStackTrace();
        }
       return new int[0][0];
    }
}
