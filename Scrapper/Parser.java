package br.com.scrapper;

import java.io.*;

/*
* Nome: Parser
* Data: 15-12-2016
* Atualizado: 20-12-2016
* Descrição: Objeto de conversão dos dados.
*/
abstract class Parser implements Loader{ 

    public static String toFind = null;
    protected String code = CONTENT.toString();
    
    /* 
     * Conversão de dados e raspagem de conteúdo.
     */
    public int Parsing(boolean status, boolean display) {
        
        // Faz a raspagem apenas se passar na verificação.
        if(Verify()) {
            
            
            
        }
        
        return -1;
    }
    
    
    /* 
     * Verifica o código para prosseguir com a raspagem.
     */
    private boolean Verify() {
        
        if(code.isEmpty()) {
            System.out.println("Código vazio.");
            return false;
        } 
        
        if(!code.contains(toFind)) {
            System.out.println("Não foi encontrado.");
            return false;
        }
        
        return true;
    }

}