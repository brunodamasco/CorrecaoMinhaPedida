package senac.renato.correcaominhapedida.dao;

import android.content.Context;

import senac.renato.correcaominhapedida.dao.helper.DaoHelper;
import senac.renato.correcaominhapedida.model.Categoria;
import senac.renato.correcaominhapedida.model.Comanda;

public class ComandaDao extends DaoHelper<Comanda> {

    public ComandaDao(Context c) {
        super(c, Comanda.class);
    }

}
