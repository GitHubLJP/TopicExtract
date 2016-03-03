package com.ada.TopicModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by ljp on 12/29/15.
 *
 */

public class Node {

    protected static String POIFile = "POIs.txt";

    Node() {

    }

    Node(String poiPath) {
        POIFile = poiPath;
    }

    void createNode() throws IOException {

        int i = -1;
        File file = new File(POIFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF-8"));

        File insertInfo = new File("sql/insert_info.sql");
        BufferedWriter writerInfo = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(insertInfo), "UTF-8"));

        String line;
        int flag = -1;
        int count = 0;
        writerInfo.write(" INSERT INTO `POG`.`Info`  VALUES ");
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(" ");
            if(Integer.parseInt(data[4]) != flag) {
                i++;
                flag = Integer.parseInt(data[4]);
                count = 0;

                if(count + i == 0)  {
                    writerInfo.write("('" + data[4] + "', '" +  data[0] + "', '" +
                            data[1] + "', '" + data[2] + "', '" + data[3] + "')");
                } else {
                    writerInfo.write(",\n('" + data[4] + "', '" +  data[0] + "', '" +
                            data[1] + "', '" + data[2] + "', '" + data[3] + "')");
                }

                count++;
            } else {
                writerInfo.write(",\n('" + data[4] + "', '" +  data[0] + "', '" +
                        data[1] + "', '" + data[2] + "', '" + data[3] + "')");

                count++;
            }


        }

        writerInfo.write(";");
        writerInfo.close();
    }
}
