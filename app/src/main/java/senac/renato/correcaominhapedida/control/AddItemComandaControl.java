package senac.renato.correcaominhapedida.control;

import android.app.Activity;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.dao.ItemComandaDao;
import senac.renato.correcaominhapedida.dao.ProdutoDao;
import senac.renato.correcaominhapedida.model.ItemComanda;
import senac.renato.correcaominhapedida.model.Produto;
import senac.renato.correcaominhapedida.uteis.Constantes;
import senac.renato.correcaominhapedida.view.AddItemComandaActivity;
import senac.renato.correcaominhapedida.view.ProdutoActivity;

public class AddItemComandaControl {
    private Activity activity;

    private Spinner spProduto;
    private List<Produto> listProduto;
    private ArrayAdapter<Produto> adapterProduto;

    private NumberPicker npQuantidade;

    private ProdutoDao produtoDao;

    public AddItemComandaControl(Activity activity) {
        this.activity = activity;
        produtoDao = new ProdutoDao(activity);
        initComponents();
    }

    private void initComponents() {
        spProduto = activity.findViewById(R.id.spProduto);
        npQuantidade = activity.findViewById(R.id.npQuantidade);
        configNumberPicker();
        configSpinner();
    }

    private void configNumberPicker() {
        npQuantidade.setMinValue(Constantes.MIN_QUANTIDADE_ITEM);
        npQuantidade.setMaxValue(Constantes.MAX_QUANTIDADE_ITEM);
    }

    private void configSpinner() {
        try {
            produtoDao.getDao().createIfNotExists(new Produto(1, "Refrigerante", 3.00));
            produtoDao.getDao().createIfNotExists(new Produto(2, "Cerveja", 5.00));
            produtoDao.getDao().createIfNotExists(new Produto(3, "Batata Frita", 10.00));
            produtoDao.getDao().createIfNotExists(new Produto(4, "Água", 2.50));
            produtoDao.getDao().createIfNotExists(new Produto(5, "Pastel", 3.50));
            produtoDao.getDao().createIfNotExists(new Produto(6, "Petiscos", 6.00));
            listProduto = produtoDao.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapterProduto = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_spinner_item,
                listProduto
        );
        spProduto.setAdapter(adapterProduto);
    }

    private ItemComanda getDadosForm() {
        ItemComanda i = new ItemComanda();
        i.setProduto((Produto) spProduto.getSelectedItem());
        i.setQuantidade(npQuantidade.getValue());
        return i;
    }

    public void enviarItemAction() {
        ItemComanda itemComanda = getDadosForm();
        Intent it = new Intent();
        it.putExtra(Constantes.PARAM_ITEM_COMANDA, itemComanda);
        activity.setResult(activity.RESULT_OK, it);
        activity.finish();
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

    public void cancelarAction() {
        activity.setResult(activity.RESULT_CANCELED);
        activity.finish();
    }


    public void addNovoProduto() {
        Intent it = new Intent(activity, ProdutoActivity.class);
        activity.startActivityForResult(it, Constantes.REQUEST_PRODUTO);
    }
}
