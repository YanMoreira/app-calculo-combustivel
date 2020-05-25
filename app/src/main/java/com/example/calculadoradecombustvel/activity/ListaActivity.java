package com.example.calculadoradecombustvel.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import com.example.calculadoradecombustvel.R;
import com.example.calculadoradecombustvel.adapter.ListaAdapter;
import com.example.calculadoradecombustvel.helper.AbastecimentoDAO;
import com.example.calculadoradecombustvel.helper.DbHelper;
import com.example.calculadoradecombustvel.helper.RecyclerItemClickListener;
import com.example.calculadoradecombustvel.model.Lista;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerLista;
    private ListaAdapter listaAdapter;
    private List<Lista> listaAbastecimentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Adicionando ação para o botão de voltar na barra de ferramentas
        toolbar.setNavigationIcon(R.drawable.ic_seta_voltar_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        //Configurar recycler
        recyclerLista = findViewById(R.id.recyclerLista);


        //Adicionar evento de clique
        recyclerLista.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerLista,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(getApplicationContext(), AdicionarAbastecimentoActivity.class));
            }
        });
    }

    public void carregarLista() {

        //Listar abastecimentos
        AbastecimentoDAO abastecimentoDAO = new AbastecimentoDAO(getApplicationContext());
        listaAbastecimentos = abastecimentoDAO.listar();

        //Configurar Adapter
        listaAdapter = new ListaAdapter(listaAbastecimentos);

        //Configurar recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerLista.setLayoutManager(layoutManager);
        recyclerLista.setHasFixedSize(true);
        recyclerLista.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerLista.setAdapter(listaAdapter);
    }

    @Override
    protected void onStart() {

        carregarLista();
        super.onStart();
    }
}
