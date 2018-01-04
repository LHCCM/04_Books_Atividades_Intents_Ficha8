package amsi.dei.estg.ipleiria.pt.ficha4_amsi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class NewActivity extends AppCompatActivity {

    private TextView vEmail;

    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        setTitle("BOOKS");
        mEmail = getIntent().getStringExtra(LoginActivity.DADOS_EMAIL);
        Toast.makeText(this, ""+mEmail, Toast.LENGTH_SHORT).show();

        vEmail = (TextView) findViewById(R.id.textViewEmail);
        vEmail.setText(mEmail);

    }

    public void onClickEnviar(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"+mEmail));
        intent.putExtra(Intent.EXTRA_SUBJECT,"UMA DISGRAÇA");
        intent.putExtra(Intent.EXTRA_TEXT,"SUA VIDA É UMA DISGRAÇA E A MINHA TAMBÉM NÃO VAI PRA MELHOR");
        startActivity(intent);
    }

    public void onClickListaLivros(View view) {
        Intent lista = new Intent(getApplicationContext(), ListaLivros.class);
        startActivity(lista);

    }

    public void onClickGrelhaLivros(View view) {
        Intent grelha = new Intent(getApplicationContext(),GrelhaLivros.class);
        startActivity(grelha);
    }
}
