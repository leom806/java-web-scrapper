package org.scrapper;

import org.jsoup.nodes.Document;

/**
* Name: Parser
 Date: 09-08-2016
 Update: 14-01-2017
 Description: Parser's interface.
* 
* Version Update: 05-01-2017
* 
*/
public interface Parser {

    public String VERSION = "1.0.5.6";

    public String Title(Document doc);
    public String Parse(String query);
}
