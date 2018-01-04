package amsi.dei.estg.ipleiria.pt.ficha4_amsi;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.Livro;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.SingletonLivros;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.adaptadores.ListaLivroAdapter;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.listeners.LivrosListener;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.utils.LivroJsonParser;

public class ListaLivros extends AppCompatActivity  implements LivrosListener {

    private ListView listaLivro;

    private ArrayList<Livro> listaLivros;

    private String mEmail;

    private ListaLivroAdapter listaLivroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_livros);
        setTitle("Lista de Livros");

        mEmail = getIntent().getStringExtra(LoginActivity.DADOS_EMAIL);

        SingletonLivros.getInstance(getApplicationContext()).setLivrosListener(this);
        SingletonLivros.getInstance(getApplicationContext()).getAllLivrosAPI(this, LivroJsonParser.isConnectionInternet(this));

        listaLivro=(ListView) findViewById(R.id.listViewLivros);
        //listaLivro.setAdapter(new ListaLivroAdapter(this, listaLivros));


        listaLivro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Livro tempLivro = (Livro) adapterView.getItemAtPosition(i);

                //Toast.makeText(ListaLivros.this, " " + tempLivro, Toast.LENGTH_SHORT).show();

                Intent detalhes = new Intent(getApplicationContext(),DetalhesLivro.class);
                detalhes.putExtra("DADOS_LIVRO",i);
                startActivity(detalhes);



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_lista_livros,menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemPesquisa);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Livro> tempListaLivro = new ArrayList<>();
                for (Livro temp:listaLivros) {
                    if (temp.getTitulo().toLowerCase().contains(s.toLowerCase())) {
                        tempListaLivro.add(temp);
                    }
                }
                listaLivro.setAdapter(new ListaLivroAdapter(ListaLivros.this, tempListaLivro));


                listaLivro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Livro tempLivro = (Livro) adapterView.getItemAtPosition(i);

                        Intent detalhes = new Intent(getApplicationContext(),DetalhesLivro.class);
                        detalhes.putExtra("DADOS_LIVRO",i);
                        startActivity(detalhes);

                    }
                });

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemGrelha:
                //code here
                finish();

                Intent grelha = new Intent(getApplicationContext(),GrelhaLivros.class);
                startActivity(grelha);

                return true;
            case R.id.itemEmail:
                //code here
                Intent intent = new Intent(Intent.ACTION_SENDTO);

                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:"+mEmail));
                intent.putExtra(Intent.EXTRA_SUBJECT,"UMA DISGRAÇA");
                intent.putExtra(Intent.EXTRA_TEXT,"SUA VIDA É UMA DISGRAÇA E A MINHA TAMBÉM NÃO VAI PRA MELHOR");
                startActivity(intent);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onClickAdicionarLivro(View view) {
        /*LayoutInflater inflater = getLayoutInflater();

        inflater.inflate(R.layout.activity_detalhes_livro,null);*/

        Intent intent = new Intent(getApplicationContext(),DetalhesLivro.class);
        startActivity(intent);
    }

    @Override
    public void onRefreshListaLivros(ArrayList<Livro> listaLivros){
        listaLivroAdapter = new ListaLivroAdapter(getApplicationContext(), listaLivros);
        listaLivro.setAdapter(listaLivroAdapter);
        listaLivroAdapter.refresh(listaLivros);
    }
    @Override
    public void onUpdateListaLivros(Livro livro, int operacao){

    }
}
