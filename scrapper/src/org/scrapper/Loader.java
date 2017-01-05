package org.scrapper;

import org.jsoup.nodes.Document;

/**
* Name: Loader
* Date: 09-08-2016
* Update: 27-12-2016
* Description: Parser's interface.
* 
* Version Update: 05-01-2017
* 
*/

public interface Loader {

    public String VERSION = "1.0.5.5";

    public String MainParseMethod(String args);
    public String LastParseMethod();
    public String AlternativeParseMethod(String args);
    public String Parsing(String query) throws Exception;
    public String Core() throws Exception;
    public String Title(Document doc);
    public String[] Options(Document doc);
    public boolean Initialize(String url) throws Exception;
    public boolean Verify() throws Exception;
    public void Close();
}
