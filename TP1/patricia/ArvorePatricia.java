/**
 * Trabalho pratico 01 - LAED2
 * Pesquisa digital - Arvore PATRICIA
 * 
 * Caio Vinicius Pereira Silveira
 * Samuel Augusto Oliveira Magalhães
 * Vitor Godinho Rausch
 * 
 * 21/05/2022
 * 
 * A classe "ArvorePatricia" foi adaptada da implementacao de
 * Nivio Ziviani, Projeto de Algoritmos com implementacoes em Java e C++, cap. 5, 1. ed, 2006.
 * 
 */

package patricia;

import java.util.ArrayList;

import util.CharBits;

public class ArvorePatricia {

    private static abstract class No{};
    private static class NoInt extends No{
        int posDiff;
        No esq, dir;
        public NoInt(int posDiff, No esq, No dir){
            this.posDiff = posDiff;
            this.esq = esq;
            this.dir = dir;
        }
    }
    private static class NoExt extends No{
        ArrayList<Posicao> ocorrencias;
        String palavra;
        public NoExt(String palavra){
            this.ocorrencias = new ArrayList<>();
            this.palavra = palavra;
        }
    }

    private No raiz;
    private static final int MAX_CHAR = 16;
    private static final int numBitsChave = MAX_CHAR*8;


    private int bit(int i, String palavra) {
        int charPos = i/8;
        int bitPos = i%8;
        char c = 0;
        try{
            c = palavra.charAt(charPos);
        }
        catch(StringIndexOutOfBoundsException e){
            return 0;
        }
        int[] cBits = CharBits.CharAsByte(c);
        return cBits[bitPos];
    }

    public ArvorePatricia(){
        raiz = null;
    }

    public void insere(String palavra, Posicao pos){
        raiz = insere(raiz, palavra, pos);
    }

    private No insere(No no, String palavra, Posicao pos){
        if(no == null){ //acontece somente com a raiz na primeira insercao
            NoExt novoNo = new NoExt(palavra);
            novoNo.ocorrencias.add(pos);
            return novoNo;
        }
        else{
            No p = no;
            while(p instanceof NoInt){
                NoInt aux = (NoInt)p;
                if (this.bit(aux.posDiff, palavra) == 1){
                    p = aux.dir;
                }
                else{
                    p = aux.esq;
                }
            }
            NoExt aux = (NoExt)p;
            int i = 0;
            while(i < numBitsChave && bit(i, palavra) == bit(i, aux.palavra)){
                i++;
            }
            if(i == numBitsChave){
                aux.ocorrencias.add(pos);
                return no;
            }
            else{
                return insereEntre(palavra, no, i, pos);
            }
        }
    }

    private No insereEntre(String palavra, No no, int i, Posicao pos){
        NoInt aux = null;
        if(no instanceof NoInt){
            aux = (NoInt) no;
        }
        if(no instanceof NoExt || i<aux.posDiff){
            NoExt p = new NoExt(palavra);
            p.ocorrencias.add(pos);
            if(bit(i, palavra) == 1){
                return new NoInt(i, no, p);
            }
            else{
                return new NoInt(i, p, no);
            }
        }
        else{
            if(bit(aux.posDiff, palavra) == 1){
                aux.dir = insereEntre(palavra, aux.dir, i, pos);
            }
            else{
                aux.esq = insereEntre(palavra, aux.esq, i, pos);
            }
            return aux;
        }
    }

    public ArrayList<Posicao> pesquisa(String palavra){
        NoExt retorno = pesquisa(palavra, raiz);
        if(retorno == null){
            return null;
        }
        else{
            return retorno.ocorrencias;
        }
    }

    private NoExt pesquisa(String palavra, No no){
        if(no instanceof NoExt){
            NoExt aux = (NoExt) no;
            if(aux.palavra.equals(palavra)){
                print("Palavra encontrada: " + palavra);
                return aux;
            }
            else{
                print("Palavra nao encontrada: " + palavra);
                return null;
            }
        }
        else{
            NoInt aux = (NoInt) no;
            if(bit(aux.posDiff, palavra) == 0){
                return pesquisa(palavra, aux.esq);
            }
            else{
                return pesquisa(palavra, aux.dir);
            }
        }
    }

    private void print(String str){
        System.out.println(str);
    }
}
