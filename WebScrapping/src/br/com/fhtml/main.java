package br.com.fhtml;

import java.io.File;
import java.io.IOException;

/*
*
* Classe de instância e execução da FHTML.
* Autor: Leonardo Momente
* Data: 14-08-2016
*
*/

public class main {
    public static void main(String[] args) {
        // Constantes e Variáveis
        private final String PATH = "/...";
        
        // Objetos
        FHTML obj = new FHTML();
        File FILE = new File(PATH);
        
        // Execução
        
        //ParseCode(Meta, Arquivo ou String, {flags para status no console -> [status], [display] });
        String teste = obj.ParseCode("title", FILE, true, true);
        // Exibição
        if (teste != null) System.out.println(teste);
        
        // Fecha
        obj.close();
    }
}
