package com.ada.TopicModel;

/**
 * Created by ljp on 12/18/15.
 *
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TopicExtract {

    public static void main(String[] args) throws IOException{
        String dictionary = new String("街路号里营区门环");
        double initRadio = 0.5;
        DataRead dr = new DataRead();
        List<String[]> POGList = new ArrayList<>();

        POGList = dr.readPOG(POGList);
        int count = 0;
        for(int i = 0; i < POGList.size(); i++) {
            KeyWordExtract test = new KeyWordExtract(POGList.get(i), dictionary, initRadio);
            String kw = test.keyWord();
            System.out.println(kw);
//            for(int j = 0; j < POGList.get(i).length; j++) {
//                System.out.println("info[" + i + "][" + j + "]:" + POGList.get(i)[j]);
//                count++;
//            }
        }
        //System.out.println(count);
    }
}

