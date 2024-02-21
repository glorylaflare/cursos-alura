package br.com.alura.screenmath.principal;

import br.com.alura.screenmath.calculos.CalculadoraDeTempo;
import br.com.alura.screenmath.calculos.FiltroRecomendacao;
import br.com.alura.screenmath.modelos.Episodio;
import br.com.alura.screenmath.modelos.Filme;
import br.com.alura.screenmath.modelos.Serie;

import java.util.ArrayList;

public class Principal {
    public static void main(String[] args) {
        //Filme
        Filme filme1 = new Filme("O Iluminado",1980);
        filme1.setDuracaoEmMinutos(146);
        filme1.exibeFichaTecnica();
        filme1.avalia(8);
        filme1.avalia(10);
        filme1.avalia(7);
        System.out.println("A média do filme é de: " + filme1.pegaMedia());

        var filme2 = new Filme("Dogville", 2003);
        filme2.setDuracaoEmMinutos(178);
        filme2.avalia(10);
        filme2.avalia(6);
        filme2.avalia(8);
        System.out.println("A média do filme é de: " + filme2.pegaMedia());

        var filme3 = new Filme("Psicose", 1960);
        filme3.setDuracaoEmMinutos(109);
        filme3.avalia(10);
        filme3.avalia(8);
        filme3.avalia(9);
        System.out.println("A média do filme é de: " + filme3.pegaMedia());

        //Lista de filmes
        ArrayList<Filme> listaDeFilmes = new ArrayList<>();
        listaDeFilmes.add(filme3);
        listaDeFilmes.add(filme1);
        listaDeFilmes.add(filme2);
        System.out.println("O comprimento da lista é " + listaDeFilmes.size());
        System.out.println("O primeiro filme da lista é " + listaDeFilmes.get(0).getNome());
        System.out.println(listaDeFilmes);

        System.out.println("\n");
        //Serie
        Serie minhaSerie = new Serie("A Maldição da Residência Hills", 2018);
        minhaSerie.exibeFichaTecnica();
        minhaSerie.setTemporadas(1);
        minhaSerie.setEpisodiosPorTemporada(15);
        minhaSerie.setMinutosPorEpisodio(40);
        System.out.println("Duração para maratonar série: " + minhaSerie.getDuracaoEmMinutos() + " minutos");

        System.out.println("\n");
        //Calculadora
        CalculadoraDeTempo calculadora = new CalculadoraDeTempo();
        calculadora.inclui(filme1);
        calculadora.inclui(minhaSerie);
        System.out.println("Tempo total necessário para assistir suas séries e filmes pendetes é de: " + calculadora.getTempoTotal() + " minutos");

        System.out.println("\n");
        //Filtro
        FiltroRecomendacao filtro = new FiltroRecomendacao();
        filtro.filtra(filme1);

        Episodio episodio = new Episodio();
        episodio.setNumero(1);
        episodio.setSerie(minhaSerie);
        episodio.setTotalVisualizacoes(30000);
        filtro.filtra(episodio);
    }
}