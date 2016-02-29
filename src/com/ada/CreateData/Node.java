package com.ada.CreateData;

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

    static void createNode() throws IOException {

        int i = -1;
        File file = new File("result.txt");
        File file1 = new File("test.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file1), "UTF-8"));
        writer.write("var info_kw = new Array();");

        String line = null;
        int flag = -1;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(" ");
            if(Integer.parseInt(data[4]) != flag) {
                i++;
                flag = Integer.parseInt(data[4]);
                count = 0;
                writer.write("info_kw[" + i + "] = new Array();\n");
                writer.write("info_kw[" + i + "]" + "[" + count + "]" + " = " + "[" + data[0] + ", "
                        + data[1] + ", " + "\"" + data[2] + "\"" + ", " + data[3]
                        + ", " + data[4] + "];" + "\n");

                count++;
            } else {
                writer.write("info_kw[" + i + "]" + "[" + count + "]" + " = " + "[" + data[0] + ", "
                    + data[1] + ", " + "\"" + data[2] + "\"" + ", " + data[3]
                    + ", " + data[4] + "];" + "\n");
                count++;
            }


        }
        writer.close();
        return;
    }

    public static void main(String[] args) throws IOException {
        createNode();
    }
}