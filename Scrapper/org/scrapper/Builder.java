package org.scrapper;

import javax.swing.JOptionPane;
import org.jsoup.Jsoup;

/**
* Nome: Builder
* Data: 15-12-2016
* Atualizado: 24-12-2016
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
     * Método de exibição simples para quebra de linha.
     */
    public void print() {
        print("");
    }
    
    /**
     * Método simples de exibição de texto em console.
     * 
     * @param args argumentos de exibição.
     */
    @SuppressWarnings("InfiniteRecursion")
    public void print(String args) {
        try{
            if(args.startsWith("> ")) {
                args = ANSI_BLUE+args.replace("\n", "");
                System.out.println(args+"\n");
            }else
                System.out.println(ANSI_RESET+args);
        }catch(Exception ex){
            if(args != null)
                print("Erro: "+ex.getMessage()+"\n", "red");
            else
                print();
        }
    }
    
    /**
     * Método composto de exibição de texto colorido em console.
     * 
     * @param args argumentos de exibição.
     * @param color cor da exibição.
     */
    @SuppressWarnings("InfiniteRecursion")
    public void print(String args, String color) {
        try{
            if(args.startsWith("> ")) {
                args = ANSI_BLUE+args.replace("\n", "");
                System.out.println(args+"\n");
            }else
                switch(color) {
                    case "red": args = ANSI_RED+args.replace("\n", ""); break;
                    case "green": args = ANSI_GREEN+args.replace("\n", ""); break;
                    case "yellow": args = ANSI_YELLOW+args.replace("\n", ""); break;
                    case "blue": args = ANSI_BLUE+args.replace("\n", ""); break;
                    case "purple": args = ANSI_PURPLE+args.replace("\n", ""); break;
                    case "cyan": args = ANSI_CYAN+args.replace("\n", ""); break;
                    default: args = ANSI_RESET+args.replace("\n", "");
                }
                    System.out.println(args+"\n");
        }catch(Exception ex){
            print("Erro: "+ex.getMessage()+"\n", "red");
        }
    }
    
    /**
     * Método simples de exibição de texto em diálogo simples.
     * 
     * @param args argumentos de exibição.
     */
    public void show(String args) {
        try{
            JOptionPane.showMessageDialog(null, args);
        }catch(Exception ex){
            print("Erro: "+ex.getMessage()+"\n", "red");
        }
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
