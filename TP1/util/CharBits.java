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

package util;

public class CharBits{

    /**
     * Converte um caractere para a sequencia de 8 bits equivalente a tabela ASCII.
     * O bit que representa 2^N em valor numerico estara na posicao 2^[7-N] no vetor.
     * @param c
     * @return int[8] charAsByte
     */
    public static int[] CharAsByte(char c){
        int retorno[] = new int[8];
        int x = c;

        int i = 0;
        int mod = 0;

        while(x > 0){
            mod = x%2;
            retorno[7-i++] = mod; //atencao: a primeira posicao do vetor correspondera ao bit que representa 2^7 ...
            x = x/2;
        }

        return retorno;
    }
}
