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


    /**
     * Retorna o bit da chave na posicao i.
     * @param i
     * @param palavra
     * @return
     */
    private int bit(int i, String palavra) {
        int charPos = i/8; //encontra em qual caractere o bit i se encontra
        int bitPos = i%8; //encontra qual eh o bit do caractere
        char c = 0;
        try{
            c = palavra.charAt(charPos);
        }
        catch(StringIndexOutOfBoundsException e){ //se a palavra comparada tem menos caracteres do que eh necessario: retorna 0
            return 0; //equivale a dizer que todas as chaves ate do ultimo char ate completar o MAX_CARACTERE sao 0
        }
        int[] cBits = CharBits.CharAsByte(c); //converte o char para seus bits da ASCII
        return cBits[bitPos];
    }

    public ArvorePatricia(){
        raiz = null;
    }

    public void insere(String palavra, Posicao pos){
        raiz = insere(raiz, palavra, pos);
    }

    /**
     * Insere uma palavra e sua ocorrenccia posicao na arvore
     * Se a palavra nao existir na arvore, criara um novo no externo
     * Se existir, adicionara sua posicao ao arraylist que contem todas as ocorrencias da palavra no texto
     * @param no
     * @param palavra
     * @param pos
     * @return
     */
    private No insere(No no, String palavra, Posicao pos){
        if(no == null){ //acontece somente com a raiz na primeira insercao
            NoExt novoNo = new NoExt(palavra);
            novoNo.ocorrencias.add(pos);
            return novoNo;
        }
        else{
            No p = no;
            while(p instanceof NoInt){ //enquanto for um no Interno: comparar o bit i com o bit que o no indica diferenca
                NoInt aux = (NoInt)p;
                if (this.bit(aux.posDiff, palavra) == 1){
                    p = aux.dir; //se o bit indicado pelo no interno for i, continua percorrendo a subarvore direita
                }
                else{
                    p = aux.esq; //se nao, continua percorrendo a subarvore esquerda
                }
            }
            NoExt aux = (NoExt)p; //Encontrou um no externo, que contem uma palavra
            int i = 0;
            while(i < numBitsChave && bit(i, palavra) == bit(i, aux.palavra)){ //recompara todos os bits da palavra inserida com a palavra contida no no
                i++;
            }
            if(i == numBitsChave){ //Se todos os primeiros MAX_CARACTERES da palavra sao iguais, entao considera-se as palavras iguais, e adiciona mais uma ocorrencia
                aux.ocorrencias.add(pos);
                return no;
            }
            else{ //se nao, inserir onde encontrou a diferenca
                return insereEntre(palavra, no, i, pos);
            }
        }
    }

    /**
     * Apos encontrar a diferenca entre a palavra do no terminal e a nova palavra a ser inserida,
     * deve se procurar, ao longo do mesmo caminho percorrido, o ponto em que a nova palavra se diferencia, e entao
     * criar um novo no interno que possibilita distinguir entre o caminho antigo e a nova palavra 
     * @param palavra
     * @param no
     * @param i
     * @param pos
     * @return
     */
    private No insereEntre(String palavra, No no, int i, Posicao pos){
        NoInt aux = null;
        if(no instanceof NoInt){
            aux = (NoInt) no;
        }
        //se quer inserir entre um no externo ou a diferenca eh MAIOR do que a
        //indicada por i: Deve se criar um novo no interno, que dividira as duas novas subarvores
        if(no instanceof NoExt || i<aux.posDiff){ 
            NoExt p = new NoExt(palavra); //cria-se um novo no externo, que representara uma nova palavra na arvore
            p.ocorrencias.add(pos);
            if(bit(i, palavra) == 1){ //Se o bit i da palavra inserida eh 1: o novo no Externo que contem a nova palavra deve ficar a direita
                return new NoInt(i, no, p);
            }
            else{
                return new NoInt(i, p, no); //se nao, o novo no Externo que contem a nova palavra deve ficar a esquerda
            }
        }
        else{ //se nao, buscar o proximo no interno que possui diferenca de chave MAIOR do que a informada por i
            if(bit(aux.posDiff, palavra) == 1){
                aux.dir = insereEntre(palavra, aux.dir, i, pos); //aquele no superior podera ter um novo filho, a direita
            }
            else{
                aux.esq = insereEntre(palavra, aux.esq, i, pos); //aquele no superior podera ter um novo filho, a esquerda
            }
            return aux;
        }
    }

    /**
     * Retorna null se a palavra nao esta na arvore, ou retorna um arrayList contendo todas os pares (linha, coluna) das ocorrencias da palavra buscada
     * @param palavra
     * @return
     */
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
        if(no instanceof NoExt){ //Se finalmente chegou em um no externo (contem a palavra)
            NoExt aux = (NoExt) no;
            if(aux.palavra.equals(palavra)){ //se a palavra pesquisada for igual a contida no no Externo
                print("Palavra encontrada: " + palavra);
                return aux;
            }
            else{
                print("Palavra nao encontrada: " + palavra); //se nao, a palavra nao existe na arvore
                return null;
            }
        }
        else{ //ainda nao atingiu um no externo: continuar procurando nas subarvores
            NoInt aux = (NoInt) no;
            if(bit(aux.posDiff, palavra) == 0){ //se o bit i da palavra na posicao de diferenca apontado pelo no interno for 0: pesquisar na subarvore esquerda
                return pesquisa(palavra, aux.esq);
            }
            else{
                return pesquisa(palavra, aux.dir); //se nao, procurar na subarvore direita
            }
        }
    }

    private void print(String str){
        System.out.println(str);
    }
}
