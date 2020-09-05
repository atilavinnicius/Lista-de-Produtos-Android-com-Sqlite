package br.com.devatila.listadecomprassqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.devatila.listadecomprassqlite.modelo.Categoria;

public class CategoriaDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public CategoriaDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();//conexao para escrita
    }
    //metodo inserção categoria
    public long inserir(Categoria categoria){
        ContentValues values = new ContentValues();
        values.put("nome", categoria.getNome());

        return banco.insert(Conexao.TABELA_CATEGORIA, null, values);
    }
    //consultar categorias
    public List<Categoria> pegar_Categorias(){
        List<Categoria> categorias = new ArrayList<>();
        Cursor cursor = banco.query(Conexao.TABELA_CATEGORIA, new String[]{"id_categoria", "nome"}, null, null, null, null, null);
        while(cursor.moveToNext()){
            Categoria c = new Categoria();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            categorias.add(c);
        }
        return categorias;
    }

    public void excluir(Categoria c){
        banco.delete(Conexao.TABELA_CATEGORIA, "id_categoria = ?", new String[]{c.getId().toString()});
    }

    public void atualizar(Categoria c){
        ContentValues values = new ContentValues();
        values.put("nome", c.getNome());
        banco.update(Conexao.TABELA_CATEGORIA, values, "id_categoria = ?", new String[]{c.getId().toString()});

    }
}
