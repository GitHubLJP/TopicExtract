package com.ada.TopicModel;

/**
 * Created by ljp on 12/18/15.
 *
 */


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TopicExtract {

    public static void main(String[] args) throws IOException{
        File file = new File("target.txt");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8"));
        writer.write("var POGkw = new Array();\n");

        String dictionary = new String("街路号里营区门环");
        double initRadio = 0.5;
        DataRead dr = new DataRead();
        List<String[]> POGList = new ArrayList<>();

        POGList = dr.readPOG(POGList);

        int count = 0;
        for(int i = 0; i < POGList.size(); i++) {
            KeyWordExtract test = new KeyWordExtract(POGList.get(i), dictionary, initRadio);
            String kw = test.keyWord();
            writer.write("POGkw[" + i + "] = [" + dr.logLat[i][0] + ", " + dr.logLat[i][1] + ", '" + kw +  "'];\n");

//            for(int j = 0; j < POGList.get(i).length; j++) {
//                System.out.println("info[" + i + "][" + j + "]:" + POGList.get(i)[j]);
//                count++;
//            }
        }
        writer.close();
        System.out.println(count);
    }
}

