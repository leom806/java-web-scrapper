package org.scrapper;

import java.io.IOException;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
* Name: Builder
* Date: 15-12-2016
* Update: 05-01-2017
* Description: Helper class.
*/
public final class Builder{

    /**
     * ANSI escape codes
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Prints text in the default output stream
     */
    public static void print() {
        print("");
    }
    
    /**
     * Prints text in the default output stream
     * @param args
     */
    public static void print(String[] args) {
        for(String arg : args)
            System.out.println(arg);
    }

    /**
     * Prints text in the default output stream
     *
     * @param args 
     */
    public static void print(String args) {
        try{
            if(args.startsWith("> ")) {
                args = ANSI_BLUE+args.replace("\n", "");
                System.out.println(args);
            }else
                System.out.println(ANSI_RESET+args);
        }catch(Exception ex){
            if(args != null)
                print(ex.getMessage()+"\n", "red");
        }
    }

    /**
     * Prints text in the default output stream
     *
     * @param args 
     * @param color 
     */
    public static void print(String args, String color) {
        try{
            switch(color) {
                case "red": args = ANSI_RED+args.replace("\n", ""); break;
                case "green": args = ANSI_GREEN+args.replace("\n", ""); break;
                case "yellow": args = ANSI_YELLOW+args.replace("\n", ""); break;
                case "blue": args = ANSI_BLUE+args.replace("\n", ""); break;
                case "purple": args = ANSI_PURPLE+args.replace("\n", ""); break;
                case "cyan": args = ANSI_CYAN+args.replace("\n", ""); break;
                default: args = ANSI_RESET+args.replace("\n", "");
            }
            if(args.startsWith("> "))
                System.out.println(args);
            else
                System.out.println(args+"\n");
        }catch(Exception ex){
            print(ex.getMessage()+"\n", "red");
        }
    }

    /**
     * Prints text in a jDialog
     *
     * @param args 
     */
    public static void show(String args) {
        try{
            JOptionPane.showMessageDialog(null, args);
        }catch(Exception ex){
            print(ex.getMessage()+"\n", "red");
        }
    }

    /**
     * Gets the input by a jDialog
     *
     * @param args 
     * @return 
     */
    public static String get(String args) {
        return JOptionPane.showInputDialog(null, args);
    }

    /**
     * Gets the input by a jDialog
     *
     * @param args 
     * @param options 
     * @return 
     */
    public static String get(String args, Object[] options) {
        return JOptionPane.showInputDialog(null, args, "Seleção", -1, null, options, options[0]).toString();
    }

    /**
     * Cleans text using Jsoup library
     *
     * @param args 
     * @return 
     */
    public static String clear(String args) {
        args = Jsoup.parse(args).text();
        
        // Removes the brackets and their contents
        char[] array = args.toCharArray();
        
        for(int i = 0; i < array.length; i++){
            if(array[i] == '[') {
                array[i] = '#';
                i++;
                try{
                    while(array[i] != ']'){
                        array[i] = '#';
                        i++;
                    }
                    array[i] = '#';
                }catch(ArrayIndexOutOfBoundsException e) { }
            } 
        }
        args = new String(array).replace("#", "");

        // Split into paragraphs
        if(args.contains("."))
            args = "   "+args.replace(".", ".\n\n   ");
        
        return args;
    }

    /**
     * Connects to the webpage.
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Document connect(String url) throws IOException{
        return Jsoup.connect(url).get();
    }
    
    
}
