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

public class Topic {

    static void createTopic() throws IOException {

        int i = 0;
        File file = new File("kw.txt");
        File file1 = new File("test.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file1), "UTF-8"));
        writer.write("var info_kw = new Array();");

        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(" ");
            writer.write("info_kw[" + i + "]" + " = " + "[" + data[0] + "];" + "\n");
            i++;
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        createTopic();
    }
}