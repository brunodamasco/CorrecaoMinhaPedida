package senac.renato.correcaominhapedida.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.dao.CategoriaDao;
import senac.renato.correcaominhapedida.dao.ProdutoDao;
import senac.renato.correcaominhapedida.model.Categoria;
import senac.renato.correcaominhapedida.model.Produto;
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
        try {
            listProduto = produtoDao.getDao().queryForAll();
            adapterProduto = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_spinner_dropdown_item,
                    listProduto
            );
            lvProduto.setAdapter(adapterProduto);
            cliqueCurto();
            cliqueLongo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNovaCategoria() {
        Intent it = new Intent(activity, CategoriaActivity.class);
        activity.startActivity(it);
    }

    private Produto getDadosForm() {
        Produto p = new Produto();
        p.setCategoria((Categoria) spCategoria.getSelectedItem());
        p.setNome(edproduto.getText().toString());
        p.setValor(edValor.getText().toString());
        return p;
    }

    private void cliqueCurto() {
        lvProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produto = adapterProduto.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Visualizando produto");
                alerta.setMessage(produto.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produto = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        carregarForm(produto);
                    }
                });
                alerta.show();
            }
        });
    }

    private void cliqueLongo() {
        lvProduto.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = adapterProduto.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Excluindo produto");
                alerta.setMessage(produto.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produto = null;
                    }
                });
                alerta.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if(produtoDao.getDao().delete(produto)>0){
                                adapterProduto.remove(produto);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alerta.show();
                return true;
            }
        });
    }

    public void salvarAction(){
        if(produto==null){
            produto = getDadosForm();
        } else {
            Produto p = getDadosForm();
            produto.setNome(p.getNome());
            produto.setValor(p.getValor());
        }

        try {
            Dao.CreateOrUpdateStatus res = produtoDao.getDao().createOrUpdate(produto);
            if(res.isCreated()){
                adapterProduto.add(produto);
            }else if(res.isUpdated()){
                adapterProduto.notifyDataSetChanged();
            }
            produto = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarForm(Produto p){
        edproduto.setText(p.getNome());
        edValor.setText(String.valueOf(p.getValor()));
    }

}
