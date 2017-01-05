package org.scrapper;

/**
* Name: WikipediaParser
* Date: 05-01-2017
* Update: 05-01-2017
* Description: Webpage specifically parser object.
*/

public final class WikipediaParser extends Parser{
    
    public static final WikipediaParser wiki = new WikipediaParser();
    
    private WikipediaParser() {
        initTagsAndSource();
    }

    @Override
    public void initTagsAndSource() {
        setSource("https://pt.wikipedia.org/wiki/");
        setTag("span");
        setMainTag(getTag()+".mw-headline");
        setContentTag("#mw-content-text");
    }
    
}
