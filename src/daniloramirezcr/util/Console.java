package daniloramirezcr.util;

import java.util.Scanner;

/**
 * Created by danilo on 14/12/2016.
 */
public class Console {
    public static void l(String t){
        System.out.println( t );
    }
    public static void w(String t){
        System.out.print( t );
    }
    public static void e( String t ){
        System.err.println( t );
    }
    public static String readString(String m){
        Console.w( m );
        Scanner scan= new Scanner(System.in);
        String text = scan.nextLine();
        return text;
    }
    public static void space(){
        System.out.println();
    }
    public static void space(int times){
        for( int i = 0 ; i < times ; i++ ){
            System.out.println();
        }
    }


}
