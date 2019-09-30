package senac.renato.correcaominhapedida.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.control.AddItemComandaControl;

public class AddItemComandaActivity extends AppCompatActivity {
    private AddItemComandaControl itemComandaControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_comanda);
        itemComandaControl = new AddItemComandaControl(this);
    }

    public void adicionarItem(View v){
        itemComandaControl.enviarItemAction();
    }

    public void novoProduto(View view) {
        itemComandaControl.addNovoProduto();
    }

    public void cancelar(View v){
        itemComandaControl.cancelarAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemComandaControl.configSpinner();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        itemComandaControl.onActivityResult(requestCode, resultCode, data);
    }
}
