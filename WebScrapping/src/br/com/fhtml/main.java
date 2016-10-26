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

    	FHTML obj = new FHTML();

      String teste = obj.ParseCode("title", new File("/home/chromo/workspace/WebScrapping/src/br/com/fhtml/index.html"), true, true);

      if (teste != null) System.out.println(teste);

    }

}
