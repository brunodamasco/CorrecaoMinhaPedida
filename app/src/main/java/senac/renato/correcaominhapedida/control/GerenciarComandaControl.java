package senac.renato.correcaominhapedida.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.dao.ComandaDao;
import senac.renato.correcaominhapedida.model.Comanda;
import senac.renato.correcaominhapedida.view.ComandaActivity;

public class GerenciarComandaControl {
    private Activity activity;
    private EditText editMesa;
    private EditText editLocal;
    private ListView lvComanda;
    private ArrayAdapter<Comanda> adapterComanda;
    private ComandaDao comandaDao;

    public GerenciarComandaControl(Activity activity) {
        this.activity = activity;
        comandaDao = new ComandaDao(activity);
        initComponents();
    }

    private void initComponents(){
        editMesa = activity.findViewById(R.id.editMesa);
        editLocal = activity.findViewById(R.id.editLocal);
        lvComanda = activity.findViewById(R.id.lvComanda);
        configListView();
    }

    private void configListView(){
        try {
            adapterComanda = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    comandaDao.getDao().queryForAll()
            );
            lvComanda.setAdapter(adapterComanda);
            cliqueCurto();
            cliqueLongo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cliqueCurto(){
        lvComanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Comanda comanda = adapterComanda.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Dados da comanda");
                alerta.setMessage(comanda.toString());
                alerta.setNegativeButton("Não", null);
                alerta.setPositiveButton("Ver itens", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chamarTelaComanda(comanda);
                    }
                });
                alerta.show();
            }
        });
    }

    private void cliqueLongo(){
        lvComanda.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Comanda comanda = adapterComanda.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Excluindo comanda");
                alerta.setMessage(comanda.toString());
                alerta.setNegativeButton("Não", null);
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if(comandaDao.getDao().delete(comanda)>0){
                                adapterComanda.remove(comanda);
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

    public void novaComandaAction(){
        Comanda comanda = getDadosForm();
        try {
            comandaDao.getDao().create(comanda);
            chamarTelaComanda(comanda);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Comanda getDadosForm(){
        Comanda comanda = new Comanda();
        comanda.setMesa(Integer.parseInt(editMesa.getText().toString()));
        comanda.setLocal(editLocal.getText().toString());
        return comanda;
    }

    private void chamarTelaComanda(Comanda comanda){
        Intent it = new Intent(activity, ComandaActivity.class);
        it.putExtra("comanda", comanda);
        activity.startActivity(it);
    }
}
