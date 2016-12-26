package org.scrapper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
* Nome: Parser
* Data: 15-12-2016
* Atualizado: 26-12-2016
* Descrição: Objeto de conversão dos dados.
*/
public class Parser extends GUIStream implements Loader{ 

    /*
    * =========================    VARIABLES    ================================
    */
    
    private String AIM = null;
    private String NEXT = null;
    private String SEARCH_QUERY = null;
    
    private String[] completo = null;
    private Document code = null, doc = null;
    private boolean status = false, display = false;
    
    private static String CONTENT = null;
    
    /*
    * =========================    CONSTANTS    ================================
    */
    
    private final String MAIN_TAG = "span.mw-headline"; // Tag principal da fonte de buscas. 
    private final String CONTENT_TAG = "#mw-content-text"; // Tag de conteudo.
    private final String SOURCE = "https://pt.wikipedia.org/wiki/"; 
    
    private static final String VERSION = "1.0.5.4";
    private static final String PARSING_ERROR = "Erro durante processo de raspagem.";
    
    /*
    * =========================    CONSTRUCTORS    =============================
    */
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Parser() {
        print("JScrapper "+VERSION);
    }
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Parser(boolean status, boolean display) {
        print("JScrapper "+VERSION);
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
        if (completo != null)
            return completo;
        return new String[]{""};
    }
    
    /*
    * ===========================    METHODS    ================================
    */

    /**
     *  Método de raspagem principal.
     *
     * @param code
     * @return 
     */
    @Override
    public String MainParseMethod(String code) {
        if(status) {
            print("> Metodo Principal.");
        }
        if(display) print(Title(doc)+"\n");

        Whitelist wl = new Whitelist();
        wl.addTags("span", "p");

        String clean = Jsoup.clean(code, wl);

        clean = clean.replace("<span><span>[</span>editar<span> | </span>editar código-fonte<span>]</span></span>", " ");

        String ocurrency = "<span>"+AIM+"</span>";
        String limit = "<span>"+NEXT+"</span>";

        clean = clean.substring(clean.lastIndexOf(ocurrency)+ocurrency.length()).replace("\n", "ʘ");
        
        try{
            clean = clean.substring(0, clean.indexOf(limit));
        }catch(StringIndexOutOfBoundsException ex){
            
        }
        
        String content = Jsoup.parse(clean).text().replace("ʘ", "\n");

        if(display) print(content);
        CONTENT = content;
        
        return CONTENT;
    }
    
    /**
     *  Método de raspagem alternativo.
     *
     * @param code
     * @return 
     */
    @Override
    public String AlternativeParseMethod(String code) {
        try {               
            if(code.contains(AIM)) {
                if(status) {
                    print("> Metodo Alternativo.");
                }
                String minified = code.substring(code.indexOf(AIM), code.length());
                code = minified.substring(0, minified.indexOf("</p>"));
                code = clean(code); // Limpeza das tags html.
                if(display) print(Title(doc)+"\n");
                if(display) print(code+"\n");
                // Salva conteúdo para retorno em StringBuilder static.
                CONTENT = code;
            }
        }catch(StringIndexOutOfBoundsException ex) {
            print("Erro em limite de String.", "red");
            return PARSING_ERROR;
        }catch(Exception ev) {
            print("Erro: "+ev.getMessage()+"\n", "red");
            return PARSING_ERROR;
        }
        
        return CONTENT;
    }
    
    @Override
    public String LastParseMethod() {
        if(status) {
            print("> Metodo Final.");
        }
        if(display) print(Title(doc)+"\n");

        String content = doc.select(CONTENT_TAG).select("p").toString().replace("\n", "ʘ");
        content = Jsoup.parse(content).text().replace("ʘ", "\n");

        if(display) print(content);

        CONTENT = content;
        return CONTENT;
    }
    
    /**
    * Conversão de dados e raspagem de conteúdo.
    * 
    * @param query página que será buscada na fonte.
    * @return conteúdo da raspagem.
    */
    @Override
    public String Parsing(String query) {

        if(status) print("> Inicializado.");
                    
        SEARCH_QUERY = query;
        
        if(SEARCH_QUERY == null) return PARSING_ERROR;
        
        try{
            Initialize(SOURCE+SEARCH_QUERY);
            if(status) print("> Fonte: "+SOURCE);
        }catch(Exception ex){
            print("Erro em inicialização: "+ex.getMessage()+"\n", "red");
            return PARSING_ERROR;
        }
        
        // Gera um Document com o código da URL da fonte
        try{
            doc = Jsoup.parse(code.toString());
        }catch(NullPointerException ex){
            print("Erro em conversão: "+ex.getMessage()+"\n", "red");
            return PARSING_ERROR;
        }
        
        // Exibe as opções e roda o centro de raspagem.
        return Core();
    }
    
