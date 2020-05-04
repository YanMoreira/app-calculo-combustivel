package com.example.calculadoradecombustvel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadoradecombustvel.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class CustoViagemActivity extends AppCompatActivity {

    private Switch switchGasolina, switchEtanol;
    private Toolbar toolbar;
    private TextView textConsumoGasolina, textPrecoGasolina, textConsumoEtanol, textPrecoEtanol,
            colunaGasolina, colunaEtanol, linhaConsumo, linhaPreco;
    private TextInputEditText editTextKmTotal, editTextGasolinaViagem, editTextEtanolViagem,
            editTextKmlGasolinaViagem, editTextKmlEtanolViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custo_viagem);

        colunaGasolina = findViewById(R.id.colunaGasolina);
        colunaEtanol = findViewById(R.id.colunaEtanol);
        linhaConsumo = findViewById(R.id.linhaConsumo);
        linhaPreco = findViewById(R.id.linhaPreco);

        textConsumoGasolina = findViewById(R.id.textConsumoGasolina);
        textPrecoGasolina = findViewById(R.id.textPrecoGasolina);
        textConsumoEtanol = findViewById(R.id.textConsumoEtanol);
        textPrecoEtanol = findViewById(R.id.textPrecoEtanol);

        switchGasolina = findViewById(R.id.switchGasolina);
        switchEtanol = findViewById(R.id.switchEtanol);

        editTextKmTotal = findViewById(R.id.editTextKmTotal);
        editTextGasolinaViagem = findViewById(R.id.editTextGasolinaViagem);
        editTextEtanolViagem = findViewById(R.id.editTextEtanolViagem);
        editTextKmlGasolinaViagem = findViewById(R.id.editTextKmlGasolinaViagem);
        editTextKmlEtanolViagem = findViewById(R.id.editTextKmlEtanolViagem);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Adicionando ação para o botão de voltar na barra de ferramentas

        toolbar.setNavigationIcon(R.drawable.ic_seta_voltar_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Deixando tabela de resultados invisível até o usuário fazer o cálculo

        colunaGasolina.setVisibility(View.INVISIBLE);
        colunaEtanol.setVisibility(View.INVISIBLE);
        linhaConsumo.setVisibility(View.INVISIBLE);
        linhaPreco.setVisibility(View.INVISIBLE);

        //Adicionando listener no switch gasolina para bloquear ou desbloquear campos de textos

        switchGasolina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    editTextGasolinaViagem.setEnabled(true);
                    editTextKmlGasolinaViagem.setEnabled(true);
                } else {

                    editTextGasolinaViagem.setEnabled(false);
                    editTextKmlGasolinaViagem.setEnabled(false);
                }
            }

            //Adicionando listener no switch etanol para bloquear ou desbloquear campos de textos
        });

        switchEtanol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    editTextEtanolViagem.setEnabled(true);
                    editTextKmlEtanolViagem.setEnabled(true);
                } else {

                    editTextEtanolViagem.setEnabled(false);
                    editTextKmlEtanolViagem.setEnabled(false);
                }

            }
        });
    }


    public void botaoCalcularViagem(View view) {

        String kmTotal = editTextKmTotal.getText().toString();
        String gasolinaViagem = editTextGasolinaViagem.getText().toString();
        String etanolViagem = editTextEtanolViagem.getText().toString();
        String gasolinaKmlViagem = editTextKmlGasolinaViagem.getText().toString();
        String etanolKmlViagem = editTextKmlEtanolViagem.getText().toString();

        //Estrutura de inicialização de validações e cálculos após opções escolhidas pelo usuário

        if (switchGasolina.isChecked() && switchEtanol.isChecked()) {

            String campoValidado = validarTodosCampos(kmTotal, gasolinaViagem, gasolinaKmlViagem, etanolViagem, etanolKmlViagem);

            if (campoValidado.equals("")) {

                calcularTodosCampos(kmTotal, gasolinaViagem, gasolinaKmlViagem, etanolViagem, etanolKmlViagem);

                fecharTeclado(view);
                visualizarTabela();

            }

            else {

                visualizarToastErro(campoValidado);
            }
        }

        else if (switchGasolina.isChecked()) {

            String campoValidado = validarCamposGasolina(kmTotal, gasolinaViagem, gasolinaKmlViagem);

            if (campoValidado.equals("")) {

                calcularCamposGasolina(kmTotal, gasolinaViagem, gasolinaKmlViagem);

                fecharTeclado(view);
                visualizarTabela();
            }

            else {

                visualizarToastErro(campoValidado);
            }

        }

        else if (switchEtanol.isChecked()) {

            String campoValidado = validarCamposEtanol(kmTotal, etanolViagem, etanolKmlViagem);

            if (campoValidado.equals("")) {

                calcularCamposEtanol(kmTotal, etanolViagem, etanolKmlViagem);

                fecharTeclado(view);


            }

            else {

                visualizarToastErro(campoValidado);
            }

        }

        else {


        }


    }

    //Metodos de validação dos campos disponíveis para o usuário

    public String validarCamposGasolina(String kmTotal, String gasolinaViagem, String gasolinaKmlViagem) {

        String campoNulo = "";


        if (kmTotal.equals("") || gasolinaViagem.equals("") || gasolinaKmlViagem.equals("")) {

            campoNulo = "Preencha os campos necessários";
        }

        return campoNulo;
    }


    public String validarCamposEtanol(String kmTotal, String etanolViagem, String etanolKmlViagem) {

        String campoNulo = "";


        if (kmTotal.equals("") || etanolViagem.equals("") || etanolKmlViagem.equals("")) {

            campoNulo = "Preencha os campos necessários";
        }

        return campoNulo;
    }


    public String validarTodosCampos(String kmTotal, String gasolinaViagem, String etanolViagem,
                                     String gasolinaKmlViagem, String etanolKmlViagem) {

        String campoNulo = "";

        if (kmTotal.equals("") || gasolinaViagem.equals("") || gasolinaKmlViagem.equals("") ||
                etanolViagem.equals("") || etanolKmlViagem.equals("")) {

            campoNulo = "Preencha os campos necessários";
        }

        return campoNulo;
    }

    //Metodos para cálculos dos campos habilitados

    public void calcularCamposGasolina(String kmTotal, String gasolinaViagem, String gasolinaKmlViagem) {

        String resultado = "";

        //Convertendo valores de String para double
        double valorKmTotal = Double.parseDouble(kmTotal);
        double valorGasolinaViagem = Double.parseDouble(gasolinaViagem);
        double valorGasolinaKmlViagem = Double.parseDouble(gasolinaKmlViagem);

        double tanqueCombustivel = valorKmTotal / valorGasolinaKmlViagem;
        double gastoCombustivel = tanqueCombustivel * valorGasolinaViagem;

        textConsumoGasolina.setText(String.valueOf(Math.round(tanqueCombustivel))+"L");
        textPrecoGasolina.setText("R$ "+ String.valueOf(String.format("%.2f",gastoCombustivel)));
        textConsumoEtanol.setText("-");
        textPrecoEtanol.setText("-");

        textConsumoGasolina.setTextColor(getResources().getColor(R.color.corEstilo));
        textPrecoGasolina.setTextColor(getResources().getColor(R.color.corEstilo));
        textConsumoEtanol.setTextColor(getResources().getColor(R.color.corTextoTabela));
        textPrecoEtanol.setTextColor(getResources().getColor(R.color.corTextoTabela));
    }

    public void calcularCamposEtanol(String kmTotal, String etanolViagem, String etanolKmlViagem) {

        String resultado = "";

        //Convertendo valores para double
        double valorKmTotal = Double.parseDouble(kmTotal);
        double valorEtanolViagem = Double.parseDouble(etanolViagem);
        double valorEtanolKmlViagem = Double.parseDouble(etanolKmlViagem);

        double tanqueCombustivel = valorKmTotal / valorEtanolKmlViagem;
        double gastoCombustivel = tanqueCombustivel * valorEtanolViagem;

        textConsumoGasolina.setText("-");
        textPrecoGasolina.setText("-");
        textConsumoEtanol.setText(String.valueOf(String.valueOf(Math.round(tanqueCombustivel)))+"L");
        textPrecoEtanol.setText("R$ " + String.valueOf(String.format("%.2f",gastoCombustivel)));

        textConsumoEtanol.setTextColor(getResources().getColor(R.color.corEstilo));
        textPrecoEtanol.setTextColor(getResources().getColor(R.color.corEstilo));
        textConsumoGasolina.setTextColor(getResources().getColor(R.color.corTextoTabela));
        textPrecoGasolina.setTextColor(getResources().getColor(R.color.corTextoTabela));
    }

    public void calcularTodosCampos(String kmTotal, String gasolinaViagem, String gasolinaKmlViagem,
                                      String etanolViagem, String etanolKmlViagem) {


        //Convertendo valores para double
        double valorKmTotal = Double.parseDouble(kmTotal);
        double valorGasolinaViagem = Double.parseDouble(gasolinaViagem);
        double valorGasolinaKmlViagem = Double.parseDouble(gasolinaKmlViagem);
        double valorEtanolViagem = Double.parseDouble(etanolViagem);
        double valorEtanolKmlViagem = Double.parseDouble(etanolKmlViagem);

        //Calculando valores dos campos gasolina
        double tanqueCombustivelGasolina = valorKmTotal / valorGasolinaKmlViagem;
        double gastoCombustivelGasolina = tanqueCombustivelGasolina * valorGasolinaViagem;

        //Calculando valores dos campos etanol
        double tanqueCombustivelEtanol = valorKmTotal / valorEtanolKmlViagem;
        double gastoCombustivelEtanol = tanqueCombustivelEtanol * valorEtanolViagem;

        textConsumoGasolina.setText(String.valueOf(Math.round(tanqueCombustivelGasolina))+"L");
        textPrecoGasolina.setText("R$ "+ String.valueOf(String.format("%.2f",gastoCombustivelGasolina)));
        textConsumoEtanol.setText(String.valueOf(String.valueOf(Math.round(tanqueCombustivelEtanol)))+"L");
        textPrecoEtanol.setText("R$ " + String.valueOf(String.format("%.2f",gastoCombustivelEtanol)));

        textConsumoGasolina.setTextColor(getResources().getColor(R.color.corEstilo));
        textConsumoEtanol.setTextColor(getResources().getColor(R.color.corEstilo));
        textPrecoEtanol.setTextColor(getResources().getColor(R.color.corEstilo));
        textPrecoGasolina.setTextColor(getResources().getColor(R.color.corEstilo));

    }


    public void fecharTeclado(View view) {

        if (view != null) {

            InputMethodManager input = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void visualizarTabela() {

        colunaGasolina.setVisibility(View.VISIBLE);
        colunaEtanol.setVisibility(View.VISIBLE);
        linhaConsumo.setVisibility(View.VISIBLE);
        linhaPreco.setVisibility(View.VISIBLE);

    }

    public void visualizarToastErro(String campoValidado) {

        Toast toast = Toast.makeText(this, campoValidado, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        TextView toastText = toastView.findViewById(android.R.id.message);
        //Alterando cor do texto exibido
        toastText.setTextColor(getResources().getColor(R.color.corTextToast));
        //alterando plano de fundo
        toastView.setBackgroundResource(R.drawable.plano_de_fundo_toast);
        toast.show();
    }


}