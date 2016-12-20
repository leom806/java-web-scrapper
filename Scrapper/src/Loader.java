package br.com.scrapper;

import org.jsoup.nodes.Document;

/*
* Nome: Loader
* Data: 09-08-2016
* Atualizado: 20-12-2016
* Descrição: Interface de carregamento, lista os métodos sobrecarregados que 
* fazem a conversão e também o método de liberação dos recursos que foram alocados.
*/

interface Loader {
    
    /* 
    * Variável que armazena o conteúdo final da raspagem, usada no retorno dos métodos.
    */
    StringBuilder CONTENT = new StringBuilder();

    String Title(Document code);
    int Parsing(boolean status, boolean display);
} 
