package br.com.scrapper;

import java.io.*;

/*
* Nome: Builder
* Data: 15-12-2016
* Atualizado: 20-12-2016
* Descrição: Classe de implementação da Loader.
*/
public class Builder extends Parser {
 
    protected StringBuilder code = new StringBuilder();
    protected BufferedReader bf;
    
    /**
     * Inicializa os objetos 
     */
    private void Initi(String source) {

        this.code.append(source);
        
        try {
            code.append(bf.readLine());
            while (bf.readLine() != null) {
                code.append(bf.readLine());
            }
            CONTENT.append(code);

        } catch (IOException e) {
            System.out.println("Erro de IO\nErro: " + e);
        }

        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Initi(File path) {
        try {
            bf = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado\nErro: " + e);
        }

        try {
            code.append(bf.readLine());
            while (bf.readLine() != null) {
                code.append(bf.readLine());
            }

        } catch (IOException e) {
            System.out.println("Erro de IO\nErro: " + e);
        }

        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 'Desacopla' os objetos ; Limpa as variáveis ; etc
     */
    private void Detach() {
        code = null;
        try {
            bf.close();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }
    }
    
    /**
     * Fecha o objeto e gera retorno
     */
    public int Close() {
        try {
            Detach();
            return 0;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
        return -1;
    }
    
    /**
     * Métodos Principais Sobrecarregados e Sobrescritos
     */
    
    /**
    * Opções de chamada dos métodos.
    *   status:
    *   exibe passo-a-passo do processo de raspagem.
    *   
    *   display:
    *   exibe o conteúdo raspado.
    *   
    *   default:
    *   será retornado String com conteúdo raspado.
    */
    
    /**
     * Método sobrecarregado para busca.
     *
     * @param palavra buscada
     * @param arquivo fonte
     * @param flag de status da busca
     * @param flag de display de resultados
     * @since 1.0
     * @version 1.0
     */

    public String ParseCode(String toFind, File path, boolean status, boolean display) {

        Detach();
        Initi(path);
        Parsing(status, display);

        return CONTENT.toString();
    }

    /**
     * Método sobrecarregado para busca.
     *
     * @param palavra buscada
     * @param String fonte
     * @param flag de status da busca
     * @param flag de display de resultados
     * @since 1.0
     * @version 1.0
     */

    public String ParseCode(String toFind, String source, boolean status, boolean display) {

        Detach();
        Initi(source);
        Parsing(status, display);

        return CONTENT.toString();
    }
    
}
