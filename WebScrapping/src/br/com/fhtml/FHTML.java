package br.com.fhtml;

import java.io.*;

import com.thoughtworks.xstream.XStream;

/*
*
* Algoritmo de busca de conteúdo por tag em páginas HTML.
* Versão 1.0.4.2
* Nome: FHTML (Find in HTML)
* Autor: Leonardo Momente
* Data: 09-08-2016
* Atualizado: 26-10-2016
*
*/

public class FHTML {

  /*Constanstes*/

  /*Variáveis*/
  private int MAX = 0;                    //Posição final de raspagem
  private int MIN = 0;                    //Posição inicial de raspagem
  private int index = 0;                  //Índice do primeiro caracter a ser buscado
  private long time = 0;                  //Tempo de conversão
  private String sub;                     //Substring
  private String toFind;                  //Tag para buscar conteúdo
  private String new_sc;                  //Novo codigo html ; sc = source code

  private String html = "";               //Código html recebido
  private static String parcial = "";    	//Resultado parcial para retorno

  /*Objetos*/
  private static XStream xstream = null;                    //Manipulador de arquivos DOM
  private StringBuilder content = new StringBuilder();      //Conteúdo buscado
  private BufferedReader bf = null;

  /*Vetores*/

  //Vetor de caracteres curingas
  private String[] jokers = { "#", "@", "§", "|"};


  /*Métodos Auxiliares*/


  //Verifica se é realmente uma Tag
  private boolean VerifyTag(String src) {
    return (src.contains("<" + toFind + ">") || html.contains("<" + toFind));
  }

  //Conversão de dados e raspagem de conteúdo
  private int Parsing(boolean status, boolean display) {
    if(this.html != null || this.html.length() != 0) {
      long start = System.currentTimeMillis();

      if (!VerifyTag(html)) {
        System.out.println("\tNão é uma tag HTML!\n\tBusca por conteúdo não é recomendada!");
      }

      int i = 0;

      String[] aux = html.split(" ");
      for (String s : aux) {
        if (s.equals(toFind)) i++;
      }
      if (status) System.out.println("Total de aparecimento(s): " + i);
      if (i == 0) {
      	System.out.println("Erro! Aparecimentos: "+i+".");
      	return -1;
      }


      if (status) {
        System.out.println("Source Code: ");
        System.out.println(new_sc);
        System.out.println("Mirando: \'" + toFind + "\'");
        System.out.print("Comprimento: ");
        System.out.println("" + new_sc.length());
      }

      try {
        index = new_sc.indexOf(toFind);           //Define posição do primeiro caracter da busca
        if (status) System.out.println("Ã­ndice: " + index + "\tchar: " + new_sc.charAt(index));
      } catch (StringIndexOutOfBoundsException e) {
        System.out.println("\nErro em conversão de String");
        index = 0;
      }

      sub = new_sc.substring(index);              //Cria substring a partir da posição da busca
      sub = sub.replace("\"", "");

      if (status) {
        System.out.println("\nParcial: ");
        System.out.println(sub);                  //PARCIAL
      }

      MIN = sub.indexOf(">");                     //Posição da parte inicial do conteúdo pra raspar

      /*
      *  MAX só é usada se a raspagem por laço falhar.
      *  APENAS para Tags.
      */
      MAX = sub.indexOf("</"+toFind);             //Posição da parte final do conteúdo pra raspar

      i = MIN+1;
      while(sub.charAt(i) != '<')
      {
        content.append(sub.charAt(i));            //Raspagem
        i++;
      }

      //While pode retornar vazio, problema ainda não identificado
      //Há erro com substring também

      //Caso o while retorne vazio, tentar por substring
      if(content.toString().replace(" ","").replace("\n","").equals(""))
      {
        content.append(sub.substring(MIN+1, MAX));      //Raspagem
      }

      if(display) System.out.println("\nConteúdo de "+toFind+": \n"+content.toString());

      time = System.currentTimeMillis() - start;
      System.out.println("\nTempo de conversão em milisegundos: "+time);

      return 0;
    }else{
      System.out.println("Arquivo vazio!");
      return -1;
    }
  }

