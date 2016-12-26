package org.scrapper;

/**
* Nome: Main
* Data: 20-12-2016
* Atualizado: 26-12-2016
* Descrição: Classe principal; Execução em console com Dialogs.
*/

public class Main extends Parser{
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Main() {
        show("Iniciando Scrapper");
        print(Parsing(true, false));
        Close();   // retorna 0 se executado com sucesso.
    }
    
    public static void main(String[] args) {
        Main m = new Main();
    } 
}
