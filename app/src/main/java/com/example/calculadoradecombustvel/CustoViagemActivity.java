package com.example.calculadoradecombustvel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CustoViagemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custo_viagem);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Desabilitando o título embutido da barra de farramentas
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Adicionando ação para o botão de voltar na barra de ferramentas
        toolbar.setNavigationIcon(R.drawable.ic_seta_voltar_custo_viagem);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        }

}

