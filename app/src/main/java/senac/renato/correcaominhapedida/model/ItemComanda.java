package senac.renato.correcaominhapedida.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "itemcomanda")
public class ItemComanda implements Serializable {
    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Produto produto;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Comanda comanda;

    @DatabaseField(canBeNull = false)
    private int quantidade;

    public ItemComanda() {
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public ItemComanda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemComanda(Integer id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setQuantidade(String quantidade) {
        try {
            this.quantidade = Integer.parseInt(quantidade);
        } catch (Exception e){
            this.quantidade = 0;
        }
    }

    public void addQuantidade(){
        this.quantidade++;
    }

    public void removeQuantidade(){
        this.quantidade--;
    }

    public Double getSubtotal(){
        return produto.getValor() * quantidade;
    }

    @Override
    public String toString() {
        return "Qtd. " + quantidade + " - " + produto.getNome() + " - R$ "
                + produto.getValor() + " = SubTotal R$ " + getSubtotal();
    }
}
