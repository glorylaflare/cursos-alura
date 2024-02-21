package br.com.alura.minhasmusicas.principal;

import br.com.alura.minhasmusicas.modelos.MinhasPreferidas;
import br.com.alura.minhasmusicas.modelos.Musica;
import br.com.alura.minhasmusicas.modelos.Podcast;

public class Principal {
    public static void main(String[] args) {
        Musica minhaMusica = new Musica();
        minhaMusica.setTitulo("Money Trees (ft. Jay Rock)");
        minhaMusica.setArtista("Kendrick Lamar");

        for (int i = 0; i < 1000; i++) {
            minhaMusica.reproduz();
        }

        for (int i = 0; i < 50; i++) {
            minhaMusica.curte();
        }

        //Podcast
        Podcast meuPodcast = new Podcast();
        meuPodcast.setTitulo("Pod&Dev");
        meuPodcast.setHost("Noel Galleger");

        for (int i = 0; i < 1500; i++) {
            meuPodcast.reproduz();
        }

        for (int i = 0; i < 300; i++) {
            meuPodcast.curte();
        }

        MinhasPreferidas preferidas = new MinhasPreferidas();
        preferidas.inclui(meuPodcast);
        preferidas.inclui(minhaMusica);
    }
}
