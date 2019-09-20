package senac.renato.correcaominhapedida.control;

import android.app.Activity;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.dao.CategoriaDao;
import senac.renato.correcaominhapedida.dao.ProdutoDao;
import senac.renato.correcaominhapedida.model.Categoria;
import senac.renato.correcaominhapedida.model.Produto;
import senac.renato.correcaominhapedida.uteis.Constantes;
import senac.renato.correcaominhapedida.view.CategoriaActivity;

public class ProdutoControl {
    private Activity activity;
    private Spinner spCategoria;
    private EditText edproduto;
    private EditText edValor;
    private ListView lvProduto;
    private TextView tvTotalProduto;

    private List<Categoria> listCategoria;
    private List<Produto> listProduto;

    private ArrayAdapter<Categoria> adapterCategoria;
    private ArrayAdapter<Produto> adapterProduto;

    private CategoriaDao categoriaDao;
    private ProdutoDao produtoDao;
    private Produto produto;

    public ProdutoControl(Activity activity) {
        this.activity = activity;
        categoriaDao = new CategoriaDao(activity);
        produtoDao = new ProdutoDao(activity);
        initComponents();
    }

    private void initComponents() {
        spCategoria = activity.findViewById(R.id.spCategoria);
        edproduto = activity.findViewById(R.id.edProduto);
        edValor = activity.findViewById(R.id.edValor);
        lvProduto = activity.findViewById(R.id.lvProduto);
        tvTotalProduto = activity.findViewById(R.id.tvTotalProduto);
        configSpinnerCategoria();
        configListViewProduto();
    }

    private void configSpinnerCategoria() {
        try {
            categoriaDao.getDao().createIfNotExists(new Categoria(1, "Alimentos"));
            categoriaDao.getDao().createIfNotExists(new Categoria(2, "Bebidas"));
            categoriaDao.getDao().createIfNotExists(new Categoria(3, "Diversos"));
            listCategoria = categoriaDao.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapterCategoria = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_spinner_item,
                listCategoria
        );
        spCategoria.setAdapter(adapterCategoria);
    }

    private void configListViewProduto() {
        listProduto = new ArrayList<>();
        adapterProduto = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_spinner_dropdown_item,
                listProduto
        );
        lvProduto.setAdapter(adapterProduto);
        cliqueCurto();
        cliqueLongo();
    }

    public void addNovaCategoria() {
        Intent it = new Intent(activity, CategoriaActivity.class);
        activity.startActivityForResult(it, Constantes.REQUEST_CATEGORIA);
    }

    private Produto getDadosForm() {
        Produto p = new Produto();
        p.setCategoria((Categoria) spCategoria.getSelectedItem());
        p.setNome(edproduto.getText().toString());
        p.setValor(edValor.getText().toString());
        return p;
    }

    public void enviarItemAction() {
        Produto produto = getDadosForm();
        Intent it = new Intent();
        it.putExtra(Constantes.PARAM_PRODUTO, produto);
        activity.setResult(activity.RESULT_OK, it);
        activity.finish();
    }

    private void cliqueCurto() {
        /*lvProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = adapterProduto.getItem(position);
                dialogAddMaisUm(produto);
            }
        });*/
    }

    private void dialogAddMaisUm(final Produto prod){
        /*AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Mostrando item");
        alerta.setMessage("Adicionar ou remover Produto");
        alerta.setPositiveButton("Adicionar Produto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                prod.addQuantidade();
                adapterItemComanda.notifyDataSetChanged();
                itemComanda = null;
                atualizarTvTotal();
            }
        });
        alerta.setNegativeButton("Remover Produto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                prod.removeQuantidade();
                adapterItemComanda.notifyDataSetChanged();
                if(item.getQuantidade()==0){
                    adapterItemComanda.remove(prod);
                }
                itemComanda = null;
                atualizarTvTotal();
            }
        });
        alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemComanda = null;
            }
        });
        alerta.show();*/
    }

    private void cliqueLongo() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == activity.RESULT_OK){
            if(requestCode==Constantes.REQUEST_PRODUTO){
                Produto item = (Produto) data.getSerializableExtra(Constantes.PARAM_PRODUTO);
                adapterProduto.add(item);
                //atualizarTvTotal();
            }
        } else if(resultCode==activity.RESULT_CANCELED){
            Toast.makeText(activity, "Ação cancelada", Toast.LENGTH_SHORT).show();
        }
    }

}
