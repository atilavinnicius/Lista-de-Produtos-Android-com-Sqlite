package br.com.devatila.listadecomprassqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.devatila.listadecomprassqlite.modelo.Categoria;
import br.com.devatila.listadecomprassqlite.modelo.Produto;

public class ProdutoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ProdutoDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();//conexao de escrita
    }
    //inserir produto
    public long inserir(Produto produto){//Receber um produto
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("quantidade", produto.getQuantidade());
        values.put("preco", produto.getPreco());
        values.put("categoria", produto.getCategoria());

        return banco.insert(Conexao.TABELA_PRODUTO, null, values);
    }

    //consultar produtos
    public List<Produto> pegar_Produtos(){
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = banco.query(Conexao.TABELA_PRODUTO, new String[]{"id_produto", "nome", "quantidade", "preco", "categoria"}, null, null, null, null, null);
        while(cursor.moveToNext()){
            Produto p = new Produto();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setQuantidade(cursor.getString(2));
            p.setPreco(cursor.getString(3));
            p.setCategoria(cursor.getString(4));
            produtos.add(p);
        }
        return produtos;
    }
    public void excluir(Produto p){
        banco.delete(Conexao.TABELA_PRODUTO, "id_produto = ?", new String[]{p.getId().toString()});
    }
}
