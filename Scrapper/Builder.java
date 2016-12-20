package br.com.scrapper;

import java.io.*;

/*
*
* Nome: Builder
* @author Leonardo Momente
* @version 1.0.5.0
* Data: 15-12-2016
* Atualizado: 20-12-2016
*
 */
public class Builder extends Parser implements Loader {

    private String toFind = null;
    
    protected StringBuilder html = new StringBuilder();
    protected BufferedReader bf;
    
    
    /**
     * 'Desacopla' os objetos ; Limpa as variáveis ; etc
     */
    private void Detach() {
        //
        //
        //
        //
        //
        //
        //
    }

    /**
     * Fecha o objeto e gera retorno
     */
    @Override
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
     * Método sobrecarregado para busca.
     *
     * @param palavra buscada
     * @param arquivo fonte
     * @param flag de status da busca
     * @param flag de display de resultados
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String ParseCode(String toFind, File path, boolean status, boolean display) {

        Detach();

        this.toFind = toFind;

        try {
            bf = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado\nErro: " + e);
        }

        try {
            html.append(bf.readLine());
            while (bf.readLine() != null) {
                html.append(bf.readLine());
            }

        } catch (IOException e) {
            System.out.println("Erro de IO\nErro: " + e);
        }

        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parsing(status, display);

        return content;
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
    @Override
    public String ParseCode(String toFind, String source, boolean status, boolean display) {

        Detach();
        this.toFind = toFind;
        this.html.append(source);

        Parsing(status, display);

        return content;
    }

    /**
     * Método sobrecarregado para busca.
     *
     * @param flag de status da busca
     * @param palavra buscada
     * @param arquivo fonte
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String ParseCode(boolean status, String toFind, File path) {

        Detach();

        this.toFind = toFind;

        try {
            bf = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado\nErro: " + e);
        }

        try {
            html.append(bf.readLine());
            while (bf.readLine() != null) {
                html.append(bf.readLine());
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("Erro de IO\nErro: " + e);
        }

        Parsing(status, true);

        return content;
    }

    /**
     * Método sobrecarregado para busca.
     *
     * @param flag de status da busca
     * @param palavra buscada
     * @param String fonte
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String ParseCode(boolean status, String toFind, String source) {

        Detach();
        this.toFind = toFind;
        this.html.append(source);

        Parsing(status, true);

        return content;
    }

    /**
     * Método sobrecarregado para busca.
     *
     * @param palavra buscada
     * @param arquivo fonte
     * @param flag de display de resultados
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String ParseCode(String toFind, File path, boolean display) {

        Detach();

        this.toFind = toFind;

        try {
            bf = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado\nErro: " + e);
        }

        try {
            html.append(bf.readLine());
            while (bf.readLine() != null) {
                html.append(bf.readLine());
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("Erro de IO\nErro: " + e);
        }

        Parsing(false, display);

        return content;
    }

    /**
     * Método sobrecarregado para busca.
     *
     * @param palavra buscada
     * @param String fonte
     * @param flag de display de resultados
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String ParseCode(String toFind, String source, boolean display) {

        Detach();
        this.toFind = toFind;
        this.html.append(source);

        Parsing(false, display);

        return content;
    }

    /**
     * Método sobrecarregado para busca.
     *
     * @param palavra buscada
     * @param arquivo fonte
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String ParseCode(String toFind, File path) {

        Detach();

        this.toFind = toFind;

        try {
            bf = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado\nErro: " + e);
        }

        try {
            html.append(bf.readLine());
            while (bf.readLine() != null) {
                html.append(bf.readLine());
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("Erro de IO\nErro: " + e);
        }

        Parsing(false, false);

        return content;
    }

    /**
     * Método sobrecarregado para busca.
     *
     * @param palavra buscada
     * @param String fonte
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String ParseCode(String toFind, String source) {

        Detach();
        this.toFind = toFind;
        this.html.append(source);

        Parsing(false, false);

        return content;
    }

}
