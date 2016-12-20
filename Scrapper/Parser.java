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

    public static String toFind = null;
    protected Document code = null;
    
    /* 
     * Conecta ao site e recebe o código fonte.
     */
    public int Initialize(String url, String search) {
        try {
            code = Jsoup.connect(url).get();
            toFind = search;
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
        
        Initialize("https://pt.wikipedia.org/wiki/Amoxicilina", "As reações");
        
        // Faz a raspagem apenas se passar na verificação.
        if(Verify()) {
            
            Document doc = Jsoup.parse(code.toString());
            //Element link = doc.select("a").first();

            String ignore = "[editar | editar código-fonte]";
            
            Elements tags = doc.body().getElementsByTag("p");            
            String text = tags.toString();
            String text2 = tags.text();
            
            if(text.contains(toFind)) {
                String minified = text.substring(text.indexOf(toFind), text.length());
                text = minified.substring(0, minified.indexOf("</p>"));
            }else{
                text = "Não encontrado.";
            }
            
            // Salvar a região que dá "match" entre o texto das tags e o texto sem tags.
            
            System.out.println(text.replace(ignore, ""));
            
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
        
        if(!code.toString().contains(toFind)) {
            System.out.println("Não foi encontrado.");
            return false;
        }
        
        return true;
    }
    
}