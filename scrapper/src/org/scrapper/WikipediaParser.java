package org.scrapper;

/**
* Name: WikipediaParser
* Date: 05-01-2017
* Update: 14-01-2017
* Description: Wikipedia.org specifically parser object.
*/

public final class WikipediaParser extends ParserWithMenu{
    
    public static final WikipediaParser WIKI = new WikipediaParser();
    
    private WikipediaParser() {
        initTagsAndSource();
    }
    
    /**
    * 
    * SET THIS UP FOR THE WEBPAGE
    * IT IS SIMPLE BUT REQUIRES SOME RESEARCH ON THE SITE SOURCE-CODE.
    *
    */

    @Override
    public void initTagsAndSource() {
        setSource("https://pt.wikipedia.org/wiki/");
        setTag("span");
        setMainTag(getTag()+".mw-headline");
        setContentTag("#mw-content-text");
    }
    
    @Override
    public String RemoveUnnecessaryThings(String code) {
        return code.replace("<span><span>[</span>editar<span> | </span>editar código-fonte<span>]</span></span>", " ");
    }
    
}
