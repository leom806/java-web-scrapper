package br.com.scrapper;

import java.io.IOException;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;

/**
* Nome: Builder
* Data: 15-12-2016
* Atualizado: 20-12-2016
* Descrição: Classe de extensão da Parser.
*/
public class Builder {
    
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
     * Método simples de exibição de texto em console.
     * 
     * @param args argumentos de exibição.
     */
    public void print(String args) {
        if(args.startsWith("> ")) {
            args = ANSI_BLUE+args.replace("\n", "");
            System.out.println(args+"\n");
        }else
            System.out.println(ANSI_BLACK+args);
    }
    
    /**
     * Método simples de exibição de texto em console.
     * 
     * @param args argumentos de exibição.
     * @param color cor da exibição.
     */
    public void print(String args, String color) {
        if(args.startsWith("> ")) {
            switch(color) {
                case "red": args = ANSI_RED+args.replace("\n", ""); break;
                case "green": args = ANSI_GREEN+args.replace("\n", ""); break;
                case "blue": args = ANSI_BLUE+args.replace("\n", ""); break;
                case "purple": args = ANSI_PURPLE+args.replace("\n", ""); break;
                default: args = ANSI_BLUE+args.replace("\n", ""); break;
            }
            
            System.out.println(args+"\n");
        }else
            System.out.println(ANSI_BLACK+args);
    }
    
    /**
     * Método simples de exibição de texto em diálogo simples.
     * 
     * @param args argumentos de exibição.
     */
    public void show(String args) {
        JOptionPane.showMessageDialog(null, args);
    }
    
    /**
     * Método simples de entrada em diálogo simples.
     * 
     * @param args argumentos de exibição.
     * @return String de retorno do usuário.
     */
    public String get(String args) {
        return JOptionPane.showInputDialog(null, args);
    }
    
    /**
     * Método simples de entrada de opções em diálogo simples.
     * 
     * @param args argumentos de exibição.
     * @param options argumentos das opções.
     * @return retorna inteiro da opção selecionada.
     */
    public String get(String args, Object[] options) {
        return JOptionPane.showInputDialog(null, args, "Seleção", 0, null, options, options[0]).toString();
    }
    
    /**
     * Método para limpeza de tags html usando a biblioteca Jsoup.
     * 
     * @param args documento que será limpado.
     * @return documento limpo.
     */
    public String clean(String args) {
        return Jsoup.parse(args).text();
    }
    
}
