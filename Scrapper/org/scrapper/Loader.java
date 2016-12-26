package org.scrapper;

import org.jsoup.nodes.Document;

/**
* Nome: Loader
* Data: 09-08-2016
* Atualizado: 26-12-2016
* Descrição: Interface de carregamento e execução do objeto Parser.
*/

interface Loader {
    String MainParseMethod(String code);
    String LastParseMethod();
    String AlternativeParseMethod(String code);
    String Parsing(String query);
    String Core();
    String Title(Document doc);
    String[] Options(Document doc, String key);
    boolean Initialize(String url);
    boolean Verify();
    void Close();
} 
