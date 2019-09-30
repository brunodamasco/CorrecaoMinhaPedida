package senac.renato.correcaominhapedida.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.dao.ItemComandaDao;
import senac.renato.correcaominhapedida.model.Comanda;
import senac.renato.correcaominhapedida.model.ItemComanda;
import senac.renato.correcaominhapedida.uteis.Constantes;
import senac.renato.correcaominhapedida.view.AddItemComandaActivity;

public class ComandaControl {
    private Activity activity;
    private TextView tvTotal;

    private ListView lvItemComanda;
    private List<ItemComanda> listItem;
    private ArrayAdapter<ItemComanda> adapterItemComanda;
    private ItemComanda itemComanda;
    private Comanda comanda;

    private ItemComandaDao itemComandaDao;

    public ComandaControl(Activity activity) {
        this.activity = activity;
        itemComandaDao = new ItemComandaDao(activity);

        comanda = getComandaParam();

        initComponents();
        atualizarTvTotal();
    }

    private Comanda getComandaParam(){
        return (Comanda) activity.getIntent().getSerializableExtra("comanda");
    }

    private void initComponents(){
        tvTotal = activity.findViewById(R.id.tvTotal);
        lvItemComanda = activity.findViewById(R.id.lvItemComanda);
        configListView();
    }

    private void configListView(){
        if (comanda.getItemComandas() != null){
            listItem = new ArrayList<>(comanda.getItemComandas());
        } else {
            listItem = new ArrayList<>();
        }

        adapterItemComanda = new ArrayAdapter<>(
            activity,
            android.R.layout.simple_list_item_1,
            listItem
        );
        lvItemComanda.setAdapter(adapterItemComanda);
        cliqueCurto();
        cliqueLongo();
    }

    private void cliqueCurto(){
        lvItemComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemComanda = adapterItemComanda.getItem(position);
                dialogAddMaisUm(itemComanda);
            }
        });
    }

    private void dialogAddMaisUm(final ItemComanda item){
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Mostrando item");
        alerta.setMessage("Adicionar ou remover 1");
        alerta.setPositiveButton("+1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item.addQuantidade();
                try {
                    if(itemComandaDao.getDao().update(item)>0){
                        adapterItemComanda.notifyDataSetChanged();
                        itemComanda = null;
                        atualizarTvTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        alerta.setNegativeButton("-1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item.removeQuantidade();
                if(item.getQuantidade()==0){
                    try {
                        if(itemComandaDao.getDao().delete(item)>0){
                            adapterItemComanda.remove(item);
                            itemComanda = null;
                            atualizarTvTotal();
                            adapterItemComanda.notifyDataSetChanged();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if(itemComandaDao.getDao().update(item)>0){
                            adapterItemComanda.notifyDataSetChanged();
                            itemComanda = null;
                            atualizarTvTotal();
                            adapterItemComanda.notifyDataSetChanged();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemComanda = null;
            }
        });
        alerta.show();
    }

    private void cliqueLongo(){
        lvItemComanda.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemComanda = adapterItemComanda.getItem(position);
                dialogExcluir(itemComanda);
                return true;
            }
        });
    }

    private void dialogExcluir(final ItemComanda item){
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo item");
        alerta.setMessage(item.toString());
        alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemComanda = null;
            }
        });
        alerta.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if(itemComandaDao.getDao().delete(itemComanda)>0){
                        adapterItemComanda.remove(item);
                        itemComanda = null;
                        atualizarTvTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        alerta.show();
    }

    private Double getTotal(){
        double total = 0;
        for(ItemComanda item : listItem){
            total += item.getSubtotal();
        }
        return total;
    }

    private void atualizarTvTotal(){
        tvTotal.setText("Total: R$" + getTotal());
    }

    public void AddItemComandaAction(){
        Intent it = new Intent(activity, AddItemComandaActivity.class);
        activity.startActivityForResult(it, Constantes.REQUEST_ITEM_COMANDA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == activity.RESULT_OK){
            if(requestCode==Constantes.REQUEST_ITEM_COMANDA){
                ItemComanda item = (ItemComanda) data.getSerializableExtra(Constantes.PARAM_ITEM_COMANDA);
                item.setComanda(comanda);
                try {
                    if(itemComandaDao.getDao().create(item)>0){
                        adapterItemComanda.add(item);
                        atualizarTvTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } else if(resultCode==activity.RESULT_CANCELED){
            Toast.makeText(activity, "Ação cancelada", Toast.LENGTH_SHORT).show();
        }
    }

    public void limparListAction(){
        adapterItemComanda.clear();
        atualizarTvTotal();
    }
}
