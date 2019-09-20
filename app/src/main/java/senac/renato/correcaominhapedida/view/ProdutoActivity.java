package senac.renato.correcaominhapedida.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.control.ProdutoControl;

public class ProdutoActivity extends AppCompatActivity {
    private ProdutoControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        control = new ProdutoControl(this);
    }

    public void addProduto(View view) {
        control.enviarItemAction();
    }

    public void addNovaCategoria(View view) {
        control.addNovaCategoria();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        control.onActivityResult(requestCode, resultCode, data);
    }
}
