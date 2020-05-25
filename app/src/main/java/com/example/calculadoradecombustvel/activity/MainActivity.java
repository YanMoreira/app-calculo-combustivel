package com.example.calculadoradecombustvel.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculadoradecombustvel.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton localizarPosto, consumoViagem, visualizarLista;
    private Button botaoCalc;
    private TextInputEditText editTextGasolina, editTextEtanol, editTextKmlGasolina, editTextKmlEtanol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextGasolina = findViewById(R.id.editTextGasolina);
        editTextEtanol = findViewById(R.id.editTextEtanol);
        editTextKmlGasolina = findViewById(R.id.editTextKmlGasolina);
        editTextKmlEtanol = findViewById(R.id.EditTextKmlEtanol);

        localizarPosto = findViewById(R.id.localizarPosto);
        consumoViagem = findViewById(R.id.consumoViagem);
        visualizarLista = findViewById(R.id.visualizarLista);

        // Listener dos menus do fab
        localizarPosto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postoMaisProximo();
            }
        });

        consumoViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),CustoViagemActivity.class));
            }
        });

        visualizarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ListaActivity.class));

            }
        });

    }

    public void botaoCalcular(View view) {

        String valorGasolina = editTextGasolina.getText().toString();
        String valorEtanol = editTextEtanol.getText().toString();
        String valorKmlGasolina = editTextKmlGasolina.getText().toString();
        String valorKmlEtanol = editTextKmlEtanol.getText().toString();

        String erroValidacao = validarCampos(valorGasolina, valorEtanol, valorKmlGasolina, valorKmlEtanol);

        if (erroValidacao.equals("")) {

            String resultado = calcularResultadoValidado(valorGasolina, valorEtanol, valorKmlGasolina, valorKmlEtanol);

            //fechar teclado para melhor visualização
            fecharTeclado(view);

            //Gerar pop-up do resultado
                final Snackbar snackbar = Snackbar.make(view, resultado, Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(getResources().getColor(R.color.corEstilo))
                    .setTextColor(getResources().getColor(R.color.corBase))
                    .setDuration(10000);

            View snackbarView = snackbar.getView();

            TextView textView = snackbarView.findViewById(R.id.snackbar_text);

            //Modificações no texto do snackBar
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbarView.setPadding(0, 20, 0, 20);
            snackbar.show();

        } else {

            Toast toast = Toast.makeText(this, erroValidacao, Toast.LENGTH_LONG);
            View toastView = toast.getView();
            TextView toastText = toastView.findViewById(android.R.id.message);
            //Alterando cor do texto exibido
            toastText.setTextColor(getResources().getColor(R.color.corTextToast));
            //alterando plano de fundo
            toastView.setBackgroundResource(R.drawable.plano_de_fundo_toast);
            toast.show();
        }
    }

    public String validarCampos(String vGasolina, String vEtanol, String vKmlGasolina, String vKmlEtanol) {

        String campoNulo = "";

        if (vGasolina.equals("")) {

            campoNulo = "Por favor, digite o preço da gasolina";
        }

        if (vEtanol.equals("")) {

            campoNulo = "Por favor, digite o preço do etanol";

        }

        if (vKmlGasolina.equals("")) {

            campoNulo = "Por favor, digite o km/l da gasolina";
        }

        if (vKmlEtanol.equals("")) {

            campoNulo = "Por favor, digite o km/l do etanol";
        }


        if (vGasolina.equals("") && vEtanol.equals("") && vKmlGasolina.equals("") && vKmlEtanol.equals("")) {

            campoNulo = "Digite os campos corretamente";

        }

        return campoNulo;
    }

    public String calcularResultadoValidado(String valorGasolina, String valorEtanol,
                                            String valorKmlGasolina, String valorKmlEtanol) {

        //Convertendo valores para iniciar os cálculos

        double valorConvertGasolina = Double.parseDouble(valorGasolina);
        double valorConvertEtanol = Double.parseDouble(valorEtanol);
        double valorConvertKmlGasolina = Double.parseDouble(valorKmlGasolina);
        double valorConvertKmlEtanol = Double.parseDouble(valorKmlEtanol);


        double resultadoRendimentoCarro = valorConvertKmlEtanol / valorConvertKmlGasolina;

        double resultadoValorCombustivel = valorConvertEtanol / valorConvertGasolina;

        if (resultadoValorCombustivel <= resultadoRendimentoCarro) {


            return "Utilize etanol para maior economia!";

        } else {

            return "utilize gasolina para maior economia!";
        }

    }


    public void fecharTeclado(View view) {

        if (view != null) {

            InputMethodManager input = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void postoMaisProximo() {

        String endereco = "https://www.google.com/maps/dir/?api=1&destination=posto+de+combustivel&travelmode=driving";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(endereco));
        startActivity(intent);
    }
}