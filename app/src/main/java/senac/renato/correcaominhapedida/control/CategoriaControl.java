package senac.renato.correcaominhapedida.control;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
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

    private Categoria getDadosForm() {
        Categoria c = new Categoria();
        c.setNomeCategoria((editNome.getText().toString()));
        return c;
    }

    private void confifListViewCategoria() {
        listCategoria = new ArrayList<>();
        adapterCategoria = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_list_item_1,
                listCategoria
        );
        lvCategoria.setAdapter(adapterCategoria);
        cliqueCurto();
        cliqueLongo();
    }

    private void cliqueCurto() {
    }

    private void cliqueLongo() {
    }

}
