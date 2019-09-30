package senac.renato.correcaominhapedida.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.dao.CategoriaDao;
import senac.renato.correcaominhapedida.model.Categoria;

public class CategoriaControl {
    private Activity activity;
    private ListView lvCategoria;
    private EditText editNome;

    private List<Categoria> listCategoria;
    private ArrayAdapter<Categoria> adapterCategoria;
    private Categoria categoria;
    private CategoriaDao categoriaDao;

    public CategoriaControl(Activity activity) {
        this.activity = activity;
        categoriaDao = new CategoriaDao(activity);
        initComponents();
    }

    private void initComponents() {
        editNome = activity.findViewById(R.id.edNome);
        lvCategoria = activity.findViewById(R.id.lvCategoria);
        confifListViewCategoria();
    }

    public void salvarAction(){
        if(categoria==null){
            categoria = getDadosForm();
        } else {
            Categoria c = getDadosForm();
            categoria.setNomeCategoria(c.getNomeCategoria());
        }

        try {
            Dao.CreateOrUpdateStatus res = categoriaDao.getDao().createOrUpdate(categoria);
            if(res.isCreated()){
                adapterCategoria.add(categoria);
            }else if(res.isUpdated()){
                adapterCategoria.notifyDataSetChanged();
            }
            categoria = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Categoria getDadosForm() {
        Categoria c = new Categoria();
        c.setNomeCategoria((editNome.getText().toString()));
        return c;
    }

    private void carregarForm(Categoria c){
        editNome.setText(c.getNomeCategoria());
    }

    private void confifListViewCategoria() {
        try {
            listCategoria = categoriaDao.getDao().queryForAll();
            adapterCategoria = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    listCategoria
            );
            lvCategoria.setAdapter(adapterCategoria);
            cliqueCurto();
            cliqueLongo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void cliqueCurto() {
        lvCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoria = adapterCategoria.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Visualizando categoria");
                alerta.setMessage(categoria.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        categoria = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        carregarForm(categoria);
                    }
                });
                alerta.show();
            }
        });
    }

    private void cliqueLongo() {
        lvCategoria.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                categoria = adapterCategoria.getItem(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Excluindo categoria");
                alerta.setMessage(categoria.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        categoria = null;
                    }
                });
                alerta.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if(categoriaDao.getDao().delete(categoria)>0){
                                adapterCategoria.remove(categoria);
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

}
