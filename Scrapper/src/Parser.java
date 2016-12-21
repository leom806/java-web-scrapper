package br.com.scrapper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
* Nome: Parser
* Data: 15-12-2016
* Atualizado: 20-12-2016
* Descrição: Objeto de conversão dos dados.
*/
public class Parser extends Builder implements Loader{ 

    protected static String AIM = "Propriedades";
    protected static String SEARCH_QUERY = "Tylenol";
    protected static String MAIN_TAG = "span.mw-headline"; // Tag principal de cada fonte de buscas.
    protected static String SOURCE = "https://pt.wikipedia.org/wiki/";
    /* 
    * Variável que armazena o conteúdo final da raspagem, usada no retorno dos métodos.
    */
    protected static StringBuilder CONTENT = null;
    protected Document code = null;
    
    private static final String PARSING_ERROR = "Erro durante processo de raspagem.";
        
    /* 
     * Conecta ao site e recebe o código fonte.
     * @param String da URL de acesso.
     * @param String da palavra chave da busca em conteúdo.
     */
    public boolean Initialize(String url, String search) {
        try {
            code = Jsoup.connect(url).get();
            return true;
        } catch (IOException ex) {
            System.out.println("IOException: "+ex.getMessage());
        } catch(Exception ev) {
            System.out.println("Exception: "+ev.getMessage());
        }
        return false;
    }
    
    /* 
     * Conversão de dados e raspagem de conteúdo.
     */
    @Override
    public String Parsing(boolean status, boolean display) {

        if(status) System.out.println("> Inicializando.");
            
        if(Initialize(SOURCE+SEARCH_QUERY, AIM)) 
            System.out.println("Pronto.\n");
        else
            System.out.println("\nErro na inicialização\n");

        // Faz a raspagem apenas se passar na verificação.
        if(Verify()) {
            
            // Gera um Document com o código da URL da fonte
            Document doc = Jsoup.parse(code.toString());
            // Recebe as tags <p> e gera uma String 
            Elements tags = doc.body().getElementsByTag("p");
            String all = doc.body().text();
            String text = tags.toString();

            // Exibe as opções
            System.out.println("Opções: \n");
            String[] opcoes = doc.select(MAIN_TAG).toString().split("\n");
            for(int i = 0; i < opcoes.length-1; i++) {
                System.out.println("- "+Jsoup.parse(opcoes[i]).text());
            }
            
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
                    if(status) System.out.println("\n> Método Alternativo.\n");
                    String minified = text.substring(text.indexOf(AIM), text.length());
                    text = minified.substring(0, minified.indexOf("</p>"));
                    text = Jsoup.parse(text).text();
                    System.out.println(Title(doc)+"\n");
                    System.out.println(text+"\n");
                    // Salva conteúdo para retorno em StringBuilder static.
                    CONTENT = new StringBuilder().append(text);
               /*
                * Principal processo de raspagem. Usa as opcoes para buscar.
                * 
                * Lógica - descrever
                *
                */
                }else if(all.contains(AIM)) { 
                    if(status) System.out.println("\n> Método Principal.\n");
                    System.out.println(Title(doc));
                    
                    System.out.println();
                    
                    //System.out.println(all+"\n");
                }           
            }catch(StringIndexOutOfBoundsException ex) {
                System.out.println("Erro em limite de String.");
                ex.printStackTrace();
            }catch(Exception ev) {
                System.out.println("Erro: "+ev.getMessage());
            }
            
            if(CONTENT != null)
                return CONTENT.toString();
        }
        
        return PARSING_ERROR;
    }
    
    /* 
     * Busca o título com o Jsoup.
     */
    @Override
    public String Title(Document code) {
        return code.title();
    }
       
    /* 
     * Verifica o código para prosseguir com a raspagem.
     */
    @Override
    public boolean Verify() {
        
        try {
            boolean verify_code = code.toString().isEmpty();
        }catch(NullPointerException ex) {
            System.out.println("Código vazio.");
            return false;
        }
        
        if(AIM.isEmpty()) {
            System.out.println("Busca vazia.");
            return false;
        }
        
        if(SOURCE.isEmpty()) {
            System.out.println("Não há fonte.");
            return false;
        }
        
        if(!code.toString().contains(AIM)) {
            System.out.println("Não foi encontrado.");
            return false;
        }
        
        return true;
    }
    
}
