package com.fbs.fe.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LineGetter {
    public String readFromLine(int lineId, String filePath){

        String returnString = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int i = 0;
            while (i != lineId) {
                line = reader.readLine();
                returnString = line;
                i++;
                if (i > 50) {
                    break;
                }
            }

            reader.close();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String readFromLine(int lineId, File file){

        String returnString = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int i = 0;
            while (reader.ready()) {
                line = reader.readLine();
                if (i == lineId){
                    returnString = line;
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

}
