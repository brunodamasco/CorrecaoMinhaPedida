package senac.renato.correcaominhapedida.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import senac.renato.correcaominhapedida.R;
import senac.renato.correcaominhapedida.control.CategoriaControl;

public class CategoriaActivity extends AppCompatActivity {
    private CategoriaControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        control = new CategoriaControl(this);
    }

    public void salvarCategoria(View view) {
    }
}
