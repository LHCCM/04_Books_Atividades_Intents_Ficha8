package amsi.dei.estg.ipleiria.pt.ficha4_amsi.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.Livro;

/**
 * Created by User on 15/12/2017.
 */

public interface LivrosListener {


    void onRefreshListaLivros(ArrayList<Livro> listaLivros);

    void onUpdateListaLivros(Livro livro, int operacao);
}
