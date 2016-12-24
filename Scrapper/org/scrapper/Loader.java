package org.scrapper;

import org.jsoup.nodes.Document;

/**
* Nome: Loader
* Data: 09-08-2016
* Atualizado: 23-12-2016
* Descrição: Interface de carregamento e execução do objeto Parser.
*/

interface Loader {
    boolean Verify();
    String Title(Document doc);
    String[] Options(Document doc, String key);
    String Parsing(boolean status, boolean display);
} 