    /**
     * Centro da raspagem.
     * 
     * @return 
     */
    @Override
    public String Core() {
        
        // Mostra janela de busca e a opção "Ver Opções"
        try {
            
            // Diálogo com as opções para seleção
            Object[] opcoes = Options(doc, MAIN_TAG);
            AIM = get("Opções:", opcoes);
            int i = 0;
            for(Object opcao : opcoes) {
                if(opcao.equals(AIM)) break;
                else i++;
            }
            if(!AIM.equals(opcoes[opcoes.length-1]))
                NEXT = opcoes[i+1].toString().replace(" - ", "");
            if (status) print("> Buscando:"+AIM);
            
        }catch (NullPointerException ex) {
            print("Não foi possível conectar\n", "red");
            return PARSING_ERROR;
        }
        
        // Faz a raspagem apenas se passar na verificação.
        if(Verify()) {
            
            if(status) print("> Verificado.");
            if(status) print("> Exibindo Opcoes. ");
            
            // Exibe as opções
            if(display) {
                print(); // Quebra de linha
                for(String opcao : Options(doc, MAIN_TAG)) {
                    print(opcao);
                }
            }
            
            // Recebe as tags <p> e gera uma String 
            Elements tags = doc.body().getElementsByTag("p");
            String text = tags.toString();
            
            // Gera String com todo o html do conteúdo
            String all = doc.body().select(CONTENT_TAG).toString();

           /**
            * Núcleo do processo de raspagem.
            */
           
           if(!AIM.equals("Ver Tudo")) {
           
                if(all.contains(AIM)) { 
                    /*
                    * Principal processo de raspagem. Usa as opcoes para buscar.
                    * 
                    * Gera um novo código limpo por uma Whitelist do Jsoup.
                    * Cria substring partindo do titulo escolhido e retirando caracter que
                    * pode gerar erro na próxima substring.
                    * Cria a última substring usando o próximo item como limite.
                    */
                    
                    CONTENT = MainParseMethod(all);
                }else{
                    /*
                    * Processo de raspagem alternativo. Usa parte do texto para buscar.
                    * 
                    * Primeiro é cortado da busca até o final. Depois pegamos esse corte 
                    * e limitamos até a próxima tag. Então converte-se novamente com o Jsoup
                    * para poder usar o método text().
                    */

                    CONTENT = AlternativeParseMethod(text);
                }
                
            }else{
               CONTENT = LastParseMethod();
            }

            if(CONTENT != null)
                return CONTENT;
        }
                
        return PARSING_ERROR;
        
    }
    
    /** 
    * Busca o título com o Jsoup.
    * 
    * @param code Document da biblioteca Jsoup.
    * @return retorna o título da página.
    */
    @Override
    public String Title(Document code) {
        return code.title();
    }
       
    /**
    * Retorna lista com títulos do site em opções de busca.
    *
    * @param doc Document da biblioteca Jsoup.
    * @param tag_class String da class html que identifica as opções.
    * @return retorna as opções da página.
    */
    @Override
    public String[] Options(Document doc, String tag_class) {
        String[] opcoes = doc.select(tag_class).toString().split("\n");
        completo  = new String[opcoes.length+1];
        completo[0] = " - Ver Tudo";
        for(int i = 0; i < opcoes.length; i++) {
            completo[i+1] = " - "+Jsoup.parse(opcoes[i]).text();
        }
        return completo;
    }
    
    /**
    * Conecta ao site e recebe o código fonte.
    * 
    * @param url do servidor de dados.
    * @return retorna o êxito da inicialização. 
    */
    @Override
    public boolean Initialize(String url) {
        try {
            code = Jsoup.connect(url).get();
            return true;
        } catch (IOException ex) {
            print("IOException - Não foi possível conectar. ", "red");
        } catch(Exception ev) {
            print("Exception: "+ev.getMessage(), "red");
        }
        return false;
    }
    
    /**
    * Verifica o código para prosseguir com a raspagem.
    * 
    * @return retorna o êxito da verificação.
    */
    @Override
    public boolean Verify() {
        
        AIM = AIM.replace(" - ", "");
        
        try {
            boolean verify_code = code.toString().isEmpty();
        }catch(NullPointerException ex) {
            print("Codigo vazio.\n", "red");
            return false;
        }
        
        if(AIM == null) {
            print(" Busca vazia.\n", "red");
            return false;
        }
        
        if(SOURCE == null) {
            print(" Nao ha fonte.\n", "red");
            return false;
        }
        
        if(!code.toString().contains(AIM) && !AIM.equals("Ver Tudo")) {
            print(" Nao foi encontrado.\n", "red");
            return false;
        }
        
        return true;
    }
    
    /**
     * Método para fechar objeto.
     * 
     */
    @Override
    public void Close() {
        completo = null;
        AIM = NEXT = SEARCH_QUERY = CONTENT = null;
        code = doc = null;
    }
    
}
