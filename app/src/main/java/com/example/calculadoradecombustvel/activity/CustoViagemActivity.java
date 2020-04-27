package com.example.calculadoradecombustvel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calculadoradecombustvel.R;
import com.google.android.material.textfield.TextInputEditText;

public class CustoViagemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editTextKmTotal, editTextGasolinaViagem, editTextEtanolViagem,
            editTextKmlGasolinaViagem, editTextKmlEtanolViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custo_viagem);

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

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }

    public void botaoCalcularViagem(View view) {

        String kmTotal = editTextKmTotal.getText().toString();
        String gasolinaViagem = editTextGasolinaViagem.getText().toString();
        String etanolViagem = editTextEtanolViagem.getText().toString();
        String gasolinaKmlViagem = editTextKmlGasolinaViagem.getText().toString();
        String etanolKmlViagem = editTextKmlEtanolViagem.getText().toString();


    }

    public String validarCampos(String kmTotal, String gasolinaViagem, String etanolViagem,
        String gasolinaKmlViagem, String etanolKmlViagem) {



        return "";
    }


}

