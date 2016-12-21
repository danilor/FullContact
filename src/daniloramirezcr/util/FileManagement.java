package daniloramirezcr.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/*
* This class will take charge of all write, read and exists process in the system.
* */
public class FileManagement {

    private static String default_encoding = "UTF-8";

    public static Boolean verifyFolderOrCreate(String name){
        File directory = new File(String.valueOf(name));
        if (! directory.exists()){
            directory.mkdir();
            return true;
        }
        return false;
    }

    /*
    * This function will print the file using the default encoding for the class
    * */
    public static void writeToFile(String filename, String content){
        FileManagement.writeToFile( filename , content , FileManagement.default_encoding );
    }

    /*
        In this method we can print to a file and indicate the encoding.
    * */
    public static void writeToFile(String filename, String content, String encoding){
        try{
            PrintWriter writer = new PrintWriter( filename , encoding  );
            writer.print( content );
            writer.close();
        } catch (IOException e) {

        }
    }
}