  /*
  * Opções
  *
  * status:
  *   exibe passo a passo do processo de raspagem
  * display:
  *   exibe o conteúdo raspado
  * default:
  *   será retornado String com conteudo raspado
  *
  */

  //'Desacopla' os objetos ; Limpa as variáveis ; etc
  private void Detach() {
    sub = null;
    toFind = null;
    new_sc = null;
    content = null;
    html = null;
    parcial = null;
    MAX = 0;
    MIN = 0;
    index = 0;
    time = 0;
    bf = null;
  }

  private int close() {
    try { 
      Detach();
      return 0;
    }catch(Exception ex) {
      System.out.println("Exception: "+ex);
    }
    return -1;
  }

  /*Métodos Principais*/

  //Default
  // sem definições padronizadas

  public String ParseCode(String toFind, File path, boolean status, boolean display) {

    Detach();

    this.toFind = toFind;

    try {
      bf = new BufferedReader(new FileReader(path));
    }catch(FileNotFoundException e){
      System.out.println("Arquivo não encontrado\nErro: "+e);
    }

    try {
      html += bf.readLine();
      while (bf.readLine() != null)
      {
        html += bf.readLine();
      }

    }catch(IOException e){
      System.out.println("Erro de IO\nErro: "+e);
    }

    try {
		bf.close();
	} catch (IOException e) {
		e.printStackTrace();
	}

    Parsing(status, display);

    return content;
  }

  public String ParseCode(String toFind, String source, boolean status, boolean display) {

    Detach();
    this.toFind = toFind;
    this.html = source;

    Parsing(status, display);

    return content;
  }

  //Default
  // display definido como true

  public String ParseCode(boolean status, String toFind, File path) {

    Detach();

    this.toFind = toFind;

    try {
      bf = new BufferedReader(new FileReader(path));
    }catch(FileNotFoundException e){
      System.out.println("Arquivo não encontrado\nErro: "+e);
    }

    try {
      html += bf.readLine();
      while (bf.readLine() != null)
      {
        html += bf.readLine();
      }

      bf.close();
    }catch(IOException e){
      System.out.println("Erro de IO\nErro: "+e);
    }

    Parsing(status, true);

    return content;
  }

  public String ParseCode(boolean status, String toFind, String source) {

    Detach();
    this.toFind = toFind;
    this.html = source;

    Parsing(status, true);

    return content;
  }

  //Default
  // status definido como false

  public String ParseCode(String toFind, File path, boolean display) {

    Detach();

    this.toFind = toFind;

    try {
      bf = new BufferedReader(new FileReader(path));
    }catch(FileNotFoundException e){
      System.out.println("Arquivo não encontrado\nErro: "+e);
    }

    try {
      html += bf.readLine();
      while (bf.readLine() != null)
      {
        html += bf.readLine();
      }

      bf.close();
    }catch(IOException e){
      System.out.println("Erro de IO\nErro: "+e);
    }

    Parsing(false, display);

    return content;
  }

  public String ParseCode(String toFind, String source, boolean display) {

    Detach();
    this.toFind = toFind;
    this.html = source;

    Parsing(false, display);

    return content;
  }

  //Default
  // status e display definidos como false

  public String ParseCode(String toFind, File path) {

    Detach();

    this.toFind = toFind;

    try {
      bf = new BufferedReader(new FileReader(path));
    }catch(FileNotFoundException e){
      System.out.println("Arquivo não encontrado\nErro: "+e);
    }

    try {
      html += bf.readLine();
      while (bf.readLine() != null)
      {
        html += bf.readLine();
      }

      bf.close();
    }catch(IOException e){
      System.out.println("Erro de IO\nErro: "+e);
    }

    Parsing(false, false);

    return content;
  }

  public String ParseCode(String toFind, String source) {

    Detach();
    this.toFind = toFind;
    this.html = source;

    Parsing(false, false);

    return content;
  }


  /*Construtor*/

  public FHTML() { Detach(); }

}
