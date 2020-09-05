package br.com.devatila.listadecomprassqlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import br.com.devatila.listadecomprassqlite.MainActivity;
import br.com.devatila.listadecomprassqlite.R;
import br.com.devatila.listadecomprassqlite.db.CategoriaDAO;
import br.com.devatila.listadecomprassqlite.db.ProdutoDAO;
import br.com.devatila.listadecomprassqlite.modelo.Categoria;
import br.com.devatila.listadecomprassqlite.modelo.Produto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class CadProdutoActivity extends AppCompatActivity {
    private EditText edt_nome_produto, edt_qnt_produto, edt_preco_produto;
    private Spinner spn_categoria_produto;
    private ProdutoDAO produtoDAO;
    private CategoriaDAO catDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_produto);
        catDao = new CategoriaDAO(this);
        produtoDAO = new ProdutoDAO(this);

        edt_nome_produto = (EditText) findViewById(R.id.edt_nome_produto);
        edt_qnt_produto = (EditText) findViewById(R.id.edt_qnt_produto);
        edt_preco_produto = (EditText) findViewById(R.id.edt_preco_produto);
        spn_categoria_produto = (Spinner) findViewById(R.id.spn_categoria_produto);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catDao.pegar_Categorias());
        spn_categoria_produto.setAdapter(adapter);



    }

    public void cadastrar_produto(View v){
        Produto p = new Produto();
        System.out.println(edt_nome_produto.getText().toString());
        System.out.println(edt_qnt_produto.getText().toString());
        System.out.println(edt_preco_produto.getText().toString());
        System.out.println(spn_categoria_produto.getSelectedItem().toString());

        p.setNome(edt_nome_produto.getText().toString());
        p.setQuantidade(edt_qnt_produto.getText().toString());
        p.setPreco(edt_preco_produto.getText().toString());
        p.setCategoria(spn_categoria_produto.getSelectedItem().toString());
        long id = produtoDAO.inserir(p);
        System.out.println(id);
        limpar_campos();
        startActivity(new Intent(this, MainActivity.class));
    }
    public void limpar_campos_produtos(View v){
        limpar_campos();
    }

    void limpar_campos(){
        //Limpar campos dos produtos
        edt_nome_produto.setText("");
        edt_qnt_produto.setText("");
        edt_preco_produto.setText("");
        //spn_categoria_produto resetar spinner
        spn_categoria_produto.setSelection(0);
    }
}
