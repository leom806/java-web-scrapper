package br.com.scrapper;

import java.io.*;

/*
*
* Nome: Loader
* @author Leonardo Momente
* @version 1.0.5.0
* Data: 09-08-2016
* Atualizado: 20-12-2016
* Descrição: Interface de carregamento, lista os métodos sobrecarregados que 
* fazem a conversão e também o método de liberação dos recursos que foram alocados.
*/

interface Loader {
    
    /* 
    * Variável que armazena o conteúdo final da raspagem, usada no 
    * retorno dos métodos.
    */
    String content = "";
    
    /**
    * Opções de chamada dos métodos.
    *
    *   status:
    *   exibe passo a passo do processo de raspagem
    *   
    *   display:
    *   exibe o conteúdo raspado
    *   
    *   default:
    *   será retornado String com conteudo raspado
    *
    */
    String ParseCode(String toFind, File path, boolean status, boolean display);
    String ParseCode(String toFind, String source, boolean status, boolean display);
    String ParseCode(boolean status, String toFind, File path);
    String ParseCode(boolean status, String toFind, String source);
    String ParseCode(String toFind, File path, boolean display);
    String ParseCode(String toFind, String source, boolean display);
    String ParseCode(String toFind, File path);
    String ParseCode(String toFind, String source);

    int Close();
} 
