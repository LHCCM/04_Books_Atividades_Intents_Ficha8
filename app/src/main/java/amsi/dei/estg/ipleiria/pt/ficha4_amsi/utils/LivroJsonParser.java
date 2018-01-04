package amsi.dei.estg.ipleiria.pt.ficha4_amsi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo.Livro;

/**
 * Created by User on 12/12/2017.
 */

public class LivroJsonParser {

    public static ArrayList parserJsonLivros(JSONArray response, Context context){
        System.out.println("--> Parser Lista Livros: " + response.toString());

        ArrayList<Livro> tempListaLivros = new ArrayList<Livro>();

        try {
            for (int i = 0; i<response.length(); i++){
                JSONObject livro = (JSONObject) response.get(i);

                int idLivro = livro.getInt("id");
                String titulo = livro.getString("titulo");
                String serie = livro.getString("serie");
                String autor = livro.getString("autor");
                String ano = livro.getString("ano");
                String capa = livro.getString("capa");

                Livro auxLivro = new Livro(idLivro,titulo,serie,autor,ano,capa);
                tempListaLivros.add(auxLivro);
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return tempListaLivros;
    }



    public static Livro parserJsonLivro(String response, Context context){
        System.out.println("--> Parser Livro: " + response.toString());

        Livro auxlivro = null;

        try {
                JSONObject livro = new JSONObject(response);

                int idLivro = livro.getInt("id");
                String titulo = livro.getString("titulo");
                String serie = livro.getString("serie");
                String autor = livro.getString("autor");
                String ano = livro.getString("ano");
                String capa = livro.getString("capa");

                auxlivro = new Livro(idLivro,titulo,serie,autor,ano,capa);

        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return auxlivro;
    }

    public static String parserJsonLogin(String response, Context context){
        return null;
    }

    public static Boolean isConnectionInternet(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
