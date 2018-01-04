package amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo;

import android.app.DownloadManager;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.pt.ficha4_amsi.listeners.LivrosListener;
import amsi.dei.estg.ipleiria.pt.ficha4_amsi.utils.LivroJsonParser;

/**************
 * Created by USER on 14/11/2017.
 */

public class SingletonLivros implements LivrosListener{
    private static  SingletonLivros INSTANCE = null;

    private LivroBDHelper livroBDHelper = null;
    private ArrayList<Livro> livros;
    private String mURLAPILivros = "http://amsi201718.ddns.net/api/livros";
    private String mURLAPILogin = "http://amsi201718.ddns.net/api/auth/login";
    private String tokenAPI = "AMSI-TOKEN";

    private static RequestQueue volleyQueue = null;

    private LivrosListener livrosListener;

    public static synchronized SingletonLivros getInstance(Context context) {
        if (INSTANCE == null) {
             INSTANCE  = new SingletonLivros(context);
             volleyQueue = Volley.newRequestQueue(context);
        }

        return INSTANCE;
    }

    private SingletonLivros(Context context) {
        livros=new ArrayList<>();
        livroBDHelper = new LivroBDHelper(context);
        //gerarFakeData();
    }

    /*private void gerarFakeData(){
        livros.add(new Livro("Programar Android em AMSI","Android SAGA","GOKU n' Friends","1999", R.drawable.programarandroid2));
        livros.add(new Livro("Me am Supah Sand","Android SAGA","GOKU n' Friends","1999", R.drawable.programarandroid1));
        livros.add(new Livro("Programar Android em AMSI","Android SAGA","Not Akira Toryama","1999", R.drawable.programarandroid2));
        livros.add(new Livro("Programar Android em AMSI","Baguette SAGA","GOKU n' Friends","1999", R.drawable.programarandroid2));
        livros.add(new Livro("Hitler Year","Mech SAGA","Frau Engle","99999", R.drawable.programarandroid1));
        livros.add(new Livro("Programar Android em AMSI","Android SAGA","GOKU n' Friends","1999", R.drawable.programarandroid2));
        livros.add(new Livro("Programar Android em AMSI","Android SAGA","GOKU n' Friends","1999", R.drawable.programarandroid1));
    }*/

    public ArrayList getLivrosBD(){
        livros = livroBDHelper.getAllLivrosBD();

        return livros;
    }

    public void adicionarLivroBD(Livro livro){
        livroBDHelper.adicionarLivroBD(livro);
    }

    public void adicionarLivrosBD(ArrayList<Livro> livros){
        livroBDHelper.removerAllLivrosBD();

        for (Livro livro: livros) {
            adicionarLivroBD(livro);
        }
    }

    public void editarLivroBD(int value, Livro livro){

        if (livros.get(value) != null){
            if (livroBDHelper.guardarLivroBD(livro)){
                livro.setIdLivro(value);
                livros.add(value, livro);
            }
        }

    }

    public void removerLivroBD(int value) {

        if (livros.get(value) != null){
            if (livroBDHelper.removerLivroBD(livros.get(value).getIdLivro())){
                livros.remove(value);
            }
        }

    }

    public ArrayList pesquisarLivroID(int idLivro){

        livros.get(idLivro);

        return livros;
    }


    public void getAllLivrosAPI(final Context context, boolean isConnected){
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mURLAPILivros, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                livros = LivroJsonParser.parserJsonLivros(response, context);
                System.out.println("--> Resposta " + response);

                adicionarLivrosBD(livros);

                if (livrosListener != null){
                    livrosListener.onRefreshListaLivros(livros);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error Getalllivros " + error.getMessage());
            }
        });

        volleyQueue.add(req);

    }

    public void adicionarLivroApi(final Livro livro, final Context context){
        StringRequest request = new StringRequest(Request.Method.POST, mURLAPILivros, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("-->Resposta ADD Post: " + response);

                if (livrosListener != null){
                    livrosListener.onUpdateListaLivros(LivroJsonParser.parserJsonLivro(response, context),1);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error  " + error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", tokenAPI);
                params.put("titulo", livro.getTitulo());
                params.put("serie", livro.getSerie());
                params.put("autor", livro.getAutor());
                params.put("ano", livro.getAno());
                params.put("capa", livro.getCapa().toString());
                return params;
            }
        };

        volleyQueue.add(request);
    }

    public void removerLivroApi(final Livro livro, final Context context){
        StringRequest request = new StringRequest(Request.Method.DELETE, mURLAPILivros, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("-->Resposta Delete Post: " + response);

                if (livrosListener != null){
                    livrosListener.onUpdateListaLivros(LivroJsonParser.parserJsonLivro(response, context),3);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error  " + error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", tokenAPI);
                params.put("titulo", livro.getTitulo());
                params.put("serie", livro.getSerie());
                params.put("autor", livro.getAutor());
                params.put("ano", livro.getAno());
                params.put("capa", livro.getCapa().toString());
                return params;
            }
        };

        volleyQueue.add(request);
    }

    public void setLivrosListener(LivrosListener livrosListener){
        this.livrosListener = livrosListener;
    }

    @Override
    public void onRefreshListaLivros(ArrayList<Livro> listaLivros){

    }

    @Override
    public void onUpdateListaLivros(Livro livro, int operacao) {

    }
}
