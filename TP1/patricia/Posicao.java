/**
 * Trabalho pratico 01 - LAED2
 * Pesquisa digital - Arvore PATRICIA
 * 
 * Caio Vinicius Pereira Silveira
 * Samuel Augusto Oliveira Magalhães
 * Vitor Godinho Rausch
 * 
 * 21/05/2022
 */

package patricia;

public class Posicao{
    private int linha, coluna;

    public Posicao(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha(){ return linha; }
    public int getColuna(){ return coluna; }


    public void imprimePos(){
        System.out.println("Linha: " + linha + " Coluna: " + coluna);
    }
}
