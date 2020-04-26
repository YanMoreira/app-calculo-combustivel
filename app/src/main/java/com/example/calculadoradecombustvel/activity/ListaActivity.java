package com.example.calculadoradecombustvel.activity;

import android.os.Bundle;

import com.example.calculadoradecombustvel.R;
import com.example.calculadoradecombustvel.adapter.ListaAdapter;
import com.example.calculadoradecombustvel.model.Lista;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
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

        //Configurar recycler
        recyclerLista = findViewById(R.id.recyclerLista);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void carregarLista() {

        //Listar abastecimentos
        Lista lista1 = new Lista();
        lista1.setNomeCombutivel("Gasolina");
        lista1.setValorCombustivel("100");
        lista1.setDataCombustivel("19/10/2020");
        listaAbastecimentos.add(lista1);

        Lista lista2 = new Lista();
        lista2.setNomeCombutivel("Etanol");
        lista2.setValorCombustivel("200");
        lista2.setDataCombustivel("19/10/2020");
        listaAbastecimentos.add(lista2);

        Lista lista3 = new Lista();
        lista3.setNomeCombutivel("Gasolina");
        lista3.setValorCombustivel("1600,00");
        lista3.setDataCombustivel("19/10/2020");
        listaAbastecimentos.add(lista3);

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
