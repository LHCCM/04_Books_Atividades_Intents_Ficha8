package amsi.dei.estg.ipleiria.pt.ficha4_amsi.adaptadores;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.zip.Inflater;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.ListaLivros;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.Livro;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.R;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.listeners.LivrosListener;

public class ListaLivroAdapter extends BaseAdapter{

    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public ListaLivroAdapter(Context contexto, ArrayList<Livro> livros) {
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
            view = inflater.inflate(R.layout.item_lista_livro,null);
        }

        TextView titulo = view.findViewById(R.id.textViewT);
        TextView serie = view.findViewById(R.id.textViewS);
        TextView autor = view.findViewById(R.id.textViewAu);
        TextView ano = view.findViewById(R.id.textViewAno);
        ImageView capa = view.findViewById(R.id.imageView);


        Livro livro = livros.get(i);

        titulo.setText(livro.getTitulo());
        serie.setText(livro.getSerie());
        autor.setText(livro.getAutor());
        ano.setText(livro.getAno());

        //set Image
        Glide.with(contexto)
                .load(livro.getCapa())
                .placeholder(R.drawable.ipl_semfundo)
                .thumbnail(0f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(capa);


        return view;
    }

    public void refresh(ArrayList<Livro> livros){
        this.livros = livros;
        notifyDataSetChanged();
    }
}
