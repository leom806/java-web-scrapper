package br.com.scrapper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
* Nome: Parser
* Data: 15-12-2016
* Atualizado: 20-12-2016
* Descrição: Objeto de conversão dos dados. Uso da biblioteca Jsoup.
*/
public class Parser implements Loader{ 

    protected static String AIM = "Propriedades";
    protected static String SEARCH = "amoxicilina";
    protected static String SOURCE = "https://pt.wikipedia.org/wiki/";
    protected Document code = null;
    
    /* 
     * Conecta ao site e recebe o código fonte.
     */
    public int Initialize(String url, String search) {
        try {
            code = Jsoup.connect(url).get();
            return 0;
        } catch (IOException ex) {
            System.out.println("IOException: "+ex.getMessage());
        }
        return -1;
    }
    
    /* 
     * Conversão de dados e raspagem de conteúdo.
     */
    @Override
    public int Parsing(boolean status, boolean display) {
        
        Initialize(SOURCE+SEARCH, AIM);
        
        // Faz a raspagem apenas se passar na verificação.
        if(Verify()) {
            // Gera um Document com o código da URL da fonte
            Document doc = Jsoup.parse(code.toString());
            // Recebe as tags <p> e gera uma String 
            Elements tags = doc.body().getElementsByTag("p");            
            String text = tags.toString();
            // Recebe as tags <span> e gera uma String 
            tags = doc.body().getElementsByTag("span");
            String text2 = tags.toString();
            
            /**
             * Núcleo do processo de raspagem.
             * Primeiro é cortado da busca até o final. Depois pegamos esse corte 
             * e limitamos até a próxima tag. Então converte-se novamente com o Jsoup
             * para poder usar o método text().
             */
            if(text.contains(AIM)) {
                String minified = text.substring(text.indexOf(AIM), text.length());
                text = minified.substring(0, minified.indexOf("</p>"));
                text = Jsoup.parse(text).text();
                System.out.println(Title(doc)+"\n");
                System.out.println(text+"\n");
            }else if(text2.contains(AIM)){ 
                System.out.println(Title(doc)+"\n");
                System.out.println(text2+"\n");
            }{
                System.out.println("Não encontrado.");
            }            
            return 0;
        }
        
        return -1;
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
    private boolean Verify() {
        
        if(code.toString().isEmpty()) {
            System.out.println("Código vazio.");
            return false;
        } 
        
        if(!code.toString().contains(AIM)) {
            System.out.println("Não foi encontrado.");
            return false;
        }
        
        return true;
    }
    
}