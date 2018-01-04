package amsi.dei.estg.ipleiria.pt.ficha4_amsi;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.Livro;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.SingletonLivros;

public class DetalhesLivro extends AppCompatActivity {

    private Integer value;

    private ArrayList<Livro> listaLivros;
    private List<Livro> livros;
    private Livro livro;

    private EditText vTitulo;
    private EditText vSerie;
    private EditText vAutor;
    private EditText vAno;
    private ImageView vImage;
    private FloatingActionButton fab_save;


    private EditText vID;
    private boolean checkAlter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        value = getIntent().getIntExtra("DADOS_LIVRO",-1);

        Toast.makeText(this, ""+value, Toast.LENGTH_SHORT).show();

        listaLivros = SingletonLivros.getInstance(getApplicationContext()).getLivrosBD();

        vTitulo = (EditText) findViewById(R.id.labelForT);
        vSerie = (EditText) findViewById(R.id.labelForS);
        vAutor = (EditText) findViewById(R.id.labelForAu);
        vAno = (EditText) findViewById(R.id.labelForAno);
        vImage = (ImageView) findViewById(R.id.imageView);

        if (value == -1){
            fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
            fab_save.setImageResource(R.drawable.ic_action_add);
        }

        else {
            loadLivros();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       if (value!=-1) {
           MenuInflater inflater = getMenuInflater();
           inflater.inflate(R.menu.menu_detalhes_livro, menu);
       }
           return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.itemRemover:

                SingletonLivros.getInstance(getApplicationContext()).removerLivroBD(value);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View view) {

        if (vTitulo.getText().toString().equals("")||vSerie.getText().toString().equals("")||vAutor.getText().toString().equals("")||vAno.getText().toString().equals(""))
        {
            Toast.makeText(this, "Campos vazios", Toast.LENGTH_SHORT).show();
        }
        else {
            if (value == -1) //novo livro
            {
                SingletonLivros.getInstance(getApplicationContext()).adicionarLivroApi(new Livro(0,vTitulo.getText().toString(), vSerie.getText().toString(), vAutor.getText().toString(), vAno.getText().toString(), "http://amsi201718.ddns.net/img/ipl_semfundo.png"), getApplicationContext());
                checkAlter = true;

            } else if (value > -1)//editar livro
            {
                SingletonLivros.getInstance(getApplicationContext()).editarLivroBD(value, new Livro(0,vTitulo.getText().toString(), vSerie.getText().toString(), vAutor.getText().toString(), vAno.getText().toString(), livro.getCapa()));

                checkAlter = true;
            }
        }

    }

    public void loadLivros(){
        livros = listaLivros;

        livro = livros.get(value);

        setTitle("Detalhes: " + livro.getTitulo());

        vTitulo.setText(livro.getTitulo());
        vSerie.setText(livro.getSerie());
        vAutor.setText(livro.getAutor());
        vAno.setText(livro.getAno());

        //set Image
        Glide.with(getApplicationContext())
                .load(livro.getCapa())
                .placeholder(R.drawable.ipl_semfundo)
                .thumbnail(0f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vImage);
    }

}
