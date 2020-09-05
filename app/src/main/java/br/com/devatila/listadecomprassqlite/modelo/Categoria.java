package br.com.devatila.listadecomprassqlite.modelo;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Categoria implements Serializable {
    private Integer id;
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
