package amsi.dei.estg.ipleiria.pt.ficha4_amsi.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.Livro;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.R;

/**
 * Created by User on 07/11/2017.
 */

public class GrelhaLivroAdapter extends BaseAdapter {

    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public GrelhaLivroAdapter(Context contexto, ArrayList<Livro> livros) {
        this.contexto = contexto;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
        {
            inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        if (view == null)
        {
            view = inflater.inflate(R.layout.item_grelha_livro,null);
        }

        ImageView capa = view.findViewById(R.id.imageView);

        Livro livro = livros.get(i);

        //set Image
        Glide.with(contexto)
                .load(livro.getCapa())
                .placeholder(R.drawable.ipl_semfundo)
                .thumbnail(0f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(capa);

        return view;
    }
}
