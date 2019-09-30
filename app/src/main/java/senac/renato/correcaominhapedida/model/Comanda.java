package senac.renato.correcaominhapedida.model;

import android.content.ClipData;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "comanda")
public class Comanda implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private Integer mesa;

    @DatabaseField(canBeNull = false)
    private String local;

    @ForeignCollectionField(eager = true)
    private Collection<ItemComanda> itemComandas;

    public Comanda() {
    }

    public Comanda(Integer id, Integer mesa, String local, ItemComanda itemComanda) {
        this.id = id;
        this.mesa = mesa;
        this.local = local;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Collection<ItemComanda> getItemComandas() {
        return itemComandas;
    }

    public void setItemComandas(Collection<ItemComanda> itemComandas) {
        this.itemComandas = itemComandas;
    }

    @Override
    public String toString() {
        return "Comanda: " + id + " - mesa: " + mesa +
                " - local: " + local;
    }
}
