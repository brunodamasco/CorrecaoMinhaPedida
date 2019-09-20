package senac.renato.correcaominhapedida.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import senac.renato.correcaominhapedida.model.Categoria;
import senac.renato.correcaominhapedida.model.Comanda;
import senac.renato.correcaominhapedida.model.ItemComanda;
import senac.renato.correcaominhapedida.model.Produto;

public class MyOrmLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "minha_pedida.db";
    private static final int DATABASE_VERSION = 18;

    public MyOrmLiteHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Categoria.class);
            TableUtils.createTable(connectionSource, Comanda.class);
            TableUtils.createTable(connectionSource, ItemComanda.class);
            TableUtils.createTable(connectionSource, Produto.class);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Categoria.class, true);
            TableUtils.dropTable(connectionSource, Comanda.class, true);
            TableUtils.dropTable(connectionSource, ItemComanda.class, true);
            TableUtils.dropTable(connectionSource, Produto.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
