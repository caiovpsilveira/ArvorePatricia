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

import java.util.ArrayList;
import java.util.Scanner;

import patricia.ArvorePatricia;
import patricia.Posicao;
import util.ExtraiPalavra;
import util.ExtraiPalavra.BlocoTexto;

public class Main {
    public static void main(String args[]){

        ArvorePatricia pat = new ArvorePatricia();

        Scanner in = new Scanner(System.in);
        int opcao = 1;
        String palavra;

        System.out.println("Digite o nome do arquivo teste");
        String nomeArquivoTeste = in.next();

        ExtraiPalavra extrai = null;
        try{
            extrai = new ExtraiPalavra("delimitador.txt", nomeArquivoTeste);

        }
        catch(Exception e){
            System.out.println("Algum arquivo n abriu");
        }

        if(extrai != null){
            BlocoTexto bt;
            try{
                while(extrai.getEOF() == 0){
                    bt = extrai.proximaPalavra();
                    if(bt != null){ //linha so de delimitadores
                        pat.insere(bt.getPalavra(), new Posicao(bt.getLinha(), bt.getColuna()));
                    }
                }
            }
            catch(Exception e){
                System.out.println(e.getStackTrace());
            }
        }

        while(opcao != 0){
            imprimeCabecalho();
            opcao = in.nextInt();
            switch(opcao){
                case 1:
                    System.out.println("Digite a palavra a ser pesquisada: ");
                    palavra = in.next();
                    System.out.println("Ocorrencias: ");
                    ArrayList<Posicao> ocorrencias = pat.pesquisa(palavra);
                    if(ocorrencias == null){
                        System.out.println("Palavra nao encontrada no texto");
                    }
                    else{
                        for(Posicao p : ocorrencias){
                            p.imprimePos();
                        }
                    }
                break;
                default:
                break;
            }
        }

        in.close();
        try{
            extrai.fecharArquivos();
        }
        catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }

    private static void imprimeCabecalho(){
        System.out.println("0 para sair");
        System.out.println("1 para pesquisar");
    }


}
