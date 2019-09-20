package senac.renato.correcaominhapedida.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.control.ComandaControl;

public class ComandaActivity extends AppCompatActivity {
    private ComandaControl control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);
        control = new ComandaControl(this);
    }

    public void AddItemComanda(View v){
        control.AddItemComandaAction();
    }

    public void limparLista(View v){
        control.limparListAction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        control.onActivityResult(requestCode, resultCode, data);
    }
}
