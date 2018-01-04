package amsi.dei.estg.ipleiria.pt.ficha4_amsi.Modelo;

/**
 * Created by User on 24/10/2017.
 */

public class Livro {
    private long idLivro;
    private String titulo;
    private String serie;
    private String autor;
    private String ano;
    private String capa;

    //private static long autoIncrementId = 0;

    public Livro(long idLivro, String titulo, String serie, String autor, String ano, String capa) {

        this.idLivro = idLivro;
        this.titulo = titulo;
        this.serie = serie;
        this.autor = autor;
        this.ano = ano;
        this.capa = capa;
    }

    public long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(long idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", serie='" + serie + '\'' +
                ", autor='" + autor + '\'' +
                ", ano='" + ano + '\'' +
                ", capa=" + capa +
                '}';
    }
}
