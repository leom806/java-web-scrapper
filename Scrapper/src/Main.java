package br.com.scrapper;

/**
* Nome: Main
* Data: 20-12-2016
* Atualizado: 21-12-2016
* Descrição: Classe principal; Execução em console.
*/

public class Main extends Builder{
    
    public Main() {
        show("Iniciando.");
        new Parser().Parsing(true, true);
    }
    
    public static void main(String[] args) {
        Main m = new Main();
    } 
}
