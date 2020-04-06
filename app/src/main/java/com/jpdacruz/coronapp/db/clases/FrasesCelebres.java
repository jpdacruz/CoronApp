package com.jpdacruz.coronapp.db.clases;

public class FrasesCelebres {

    private String frase;
    private String autor;
    private int foto;

    public FrasesCelebres(String frase, String autor, int foto) {
        this.frase = frase;
        this.autor = autor;
        this.foto = foto;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
