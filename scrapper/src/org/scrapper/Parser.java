package org.scrapper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
* Name: Parser
* Date: 15-12-2016
* Update: 27-12-2016
* Description: Conversion object class.
*/
public class Parser extends Builder implements Loader{

    /*
    * =========================    VARIABLES    ================================
    */

    private boolean status = false, display = false;
    private String aim = null, nextOption = null, searchQuery = null, content = null;
    private String[] allOptions = null;
    private Document code = null, doc = null;

    /*
    * =========================    CONSTANTS    ================================
    */

    private final String MAIN_TAG = "span.mw-headline"; // tag and id or class that indicates main titles in the page
    private final String CONTENT_TAG = "#mw-content-text"; // id or class that indicates the content to scrap
    private final String SOURCE = "https://pt.wikipedia.org/wiki/"; // Website to scrap
 
    /*
    * =========================    MESSAGES    =================================
    */
    
    private final String INIT_MESSAGE = " JScrapper "+Loader.VERSION+".";
    private final String PARSING_ERROR_MESSAGE = "Error in scrapping proccess.";
    private final String NOT_FOUND_MESSAGE = "Not found.";
    private final String NULL_ARGS_ERROR = "Should not use null arguments here.";
    private final String CONNECTION_FAILED_MESSAGE = "Could not connect to the page.";
    private final String ALL_OPTIONS_MESSAGE = "See All";
    private final String NOT_CONNECTED_MESSAGE = "Parser is not connected to the page.";
    private final String UNKNOW_ERROR_MESSAGE = "Unknow error.";
    
