package com.ada.TopicModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljp on 12/20/15.
 * Intro: POI Organization Group(POG) read.
 * List<String[]> readPOG(List<String[]> POGList)
 *
 */

class DataRead {
    private static String INFO_PATH = "result.txt";
    //private static String INFO_PATH = "simplePOG.txt";
    //private static String JS_INFO = "createGroup1.js";
    private static String charSetName = "utf-8";

    //以info[i][4]为一个组读取数据
    public List<String[]> readPOG(List<String[]> POGList) throws IOException {
        File f = new File(INFO_PATH);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(f), charSetName);
        BufferedReader reader = new BufferedReader(isr);

        String line;
        ArrayList POGTmp = new ArrayList();
        boolean flag_read = false;
        boolean flag_firstLine = false;
        int flag1 = 0;
        int flag2 = 0;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(" +");
            if(!flag_read) {
                if(!flag_firstLine) {
                    flag1 = Integer.parseInt(data[4]);
                    POGTmp.add(data[2]);
                    flag_read = true;
                }
                else {
                    flag1 = Integer.parseInt(data[4]);
                    if (flag2 == flag1) {
                        POGTmp.add(data[2]);
                        flag_read = true;
                    } else {
                        String[] POG = (String[]) POGTmp.toArray(new String[0]);
                        flag_read = false;
                        POGTmp.clear();
                        POGTmp.add(data[2]);
                        POGList.add(POG);
                        flag2 = flag1;
                        continue;
                    }
                }
                flag_firstLine = true;

            } else {
                flag2 = Integer.parseInt(data[4]);

                if(flag1 != flag2) {
                    String[] POG = (String[]) POGTmp.toArray(new String[0]);
                    flag_read = false;
                    POGTmp.clear();
                    POGTmp.add(data[2]);
                    POGList.add(POG);
                } else {
                    POGTmp.add(data[2]);
                }
            }
        }
        String[] POG = (String[]) POGTmp.toArray(new String[0]);
        POGTmp.clear();
        POGList.add(POG);
        reader.close();
        return POGList;
    }
}
