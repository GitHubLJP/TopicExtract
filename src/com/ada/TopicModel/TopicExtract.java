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

        System.out.println("Begin Extracting Topic of POIs...");

        File file =new File("sql/");
        if  (!file.exists()  && !file.isDirectory()){
            System.out.println("MkDir sql/...");
            file .mkdir();
        }

        DataRead dr;
        Node info;

        String dictionary = "街路号里营区门环";
        double initRadio = 0.5;

        if(args.length > 0) {
            dr = new DataRead(args[0]);
            info = new Node(args[0]);
        }
        else {
            info = new Node();
            dr = new DataRead();
        }

        info.createNode();

        File insertTopic = new File("sql/insert_topic.sql");
        BufferedWriter writerTopic = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(insertTopic), "UTF-8"));

        File insertLabel = new File("sql/insert_label.sql");
        BufferedWriter writerLabel = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(insertLabel), "UTF-8"));

        List<String[]> POGList = new ArrayList<>();
        POGList = dr.readPOG(POGList);

        writerTopic.write("INSERT INTO `POG`.`Topic` VALUES ");
        writerLabel.write("INSERT INTO `POG`.`Label` VALUES ");

        System.out.println("Create insert_label.sql/...");
        for(int i = 0; i < POGList.size(); i++) {
            KeyWordExtract test = new KeyWordExtract(POGList.get(i),
                    dictionary, initRadio);
            String kw = test.keyWord();

            String temp = "";

            for(int j = 1; j < dr.label[i].length; j++) {
                if(dr.label[i][j] != -1) {
                    temp += dr.label[i][j - 1] + " ";
                }
                else {
                    temp += dr.label[i][j - 1];
                    if(i == 0)
                        writerLabel.write("('" + i  + "', '" + temp + "')");
                    else
                        writerLabel.write(",\n ('" + i  + "', '" + temp + "')");
                   break;
                }
            }

            if(i == 0) {
                System.out.println("Create insert_topic.sql/...");
                writerTopic.write("('" + i + "', '" + dr.logLat[i][0] +
                        "', '" + dr.logLat[i][1] + "', '" + kw +"')");
            }

            else
                writerTopic.write(",\n ('" + i + "', '" + dr.logLat[i][0] +
                        "', '" + dr.logLat[i][1] + "', '" + kw +"')");
        }
        writerTopic.write(";");
        writerLabel.write(";");

        writerLabel.close();
        writerTopic.close();

        System.out.println("End Extracting Topic of POIs!");

    }
}

