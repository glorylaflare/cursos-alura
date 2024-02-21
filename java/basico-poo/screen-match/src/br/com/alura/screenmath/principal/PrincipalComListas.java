package br.com.alura.screenmath.principal;

import br.com.alura.screenmath.modelos.Filme;
import br.com.alura.screenmath.modelos.Serie;
import br.com.alura.screenmath.modelos.Titulo;

import java.util.*;

import static java.util.Collections.sort;

public class PrincipalComListas {
    public static void main(String[] args) {
        var filme1 = new Filme("O Iluminado",1980);
        filme1.avalia(9);
        var filme2 = new Filme("Dogville", 2003);
        filme2.avalia(6);
        var filme3 = new Filme("Psicose", 1960);
        filme3.avalia(10);
        var serie1 = new Serie("A Maldição da Residência Hills", 2018);

        ArrayList<Titulo> lista = new ArrayList<>();
        lista.add(filme3);
        lista.add(filme1);
        lista.add(filme2);
        lista.add(serie1);

        for (Titulo item: lista) {
            System.out.println(item.getNome());
            if (item instanceof Filme filme){
                System.out.println("Avaliação: " + filme.getClassificacao());
            }
        }

        //Ordenando listas
        System.out.println("\nOrdenando Listas:");
        List<String> buscaPorArtista = new LinkedList<>();
        buscaPorArtista.add("Adam Sandler");
        buscaPorArtista.add("Jennifer Lawrence");
        buscaPorArtista.add("Will Smith");
        System.out.println(buscaPorArtista);

        System.out.println("\nOrdenando Listas de Títulos:");
        Collections.sort(lista);
        System.out.println(lista);

        lista.sort(Comparator.comparing(Titulo::getAnoDeLancamento));
        System.out.println("\nOrdenando Listas por Ano:");
        System.out.println(lista);
    }
}
