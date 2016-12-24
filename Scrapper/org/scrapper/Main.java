package org.scrapper;

/**
* Nome: Main
* Data: 20-12-2016
* Atualizado: 21-12-2016
* Descrição: Classe principal; Execução em console com Dialogs.
*/

public class Main extends Parser{
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Main() {
        show("Iniciando.");
        new Parser().Parsing(true, true);
    }
    
    public static void main(String[] args) {
        Main m = new Main();
    } 
}
