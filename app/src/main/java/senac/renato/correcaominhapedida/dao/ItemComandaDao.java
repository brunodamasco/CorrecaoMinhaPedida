package senac.renato.correcaominhapedida.dao;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import senac.renato.correcaominhapedida.dao.helper.DaoHelper;
import senac.renato.correcaominhapedida.model.ItemComanda;

public class ItemComandaDao extends DaoHelper<ItemComanda> {
    public ItemComandaDao(Context c){
        super(c, ItemComanda.class);
    }

    public List<ItemComanda> getItemAtivo(){
        try {
            return this.getDao().queryBuilder().where()
                    .eq("ativo", 1)
                    .query();
        } catch (SQLException e){
            return null;
        }
    }

}
