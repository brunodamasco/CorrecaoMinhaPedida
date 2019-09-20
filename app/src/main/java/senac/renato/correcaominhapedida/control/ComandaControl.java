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

import java.util.ArrayList;
import java.util.List;

import senac.renato.correcaominhapedida.R;
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

    public ComandaControl(Activity activity) {
        this.activity = activity;
        initComponents();
        atualizarTvTotal();
    }

    private void initComponents(){
        tvTotal = activity.findViewById(R.id.tvTotal);
        lvItemComanda = activity.findViewById(R.id.lvItemComanda);
        configListView();
    }

    private void configListView(){
        listItem = new ArrayList<>();
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
                adapterItemComanda.notifyDataSetChanged();
                itemComanda = null;
                atualizarTvTotal();
            }
        });
        alerta.setNegativeButton("-1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item.removeQuantidade();
                adapterItemComanda.notifyDataSetChanged();
                if(item.getQuantidade()==0){
                    adapterItemComanda.remove(item);
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
                adapterItemComanda.remove(item);
                itemComanda = null;
                atualizarTvTotal();
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
                adapterItemComanda.add(item);
                atualizarTvTotal();
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
