package amsi.dei.estg.ipleiria.pt.ficha4_amsi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.Livro;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.SingletonLivros;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.adaptadores.GrelhaLivroAdapter;

public class GrelhaLivros extends AppCompatActivity {

    private GridView grelhaLivros;
    private ArrayList<Livro> listaLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grelha_livros);
        setTitle("Grelha de Livros");


        listaLivros = SingletonLivros.getInstance(getApplicationContext()).getLivrosBD();

        grelhaLivros = (GridView) findViewById(R.id.gridLivros);
        grelhaLivros.setAdapter(new GrelhaLivroAdapter(this, listaLivros));

        grelhaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detalhes = new Intent(getApplicationContext(),DetalhesLivro.class);
                detalhes.putExtra("DADOS_LIVRO",i);
                startActivity(detalhes);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_grelha_livros,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLista:
                //code here

                Intent lista = new Intent(getApplicationContext(),ListaLivros.class);
                startActivity(lista);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAdicionarLivro(View view) {

    }
}
