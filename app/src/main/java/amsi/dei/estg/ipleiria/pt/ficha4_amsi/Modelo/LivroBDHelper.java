package amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by User on 28/11/2017.
 */

public class LivroBDHelper extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "livros_BD";
    private static final String TABLE_NAME = "Livro";

    private static final String TITULO_LIVRO ="titulo";
    private static final String SERIE_LIVRO ="serie";
    private static final String AUTOR_LIVRO ="autor";
    private static final String ANO_LIVRO ="ano";
    private static final String CAPA_LIVRO ="capa";
    private final SQLiteDatabase database;

    public LivroBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLivroTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITULO_LIVRO +  " TEXT NOT NULL," +
                SERIE_LIVRO + " TEXT NOT NULL," +
                AUTOR_LIVRO + " TEXT NOT NULL," +
                ANO_LIVRO + " TEXT NOT NULL," +
                CAPA_LIVRO + " INTEGER" +
                ");";

        db.execSQL(createLivroTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " +TABLE_NAME;
        db.execSQL(sql);
        this.onCreate(db);
    }

    public Livro adicionarLivroBD(Livro livro){

        ContentValues value = new ContentValues();

        value.put(TITULO_LIVRO, livro.getTitulo());
        value.put(SERIE_LIVRO, livro.getSerie());
        value.put(AUTOR_LIVRO, livro.getAutor());
        value.put(ANO_LIVRO, livro.getAno());
        value.put(CAPA_LIVRO, livro.getCapa());

        long id = this.database.insert(TABLE_NAME,null, value);
        if (id > -1){
            System.out.println("--> INSERIU BD ID: " + id);
            livro.setIdLivro(id);
            return livro;
        }

        return null;
    }

    public ArrayList<Livro> getAllLivrosBD(){
        ArrayList<Livro> livro = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = this.database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Livro auxLivro = new Livro(cursor.getLong(0),
                                           cursor.getString(1),
                                           cursor.getString(2),
                                           cursor.getString(3),
                                           cursor.getString(4),
                                           cursor.getString(5));
                auxLivro.setIdLivro(cursor.getLong(0));
                livro.add(auxLivro);
            }while(cursor.moveToNext());
        }
        return livro;
    }

    public boolean guardarLivroBD(Livro livro){
        ContentValues value = new ContentValues();

        value.put(TITULO_LIVRO, livro.getTitulo());
        value.put(SERIE_LIVRO, livro.getSerie());
        value.put(AUTOR_LIVRO, livro.getAutor());
        value.put(ANO_LIVRO, livro.getAno());
        value.put(CAPA_LIVRO, livro.getCapa());

        return this.database.update(TABLE_NAME, value, "id = ?", new String[]{"" + livro.getIdLivro()}) > 0;

    }

    public boolean removerLivroBD(long idLivro){
        /*this.database.delete(TABLE_NAME, "id = ?",
                new String[]{"" + idLivro});*/
        return (this.database.delete(TABLE_NAME,"id = ?", new String[]{"" + idLivro}) > 0);
    }

    public void removerAllLivrosBD() {
        this.database.delete(TABLE_NAME, null,null);
    }
}
