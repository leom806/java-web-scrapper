package br.com.scrapper;

import org.jsoup.nodes.Document;

/*
* Nome: Loader
* Data: 09-08-2016
* Atualizado: 20-12-2016
* Descrição: Interface de carregamento e execução do objeto Parser;
*/

interface Loader {
    boolean Verify();
    String Title(Document code);
    String Parsing(boolean status, boolean display);
} 
