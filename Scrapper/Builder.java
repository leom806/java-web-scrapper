package br.com.scrapper;

import java.io.*;

/*
* Nome: Builder
* Data: 15-12-2016
* Atualizado: 20-12-2016
* Descrição: Classe de implementação da Loader.
 */
public class Builder extends Parser implements Loader {

    private String toFind = null;
    
    protected StringBuilder CODE = new StringBuilder();
    protected BufferedReader bf;
    
    
    /**
     * Inicializa os objetos 
     */
    private void Initialize(String source) {

        this.CODE.append(source);
        
        try {
            CODE.append(bf.readLine());
            while (bf.readLine() != null) {
                CODE.append(bf.readLine());
            }
            CONTENT.append(CODE);

        } catch (IOException e) {
            System.out.println("Erro de IO\nErro: " + e);
        }

        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Initialize(File path) {
        try {
            bf = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado\nErro: " + e);
        }

        try {
            CODE.append(bf.readLine());
            while (bf.readLine() != null) {
                CODE.append(bf.readLine());
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
        CODE = null;
        try {
            bf.close();
        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }
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

        Initialize(path);

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
    @Override
    public String ParseCode(String toFind, String source, boolean status, boolean display) {

        Detach();
        this.toFind = toFind;

        Initialize(source);
        
        Parsing(status, display);

        return CONTENT.toString();
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

        Initialize(path);

        Parsing(status, true);

        return CONTENT.toString();
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
        
        Initialize(source);

        Parsing(status, true);

        return CONTENT.toString();
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

        Initialize(path);

        Parsing(false, display);

        return CONTENT.toString();
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
        
        Initialize(source);

        Parsing(false, display);

        return CONTENT.toString();
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

        Initialize(path);

        Parsing(false, false);

        return CONTENT.toString();
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

        Initialize(source);
        
        Parsing(false, false);

        return CONTENT.toString();
    }

}
