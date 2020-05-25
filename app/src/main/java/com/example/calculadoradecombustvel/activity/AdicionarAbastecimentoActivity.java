package com.example.calculadoradecombustvel.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadoradecombustvel.R;
import com.example.calculadoradecombustvel.adapter.ListaAdapter;
import com.example.calculadoradecombustvel.helper.AbastecimentoDAO;
import com.example.calculadoradecombustvel.model.Lista;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AdicionarAbastecimentoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;
    private TextView textAddGasolina, textAddEtanol, textData;
    private TextInputEditText editAddPreco;
    private String checkGasolina = "", checkEtanol = "", data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_abastecimento);

        textData = findViewById(R.id.textData);
        editAddPreco = findViewById(R.id.editAddPreco);
        textAddGasolina = findViewById(R.id.textAddGasolina);
        textAddEtanol = findViewById(R.id.textAddEtanol);

        toolbar = findViewById(R.id.toolbarLista);
        setSupportActionBar(toolbar);

        //Adicionando ação para o botão de voltar na barra de ferramentas
        toolbar.setNavigationIcon(R.drawable.ic_seta_voltar_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ListaActivity.class));
            }
        });

        //Verificando qual a opção que o usuário escolheu

        textAddGasolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkGasolina = "1";
                textAddGasolina.setBackgroundTintList(ColorStateList.valueOf(getResources()
                        .getColor(R.color.corEstilo)));

                if (checkEtanol.equals("1")) {

                    checkEtanol = "0";
                    textAddEtanol.setBackgroundTintList(ColorStateList.valueOf(getResources()
                            .getColor(R.color.corBotaoAddAbastecimento)));
                }
            }
        });

        textAddEtanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkEtanol = "1";
                textAddEtanol.setBackgroundTintList(ColorStateList.valueOf(getResources()
                        .getColor(R.color.corEstilo)));

                if (checkGasolina.equals("1")) {

                    checkGasolina = "0";
                    textAddGasolina.setBackgroundTintList(ColorStateList.valueOf(getResources()
                            .getColor(R.color.corBotaoAddAbastecimento)));
                }

            }
        });

        textData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                visualizarData();
            }
        });
    }

    //Criar o ícone na barra de ações com a funcionalidade de salvar dados do abastecimento

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add_abastecimento, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemSalvar:

                //Limitando valor em duas casas após a vírgula e convertendo para double
                double editAddPrecoConvert = Double.parseDouble(editAddPreco.getText().toString());

                //Validando se todos os dados foram digitados
                String dadosValidados = validarDados(checkGasolina, checkEtanol, editAddPreco.getText().toString(), data);

                if(dadosValidados.equals("")) {

                    //Realizando inserção no banco de dados
                    AbastecimentoDAO abastecimentoDAO = new AbastecimentoDAO(getApplicationContext());
                    Lista lista = new Lista();

                    if(checkGasolina.equals("1")) {

                        lista.setNomeCombutivel("Gasolina");
                    }

                    else {

                        lista.setNomeCombutivel("Etanol");
                    }

                    lista.setValorCombustivel(editAddPrecoConvert);
                    lista.setDataCombustivel(data);
                    abastecimentoDAO.salvar(lista);
                }

                else {

                    visualizarToastErro(dadosValidados);
                }

                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //Métdodo para visualizar o pop-up da data

    public void visualizarData() {

        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );

        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int diaDoMes) {

        mes += 1; //Correção na variável mês, pois, por padrão, a índice se inicia do 0

        if(diaDoMes > 0 && diaDoMes < 10) {

            data = "0";
        }

        if (mes > 0 && mes < 10) {

            data += diaDoMes + "/" + "0" + mes + "/" + ano;
        }

        else {

            data += diaDoMes + "/" + mes + "/" + ano;
        }

        textData.setText(data);
    }

    //Método para validar dados do usuário

    public String validarDados(String checkGasolina, String checkEtanol, String editAddPreco, String data) {

        String validarCampos = "";

        if(checkGasolina.equals("") && checkEtanol.equals("")) {

            validarCampos = "Selecione o combustível adquirido";
        }

        else if (editAddPreco.equals("")) {

            validarCampos = "Digite o valor do abastecimento";
        }

        else if(data.equals("")) {

            validarCampos = "Selecione o dia do abastecimento";
        }

        return validarCampos;
    }

    public void visualizarToastErro(String dadosValidados) {

        Toast toast = Toast.makeText(this, dadosValidados, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        TextView toastText = toastView.findViewById(android.R.id.message);
        //Alterando cor do texto exibido
        toastText.setTextColor(getResources().getColor(R.color.corTextToast));
        //alterando plano de fundo
        toastView.setBackgroundResource(R.drawable.plano_de_fundo_toast);
        toast.show();
    }
}
