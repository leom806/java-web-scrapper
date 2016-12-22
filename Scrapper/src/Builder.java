package br.com.scrapper;

import javax.swing.JOptionPane;

/**
* Nome: Builder
* Data: 15-12-2016
* Atualizado: 20-12-2016
* Descrição: Classe de extensão da Parser.
*/
public class Builder {
    
    /**
     * Método simples de exibição de texto em console.
     * 
     * @param args argumentos de exibição.
     */
    public void print(String args) {
        System.out.println(args);
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
    
}
