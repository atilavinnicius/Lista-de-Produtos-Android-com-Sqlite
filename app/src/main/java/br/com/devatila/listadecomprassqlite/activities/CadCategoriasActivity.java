package br.com.devatila.listadecomprassqlite.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.com.devatila.listadecomprassqlite.R;
import br.com.devatila.listadecomprassqlite.db.CategoriaDAO;
import br.com.devatila.listadecomprassqlite.modelo.Categoria;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CadCategoriasActivity extends AppCompatActivity {
    private EditText edt_nome_categoria;
    private ListView lst_categorias;
    private List<Categoria> categorias;
    private List<Categoria> categorias_filtradas = new ArrayList<>();
    private CategoriaDAO categoriaDAO;
    private Button btn_editar, btn_salvar_categoria;
    Categoria catEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_categorias);

        edt_nome_categoria = (EditText) findViewById(R.id.edt_nome_categoria);
        lst_categorias = (ListView) findViewById(R.id.lst_categorias);
        btn_editar = (Button) findViewById(R.id.btn_atualizar_catego) ;
        btn_salvar_categoria = (Button) findViewById(R.id.btn_salvar_categoria);
        categoriaDAO = new CategoriaDAO(this);
        categorias = categoriaDAO.pegar_Categorias();
        categorias_filtradas.addAll(categorias);


        ArrayAdapter<Categoria> adaptador = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, categorias_filtradas);
        lst_categorias.setAdapter(adaptador);

        registerForContextMenu(lst_categorias);
    }


    public void cadastrar_categoria(View v) {
        //Cadastrar Categoria

        Categoria c = new Categoria();
        c.setNome(edt_nome_categoria.getText().toString());
        if (c.getNome().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Não deixe nenhum campo Vazio", Toast.LENGTH_LONG).show();
        }
        else {
            long id = categoriaDAO.inserir(c);
            Toast.makeText(getApplicationContext(), "Categoria cadastrada com sucesso!", Toast.LENGTH_LONG).show();
            limpar_campos();
            atualiza_listview();
        }
    }
    public void editar(View v){
        Categoria c = new Categoria();
        c.setId(catEditar.getId());
        c.setNome(edt_nome_categoria.getText().toString());
        if (c.getNome().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Não deixe nenhum campo Vazio", Toast.LENGTH_LONG).show();
        }
        else {
            categoriaDAO.atualizar(c);//Recebe uma categoria
            Toast.makeText(getApplicationContext(), "Categoria atualizada com sucesso!", Toast.LENGTH_LONG).show();
            limpar_campos();
            atualiza_listview();
            btn_salvar_categoria.setVisibility(View.VISIBLE);
            btn_editar.setVisibility(View.GONE);
        }
    }

    public void limpar_campo_categoria(View v) {
        //Limpar campos
        limpar_campos();
    }

    void limpar_campos(){
        edt_nome_categoria.setText("");
        btn_salvar_categoria.setVisibility(View.VISIBLE);
        btn_editar.setVisibility(View.GONE);
    }
    void atualiza_listview(){
        categorias = categoriaDAO.pegar_Categorias();
        categorias_filtradas.clear();
        categorias_filtradas.addAll(categorias);
        lst_categorias.invalidateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_categorias, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.buscar_categoria).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                procurarCategoria(text);
                return false;
            }
        });
        return true;
    }

    @Override //Menu contexto editar e excluir categoria
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_categorias, menu);
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Categoria catExcluir = categorias_filtradas.get(adapterContextMenuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("Atenção")
        .setMessage("Deseja excluir esta Categoria?")
        .setNegativeButton("Não", null)
        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categorias_filtradas.remove(catExcluir);
                categorias.remove(catExcluir);
                categoriaDAO.excluir(catExcluir);
                lst_categorias.invalidateViews();
            }
        }).create();
        dialog.show();

    }
    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        catEditar = categorias_filtradas.get(adapterContextMenuInfo.position);

        edt_nome_categoria.setText(catEditar.getNome().toString());
        btn_salvar_categoria.setVisibility(View.GONE);
        btn_editar.setVisibility(View.VISIBLE);
        lst_categorias.invalidateViews();
    }

    public void procurarCategoria(String nome){
        categorias_filtradas.clear();
        for(Categoria c : categorias){
            if(c.getNome().toLowerCase().contains(nome.toLowerCase())){
                categorias_filtradas.add(c);
            }
        }
        lst_categorias.invalidateViews();
    }
}