    /*
    * =========================    CONSTRUCTORS    =============================
    */

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Parser() {
        this(false, false);
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Parser(boolean status, boolean display) {
        print(INIT_MESSAGE);
        this.status = status;
        this.display = display;
    }

    /*
    * =====================    GETTERS & SETTERS    ============================
    */

    public String getSource() {
        return SOURCE;
    }

    public String[] getOptions() {
        if (allOptions != null)
            return allOptions;
        return new String[]{NOT_FOUND_MESSAGE};
    }

    /*
    * ===========================    METHODS    ================================
    */

    /**
     * Mainly parse method.
     *
     * @param code
     * @return
     */
    @Override
    public String MainParseMethod(String code) {
        if(status) {
            print("> Main method.");
        }
        if(display) print(Title(doc)+"\n");

        Whitelist wl = new Whitelist();
        wl.addTags("span", "p");

        // Clean using the allowed tags of the whitelist
        String clean = Jsoup.clean(code, wl);

        /*
        * 
        * SET THIS UP FOR THE WEBPAGE
        * IT SIMPLE BUT REQUIRES SOME RESEARCH ON THE SITE SOURCE-CODE
        *
        */
        clean = clean.replace("<span><span>[</span>editar<span> | </span>editar código-fonte<span>]</span></span>", " ");

        // YOU MUST CHANGE THIS TAG
        String ocurrency = "<span>"+aim+"</span>";
        String limit = "<span>"+nextOption+"</span>";

        clean = clean.substring(clean.lastIndexOf(ocurrency)+ocurrency.length());
        clean = clean.replace("\n", "ʘ"); // Replaces \n with a joker to keep the \n after

        if(clean.contains(limit))
            clean = clean.substring(0, clean.indexOf(limit));

        content = clear(clean).replace("ʘ", "\n");

        if(display) print(content);

        return content;
    }

    /**
     * Alternatively parse method, used if main fails; less accurate.
     *
     * @param code
     * @return
     */
    @Override
    public String AlternativeParseMethod(String code) {
        try {
            if(code.contains(aim) && code.contains("</p>")) {
                if(status) {
                    print("> Alternative method.");
                }
                String minified = code.substring(code.indexOf(aim), code.length());
                code = minified.substring(0, minified.indexOf("</p>"));
                content = clear(code); // Clear tags html off the code.
                
                if(display) print(Title(doc)+"\n");
                if(display) print(content+"\n");
            }
        }catch(Exception ev) {
            print(ev.getMessage()+"\n", "red");
            return PARSING_ERROR_MESSAGE;
        }

        return content;
    }

    /**
     * Used when the user selects the first option; brings up all content of the page.
     *
     * @return
     */
    @Override
    public String LastParseMethod() {
        if(status) {
            print("> Last method.");
        }
        if(display) print(Title(doc)+"\n");

        content = doc.select(CONTENT_TAG).select("p").toString().replace("\n", "ʘ");
        content = clear(content).replace("ʘ", "\n");

        if(display) print(content);

        return content;
    }

    /**
     * Prepares and calls the Core of the scrap process
     *
     * @param query
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public String Parsing(String query) throws Exception, NullPointerException{

        // Runs the scrapping core
        try{
            searchQuery = query;
            if(Initialize(SOURCE+searchQuery)){  // throws IOException
                if(status) print("> Initialized.");
                if(status) print("> Source: "+SOURCE);
                // Gets the code and parse it
                doc = Jsoup.parse(code.toString());
                return Core(); 
            }else{
                return NOT_FOUND_MESSAGE;
            }
            
        }catch(IOException ex){
            throw new IOException("Parsing error\n"+NOT_CONNECTED_MESSAGE);
            
        }catch(NullPointerException ex){
            throw new NullPointerException("Parsing error\n"+NULL_ARGS_ERROR);
            
        }catch(final Exception ex){
            throw new Exception("Parsing error\n"+UNKNOW_ERROR_MESSAGE);  // rethrow 
        }
    }

    /**
     * Parsing's core method.
     *
     * @return
     * @throws NullPointerException
     * @throws Exception
     */
    @Override
    public String Core() throws NullPointerException, Exception{

        // Show options dialog
        try {
            // Options selection dialog
            Object[] opcoes = Options(doc);
            aim = get("Options:", opcoes);
            int i = 0;
            for(Object opcao : opcoes) {
                if(opcao.equals(aim)) break;
                else i++;
            }
            if(!aim.equals(opcoes[opcoes.length-1]))
                nextOption = opcoes[i+1].toString().replace(" - ", "");
            if (status) print("> Searching:"+aim);

        }catch (NullPointerException ex) {
            throw new NullPointerException("Core Error\n"+NULL_ARGS_ERROR);
        }

        // Parser only if verification returns true
        if(Verify()) {

            if(status) print("> Verified.");
            if(status) print("> Showing options. ");

            if(display) {
                print(); // new line
                for(String opcao : Options(doc)) {
                    print(opcao);
                }
            }

            // Gets p tags
            Elements tags = doc.body().getElementsByTag("p");
            String text = tags.toString();

            // Gets content 
            String all = doc.body().select(CONTENT_TAG).toString();

           /**
            * Scrapping core process.
            */

           if(!aim.equals(ALL_OPTIONS_MESSAGE)) {

                if(all.contains(aim)) {
                    /*
                    * Principal processo de raspagem. Usa as opcoes para buscar.
                    *
                    * Gera um novo código limpo por uma Whitelist do Jsoup.
                    * Cria substring partindo do titulo escolhido e retirando caracter que
                    * pode gerar erro na próxima substring.
                    * Cria a última substring usando o próximo item como limite.
                    */

                    content = MainParseMethod(all);
                }else{
                    /*
                    * Processo de raspagem alternativo. Usa parte do texto para buscar.
                    *
                    * Primeiro é cortado da busca até o final. Depois pegamos esse corte
                    * e limitamos até a próxima tag. Então converte-se novamente com o Jsoup
                    * para poder usar o método text().
                    */

                    content = AlternativeParseMethod(text);
                }

            }else{
               content = LastParseMethod();
            }

            if(content != null)
                return content;
        }

        throw new Exception(PARSING_ERROR_MESSAGE);

    }

    /**
     * Gets the webpage title.
     *
     * @param code
     * @return
     */
    @Override
    public String Title(Document code){
        if (code != null)
            return code.title();
        else 
            return NULL_ARGS_ERROR;
    }

    /**
     * Gets options in the page.
     *
     * @param doc
     * @return
     */
    @Override
    public String[] Options(Document doc) {
        String[] options = doc.select(MAIN_TAG).toString().split("\n");
        allOptions  = new String[options.length+1];
        allOptions[0] = " - "+ALL_OPTIONS_MESSAGE;
        for(int i = 0; i < options.length; i++) {
            allOptions[i+1] = " - "+clear(options[i]);
        }
        return allOptions;
    }

    /**
     * Connects to the webpage.
     *
     * @param url
     * @return
     * @throws java.lang.Exception
     * @throws java.io.IOException
     */
    @Override
    public boolean Initialize(String url) throws Exception, IOException{
        try {
            code = connect(url);
            return true;
            
        } catch (IOException ex) {
            throw new IOException("Initializing error\n"+CONNECTION_FAILED_MESSAGE);
            
        } catch(Exception ev) {
            throw new Exception("Initializing error\n"+UNKNOW_ERROR_MESSAGE);
        }
    }

    /**
     * Verify the code.
     *
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public boolean Verify() throws Exception, NullPointerException{

        aim = aim.replace(" - ", "");

        try {
            boolean verify_code = code.toString().isEmpty();
        }catch(NullPointerException ex) {
            throw new NullPointerException("Verify error\n"+NULL_ARGS_ERROR);
        }

        if(aim == null) {
            throw new NullPointerException("Verify error\n"+NULL_ARGS_ERROR);
        }

        if(SOURCE == null) {
            throw new NullPointerException("Verify error\n"+NULL_ARGS_ERROR);
        }

        if(!code.toString().contains(aim) && !aim.equals(ALL_OPTIONS_MESSAGE)) {
            throw new Exception("Verify error\n"+NOT_FOUND_MESSAGE);
        }

        return true;
    }

    /**
     * Finalizes all variables.
     */
    @Override
    public void Close() {
        allOptions = null;
        aim = nextOption = searchQuery = content = null;
        code = doc = null;
    }

}
