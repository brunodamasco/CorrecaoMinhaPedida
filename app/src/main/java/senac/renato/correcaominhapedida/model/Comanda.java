package senac.renato.correcaominhapedida.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "comanda")
public class Comanda implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private Integer mesa;

    @DatabaseField(canBeNull = false)
    private String local;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private ItemComanda itemComanda;

    public Comanda() {
    }

    public Comanda(Integer id, Integer mesa, String local, ItemComanda itemComanda) {
        this.id = id;
        this.mesa = mesa;
        this.local = local;
        this.itemComanda = itemComanda;
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

    public ItemComanda getItemComanda() {
        return itemComanda;
    }

    public void setItemComanda(ItemComanda itemComanda) {
        this.itemComanda = itemComanda;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", mesa=" + mesa +
                ", local='" + local + '\'' +
                ", itemComanda=" + itemComanda +
                '}';
    }
}
