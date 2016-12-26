package org.scrapper;

import java.io.IOException;
import javax.swing.UIManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
* Nome: Parser
* Data: 15-12-2016
* Atualizado: 23-12-2016
* Descrição: Objeto de conversão dos dados.
*/
public class Parser extends Builder implements Loader{ 

    private static final String VERSION = "1.0.5.2";
    protected static String AIM = null;
    protected static String NEXT = null;
    protected static String SEARCH_QUERY = null;
    protected static String MAIN_TAG = "span.mw-headline"; // Tag principal da fonte de buscas. 
    protected static String CONTENT_TAG = "#mw-content-text"; // Tag de conteudo.
    protected static String SOURCE = "https://pt.wikipedia.org/wiki/"; 
    // Variável que armazena o conteúdo final da raspagem, usada no retorno dos métodos.
    protected static StringBuilder CONTENT = null;
    // code = codigo da pagina ; doc = codigo da pagina já convertido.
    protected Document code = null, doc = null;
    
    private static final String PARSING_ERROR = "Erro durante processo de raspagem.";
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Parser() {
        print(" Java Web Scrapper "+VERSION+"\n");
    }
    
    /**
     * Método para fechar objeto.
     * 
     * @return flag 0
     */
    @Override
    public void Close() {
        AIM = NEXT = SEARCH_QUERY = MAIN_TAG = CONTENT_TAG = SOURCE = null;
        CONTENT = null;
        code = doc = null;
        System.exit(0);
    }
    
   /**
    * Conecta ao site e recebe o código fonte.
    * 
    * @param url do servidor de dados.
    * @return retorna o êxito da inicialização. 
    */
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
    * Conversão de dados e raspagem de conteúdo.
    * 
    * @param status exibe notificações sobre a raspagem.
    * @param display exibe os resultados da raspagem.
    * @return conteúdo da raspagem.
    */
    @Override
    public String Parsing(boolean status, boolean display) {

        if(status) print("> Inicializando.");
            
        SEARCH_QUERY = get("Página: ");  // Recebe a paǵina por um Dialog
        
        if(SEARCH_QUERY == null) Close();
        
        try{
            Initialize(SOURCE+SEARCH_QUERY);
            if(status) print("> Fonte: "+SOURCE);
        }catch(Exception ex){
            print("Erro: "+ex.getMessage()+"\n", "red");
        }
        
        // Gera um Document com o código da URL da fonte
        doc = Jsoup.parse(code.toString());
        
        // Mostra janela de busca e a opção "Ver Opções"
        try {
            UIManager.put("OptionPane.cancelButtonText","Ver Opções");
            AIM = get("Busca: ");
            UIManager.put("OptionPane.cancelButtonText","Cancel");
            if (AIM == null) {
                // Diálogo com as opções para seleção
                Object[] opcoes = Options(doc, MAIN_TAG);
                AIM = get("Opções:", opcoes);
                int i = 0;
                for(Object opcao : opcoes) {
                    if(opcao.equals(AIM)) break;
                    else i++;
                }
                NEXT = opcoes[i+1].toString().replace(" - ", "");
                if (status) print("> Buscando: "+AIM);
            }
        }catch (NullPointerException ex) {
            Close();
        }
        
        // Faz a raspagem apenas se passar na verificação.
        if(Verify()) {
            
            if(status) print("> Verificado.");
            
            // Recebe as tags <p> e gera uma String 
            Elements tags = doc.body().getElementsByTag("p");
            String text = tags.toString();
            
            // Gera String com todo o html do conteúdo
            String all = doc.body().select(CONTENT_TAG).toString();

            if(status) print("> Opções:");
            
            // Exibe as opções
            if(display) {
                print(); // Quebra de linha
                for(String opcao : Options(doc, MAIN_TAG)) {
                    print(opcao);
                }
            }
            
            print();
            
            
           /**
            * Núcleo do processo de raspagem.
            */
           
           if(AIM.equals("Ver Tudo")) {
           
                if(status) {
                    print("> Método Final.");
                }
                if(display) print(Title(doc)+"\n");

                String content = doc.select(CONTENT_TAG).select("p").toString().replace("\n", "ʘ");
                content = Jsoup.parse(content).text().replace("ʘ", "\n");
                
                if(display) print(content);
                
                CONTENT = new StringBuilder().append(content);
                
           }else{
                /*
                 * Processo de raspagem alternativo. Usa parte do texto para buscar.
                 * 
                 * Primeiro é cortado da busca até o final. Depois pegamos esse corte 
                 * e limitamos até a próxima tag. Então converte-se novamente com o Jsoup
                 * para poder usar o método text().
                 */
                try {               
                    if(text.contains(AIM)) {
                        if(status) {
                            print("> Método Alternativo.");
                        }
                        String minified = text.substring(text.indexOf(AIM), text.length());
                        text = minified.substring(0, minified.indexOf("</p>"));
                        text = clean(text); // Limpeza das tags html.
                        if(display) print(Title(doc)+"\n");
                        if(display) print(text+"\n");
                        // Salva conteúdo para retorno em StringBuilder static.
                        CONTENT = new StringBuilder().append(text);

                    /*
                     * Principal processo de raspagem. Usa as opcoes para buscar.
                     * 
                     * Gera um novo código limpo por uma Whitelist do Jsoup.
                     * Cria substring partindo do titulo escolhido e retirando caracter que
                     * pode gerar erro na próxima substring.
                     * Cria a última substring usando o próximo item como limite.
                     */

                    }else if(all.contains(AIM)) { 
                        if(status) {
                            print("> Método Principal.");
                        }
                        if(display) print(Title(doc)+"\n");
                        
                        Whitelist wl = new Whitelist();
                        wl.addTags("span", "p");
                        
                        String clean = Jsoup.clean(all, wl);
                        
                        clean = clean.replace("<span><span>[</span>editar<span> | </span>editar código-fonte<span>]</span></span>", " ");
                        
                        String ocurrency = "<span>"+AIM+"</span>";
                        String limit = "<span>"+NEXT+"</span>";
                        
                        clean = clean.substring(clean.lastIndexOf(ocurrency)+ocurrency.length()).replace("\n", "ʘ");
                        clean = clean.substring(0, clean.indexOf(limit));
                        
                        String content = Jsoup.parse(clean).text().replace("ʘ", "\n");
                                                
                        if(display) print(content);
                        CONTENT = new StringBuilder().append(content);
                    }
                }catch(StringIndexOutOfBoundsException ex) {
                    print("Erro em limite de String.", "red");
                }catch(Exception ev) {
                    print("Erro: "+ev.getMessage()+"\n", "red");
                }
            
            }
           
            if(CONTENT != null)
                return CONTENT.toString();
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
        String[] completo = new String[opcoes.length+1];
        completo[0] = " - Ver Tudo";
        for(int i = 0; i < opcoes.length; i++) {
            completo[i+1] = " - "+Jsoup.parse(opcoes[i]).text();
        }
        return completo;
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
            print("Código vazio.\n", "red");
            return false;
        }
        
        if(AIM == null) {
            print("Busca vazia.\n", "red");
            return false;
        }
        
        if(SOURCE == null) {
            print("Não há fonte.\n", "red");
            return false;
        }
        
        if(!code.toString().contains(AIM) && !AIM.equals("Ver Tudo")) {
            print("Não foi encontrado.\n", "red");
            return false;
        }
        
        return true;
    }
    
}
