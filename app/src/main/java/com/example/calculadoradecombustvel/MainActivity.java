package com.example.calculadoradecombustvel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton localizarPosto, consumoViagem, visualizarLista;
    private Button botaoCalc;
    private TextInputEditText editTextGasolina, editTextEtanol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextGasolina = findViewById(R.id.editTextGasolina);
        editTextEtanol = findViewById(R.id.editTextEtanol);

        localizarPosto = findViewById(R.id.localizarPosto);
        consumoViagem = findViewById(R.id.consumoViagem);
        visualizarLista = findViewById(R.id.visualizarLista);

        // Listener dos menus do fab
        localizarPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        consumoViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        visualizarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void botaoCalcular(View view) {

        String valorGasolina = editTextGasolina.getText().toString();
        String valorEtanol = editTextEtanol.getText().toString();

        String erroValidacao = validarCampos(valorGasolina, valorEtanol);

        if (erroValidacao.equals("")) {

            String resultado = calcularResultadoValidado(valorGasolina, valorEtanol);

            //Esconder teclado

            if(view != null) {

                InputMethodManager input = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            //Gerar notificação do resultado
            Snackbar.make(view, resultado, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.corEstilo))
                    .setTextColor(getResources().getColor(R.color.corBase))
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        } else {

            Toast.makeText(getApplicationContext(), erroValidacao, Toast.LENGTH_LONG).show();
        }
    }

    public String validarCampos(String vGasolina, String vEtanol) {

        String campoNulo = "";

        if (vGasolina.equals("")) {

            campoNulo = "Por favor, digite o preço da gasolina";
        }

        if (vEtanol.equals("")) {

            campoNulo = "Por favor, digite o preço do etanol";

        }

        if (vGasolina.equals("") && vEtanol.equals("")) {

            campoNulo = "Digite os preços dos combustíveis";

        }

        return campoNulo;
    }

    public String calcularResultadoValidado(String valorGasolina, String valorEtanol) {

        double valorConvertGasolina = Double.parseDouble(valorGasolina);
        double valorConvertEtanol = Double.parseDouble(valorEtanol);

        double resultadoDivisao = valorConvertEtanol / valorConvertGasolina;

        if (resultadoDivisao >= 0.7) {


            return "Utilize gasolina para maior economia!";

        } else {

            return "utilize etanol para maior economia!";
        }

    }
}
