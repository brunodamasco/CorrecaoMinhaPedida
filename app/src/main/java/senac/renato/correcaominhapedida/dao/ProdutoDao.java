package senac.renato.correcaominhapedida.dao;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import senac.renato.correcaominhapedida.dao.helper.DaoHelper;
import senac.renato.correcaominhapedida.model.Produto;

public class ProdutoDao extends DaoHelper<Produto> {
    public ProdutoDao(Context c){
        super(c, Produto.class);
    }

    public List<Produto> getEstadoAtivo(){
        try {
            return this.getDao().queryBuilder().where()
                    .eq("ativo", 1)
                    .query();
        } catch (SQLException e){
            return null;
        }
    }
}
