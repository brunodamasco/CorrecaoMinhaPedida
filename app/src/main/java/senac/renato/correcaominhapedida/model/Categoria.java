package senac.renato.correcaominhapedida.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "categorias")
public class Categoria implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "nome", canBeNull = false, width = 60)
    private String nomeCategoria;

    @ForeignCollectionField(eager = true)
    private Collection<Produto> colecaoProdutos;

    public Categoria() {
    }

    public Categoria(Integer id, String nomeCategoria) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
    }

    public Categoria(Integer id, String nomeCategoria, Collection<Produto> colecaoProdutos) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.colecaoProdutos = colecaoProdutos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Collection<Produto> getColecaoProdutos() {
        return colecaoProdutos;
    }

    public void setColecaoProdutos(Collection<Produto> colecaoProdutos) {
        this.colecaoProdutos = colecaoProdutos;
    }


    @Override
    public String toString() {
        return id + "- " + nomeCategoria;
    }
}
