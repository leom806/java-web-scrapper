package org.scrapper;

import org.jsoup.nodes.Document;

/**
* Name: Loader
* Date: 09-08-2016
* Update: 27-12-2016
* Description: Parser's interface.
*/

interface Loader {

    String VERSION = "1.0.5.4";

    String MainParseMethod(String code);
    String LastParseMethod();
    String AlternativeParseMethod(String code);
    String Parsing(String query) throws Exception;
    String Core() throws Exception;
    String Title(Document doc);
    String[] Options(Document doc);
    boolean Initialize(String url) throws Exception;
    boolean Verify() throws Exception;
    void Close();
}
