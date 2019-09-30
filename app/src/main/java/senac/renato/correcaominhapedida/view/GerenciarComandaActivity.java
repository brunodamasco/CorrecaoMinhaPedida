package senac.renato.correcaominhapedida.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.control.GerenciarComandaControl;

public class GerenciarComandaActivity extends AppCompatActivity {
    private GerenciarComandaControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_comanda);
        control = new GerenciarComandaControl(this);
    }

    public void novaComanda(View v){
        control.novaComandaAction();
    }
}
