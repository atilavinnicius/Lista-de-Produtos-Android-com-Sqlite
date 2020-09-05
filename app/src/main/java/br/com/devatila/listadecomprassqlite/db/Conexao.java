package br.com.devatila.listadecomprassqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String name_db = "listadecompras.db";
    private static final int version = 1;
    static final String TABELA_CATEGORIA = "categorias";
    static final String TABELA_PRODUTO = "produtos";

    public Conexao(Context context) {
        super(context, name_db, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //Criar tabelas
        //tabela categorias
        db.execSQL("create table "+ TABELA_CATEGORIA + "(id_categoria integer primary key autoincrement, " +
                "nome varchar(100) not null)");
        //tabela produtos
        db.execSQL("create table "+ TABELA_PRODUTO +"(id_produto integer primary key autoincrement, " +
                "nome varchar(50) , quantidade integer , preco REAL, categoria varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
