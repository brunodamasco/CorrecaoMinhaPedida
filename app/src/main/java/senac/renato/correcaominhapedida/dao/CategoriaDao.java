package senac.renato.correcaominhapedida.dao;

import android.content.Context;

import senac.renato.correcaominhapedida.dao.helper.DaoHelper;
import senac.renato.correcaominhapedida.model.Categoria;

public class CategoriaDao extends DaoHelper<Categoria> {

    public CategoriaDao(Context c) {
        super(c, Categoria.class);
    }

}
