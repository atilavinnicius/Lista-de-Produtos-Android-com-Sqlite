package br.com.devatila.listadecomprassqlite.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.devatila.listadecomprassqlite.R;
import br.com.devatila.listadecomprassqlite.modelo.Produto;

public class ProdutoAdapter extends BaseAdapter {

    private List<Produto> produtos;
    private Activity activity;

    public ProdutoAdapter(Activity activity, List<Produto> lista) {
        this.activity = activity;
        this.produtos = lista;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.item_produto, parent, false);
        TextView nome = v.findViewById(R.id.txt_nomeproduto);
        TextView valor = v.findViewById(R.id.txt_valorporduto);
        TextView quantidade = v.findViewById(R.id.txt_quantidadeproduto);
        TextView categoria = v.findViewById(R.id.txt_categoriaproduto);

        Produto p = produtos.get(position);
        nome.setText(p.getNome());
        valor.setText(p.getPreco());
        quantidade.setText(p.getQuantidade());
        categoria.setText(p.getCategoria());

        return v;
    }
}
