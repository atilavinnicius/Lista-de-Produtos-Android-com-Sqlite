package br.com.devatila.listadecomprassqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.com.devatila.listadecomprassqlite.activities.CadCategoriasActivity;
import br.com.devatila.listadecomprassqlite.activities.CadProdutoActivity;
import br.com.devatila.listadecomprassqlite.adapter.ProdutoAdapter;
import br.com.devatila.listadecomprassqlite.db.ProdutoDAO;
import br.com.devatila.listadecomprassqlite.modelo.Categoria;
import br.com.devatila.listadecomprassqlite.modelo.Produto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lst_produtos;
    private List<Produto> produtos;
    private List<Produto> produtos_filtrados = new ArrayList<>();
    private ProdutoDAO produtoDAO;
    private TextView total;
    private String t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst_produtos = (ListView) findViewById(R.id.lst_produtos);
        total = (TextView) findViewById(R.id.txt_total_em_compras);
        produtoDAO = new ProdutoDAO(this);
        produtos = produtoDAO.pegar_Produtos();
        produtos_filtrados.addAll(produtos);

        ProdutoAdapter adaptador = new ProdutoAdapter(this, produtos_filtrados);
        lst_produtos.setAdapter(adaptador);
        setatotal();

        registerForContextMenu(lst_produtos);
    }

    @Override //Menu contexto editar e excluir produtos
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_main, menu);
    }

    public void apagar_produto(MenuItem item){
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Produto prodExcluir = produtos_filtrados.get(adapterContextMenuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja excluir este Produto?")
                .setNegativeButton("Não", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produtos_filtrados.remove(prodExcluir);
                        produtos.remove(prodExcluir);
                        produtoDAO.excluir(prodExcluir);
                        lst_produtos.invalidateViews();
                        setatotal();
                    }
                }).create();
        dialog.show();

    }

    public void setatotal(){
        t = String.valueOf(produtos_filtrados.size());
        total.setText(t);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.buscar_produto).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                procurarProduto(text);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.buscar_categoria:
                return true;
            case R.id.menu_cad_produto:
                startActivity(new Intent(this, CadProdutoActivity.class));
                return true;
            case R.id.menu_cad_categoria:
                startActivity(new Intent(this, CadCategoriasActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setatotal();
    }

    public void procurarProduto(String nome){
        produtos_filtrados.clear();
        for(Produto p : produtos){
            if(p.getNome().toLowerCase().contains(nome.toLowerCase())){
                produtos_filtrados.add(p);
            }
        }
        lst_produtos.invalidateViews();
    }
}
