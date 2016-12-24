package org.scrapper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
* Nome: Parser
* Data: 15-12-2016
* Atualizado: 23-12-2016
* Descrição: Objeto de conversão dos dados.
*/
public class Parser extends Builder implements Loader{ 

    protected static String AIM = null;
    protected static String SEARCH_QUERY = null;
    protected static String MAIN_TAG = "span.mw-headline"; // Tag principal da fonte de buscas.
    protected static String SOURCE = "https://pt.wikipedia.org/wiki/"; 
    // Variável que armazena o conteúdo final da raspagem, usada no retorno dos métodos.
    protected static StringBuilder CONTENT = null;
    protected Document code = null, doc = null;
    
    private static final String PARSING_ERROR = "Erro durante processo de raspagem.";
        
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
            print("IOException: "+ex.getMessage());
        } catch(Exception ev) {
            print("Exception: "+ev.getMessage());
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
        
        try{
            Initialize(SOURCE+SEARCH_QUERY);
        }catch(Exception ex){
            print("Erro: "+ex.getMessage()+"\n", "red");
        }
        
        // Gera um Document com o código da URL da fonte
        doc = Jsoup.parse(code.toString());
        
        try {
            AIM = get("Busca: ");
            if (AIM == null) {
                // Diálogo com as opções para seleção
                AIM = get("Opções:", (Object[]) Options(doc, MAIN_TAG));
                if (display) print("> Buscando: "+AIM);
            }
        }catch (NullPointerException ex) {
            print("Erro: "+ex.getMessage()+"\n", "red");
        }
        
        // Faz a raspagem apenas se passar na verificação.
        if(Verify()) {
            
             // Recebe as tags <p> e gera uma String 
            Elements tags = doc.body().getElementsByTag("p");
            String all = doc.body().text();
            String text = tags.toString();

            // Exibe as opções
            if(display) {
                print("> Opções:");
                for(String opcao : Options(doc, MAIN_TAG)) {
                    print(opcao);
                }
            }
            
            print();
            
           /**
            * Núcleo do processo de raspagem.
            */

           
           /*
            * Processo de raspagem alternativo. Usa parte do texto para buscar.
            * 
            * Primeiro é cortado da busca até o final. Depois pegamos esse corte 
            * e limitamos até a próxima tag. Então converte-se novamente com o Jsoup
            * para poder usar o método text().
            */
            try {
                if(text.contains(AIM)) {
                    if(status) print("> Método Alternativo.");
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
                * Lógica - descrever
                *
                */
                }else if(all.contains(AIM)) { 
                    if(status) print("> Método Principal.");
                    if(display) print(Title(doc)+"\n");
                    
                    
                    
                    if(display) print(all+"\n");
                }           
            }catch(StringIndexOutOfBoundsException ex) {
                print("Erro em limite de String.", "red");
            }catch(Exception ev) {
                print("Erro: "+ev.getMessage()+"\n", "red");
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
        for(int i = 0; i < opcoes.length; i++) {
            opcoes[i] = Jsoup.parse(opcoes[i]).text();
        }
        return opcoes;
    }
    
   /**
    * Verifica o código para prosseguir com a raspagem.
    * 
    * @return retorna o êxito da verificação.
    */
    @Override
    public boolean Verify() {
        
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
        
        if(!code.toString().contains(AIM)) {
            print("Não foi encontrado.\n", "red");
            return false;
        }
        
        print("> Verificado.");
        
        return true;
    }
    
}
