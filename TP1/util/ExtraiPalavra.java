/**
 * Trabalho pratico 01 - LAED2
 * Pesquisa digital - Arvore PATRICIA
 * 
 * Caio Vinicius Pereira Silveira
 * Samuel Augustu Oliveira Magalhães
 * Vitor Godinho Rausch
 * 
 * 21/05/2022
 * 
 * A classe "ExtraiPalavra" foi adaptada da implementacao de
 * Nivio Ziviani, Projeto de Algoritmos com implementacoes em Java e C++, cap. 5, 1. ed, 2006.
 * 
 */

package util;

import java.util.StringTokenizer;
import java.io.*;

public class ExtraiPalavra {
  private BufferedReader arqDelim, arqTxt;
  private StringTokenizer palavras;
  private String delimitadores;

  private int line, coluna, EOF;

  public static class BlocoTexto{
    private String palavra;
    private int linha, coluna;
    public BlocoTexto(String palavra, int linha, int coluna){
      this.palavra = palavra;
      this.linha = linha;
      this.coluna = coluna;
    }
    public String getPalavra(){ return palavra; }
    public int getLinha(){ return linha; }
    public int getColuna(){ return coluna; }
  }

  public ExtraiPalavra (String nomeArqDelim, String nomeArqTxt) throws Exception {
    this.arqDelim = new BufferedReader (new FileReader (nomeArqDelim));
    this.arqTxt = new BufferedReader (new FileReader (nomeArqTxt));
    //Os delimitadores devem estar juntos em uma unica linha do arquivo 
    this.delimitadores = arqDelim.readLine() + "\r\n"; 
    this.palavras = null;
    this.EOF = 0;
  }

  public BlocoTexto proximaPalavra () throws Exception{
    if (palavras == null || !palavras.hasMoreTokens()) {
      String linha = arqTxt.readLine();
      coluna = 0;
      line++;
      if (linha == null){
        EOF = 1;
        return null;
      } 
      this.palavras = new StringTokenizer (linha, this.delimitadores);
      if (!palavras.hasMoreTokens()){
        return null; //ignora delimitadores
      }
    }
    coluna++;
    return new BlocoTexto(this.palavras.nextToken(), line, coluna);
  }

  public int getEOF(){
    return EOF;
  }

  public void fecharArquivos () throws Exception {
    this.arqDelim.close(); 
    this.arqTxt.close();
  }
}