package com.ada.TopicModel;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljp on 12/20/15.
 * Intro: POI Organization Group(POG) read.
 * List<String[]> readPOG(List<String[]> POGList)
 * double round(double value, int scale, int roundingMode)
 */

class DataRead {
    final int max_entry = 1000;
    final int max_label = 60;
    protected static String INFO_PATH = "POIs.txt";
    protected static String charSetName = "utf-8";
    protected double[][] logLat= new double[max_entry][2];
    protected int[][] label = new int[max_entry][max_label];

    DataRead() {

    }

    DataRead(String info_path) {
        INFO_PATH = info_path;
    }

    void init() {
        for(int i = 0; i < label.length; i++)
            for(int j = 0; j < label[i].length; j++)
                label[i][j] = -1;
    }

    boolean isExist(int l, int[] lab) {
        for(int i = 0; i < lab.length; i++)
            if(l == lab[i]) return true;

        return false;
    }

    double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

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

        double sum = 0;
        int logLatCount = 0;
        int count = 0;

        int labelCount1 = 0;
        int labelCount2 = 0;
        int tempLabel;

        init();

        String tmp = "";

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(" +");
            if(!flag_read) {
                if(!flag_firstLine) {
                    flag1 = Integer.parseInt(data[4]);
                    tempLabel = Integer.parseInt(data[3]);

                    sum += Double.parseDouble(data[0]);
                    logLat[logLatCount][0] = Double.parseDouble(data[1]);
                    count++;

                    if(!isExist(tempLabel, label[labelCount1]))
                        label[labelCount1][labelCount2++] = tempLabel;


                    POGTmp.add(data[2]);
                    flag_read = true;
                }
                else {
                    flag1 = Integer.parseInt(data[4]);
                    tempLabel = Integer.parseInt(data[3]);
                    if (flag2 == flag1) {
                        sum += Double.parseDouble(data[0]);
                        logLat[logLatCount][0] = Double.parseDouble(data[1]);
                        count++;

                        if(!isExist(tempLabel, label[labelCount1]))
                            label[labelCount1][labelCount2++] = tempLabel;

                        POGTmp.add(data[2]);
                        flag_read = true;
                    } else {
                        String[] POG = (String[]) POGTmp.toArray(new String[0]);
                        flag_read = false;
                        POGTmp.clear();

                        logLat[logLatCount++][1] = round(sum / count, 4,
                                BigDecimal.ROUND_HALF_DOWN);
                        sum =0;
                        count = 0;

                        System.out.println(labelCount1 + ": " + labelCount2);
                        labelCount1++;
                        labelCount2 = 0;
                        if(!isExist(tempLabel, label[labelCount1]))
                            label[labelCount1][labelCount2++] = tempLabel;

                        POGTmp.add(data[2]);
                        POGList.add(POG);
                        flag2 = flag1;
                        continue;
                    }
                }
                flag_firstLine = true;

            } else {
                flag2 = Integer.parseInt(data[4]);
                tempLabel = Integer.parseInt(data[3]);

                if (flag1 != flag2) {
                    String[] POG = (String[]) POGTmp.toArray(new String[0]);
                    flag_read = false;
                    POGTmp.clear();

                    logLat[logLatCount++][1] = round(sum / count, 4,
                            BigDecimal.ROUND_HALF_DOWN);
                    sum = 0;
                    count = 0;

                    labelCount1++;
                    labelCount2 = 0;
                    if(!isExist(tempLabel, label[labelCount1]))
                        label[labelCount1][labelCount2++] = tempLabel;

                    POGTmp.add(data[2]);
                    POGList.add(POG);
                } else {
                    sum += Double.parseDouble(data[0]);
                    logLat[logLatCount][0] = Double.parseDouble(data[1]);
                    count++;

                    if(!isExist(tempLabel, label[labelCount1]))
                        label[labelCount1][labelCount2++] = tempLabel;

                    POGTmp.add(data[2]);
                }
            }
        }
        logLat[logLatCount][1] = round(sum / count, 4,
                BigDecimal.ROUND_HALF_DOWN);

        String[] POG = (String[]) POGTmp.toArray(new String[POGTmp.size()]);
        POGTmp.clear();
        POGList.add(POG);
        reader.close();
        return POGList;
    }
}
