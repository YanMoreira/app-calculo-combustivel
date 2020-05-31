package com.example.calculadoradecombustvel.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerLista;
    private ListaAdapter listaAdapter;
    private List<Lista> listaAbastecimentos = new ArrayList<>();
    private Lista objSelecionado;

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
                                //Recuperar abastecimento selecionado
                                Lista objSelecionado = listaAbastecimentos.get(position);

                                //Enviando os dados para a tela de adicionar abastecimento
                                Intent intent = new Intent(getApplicationContext(), AdicionarAbastecimentoActivity.class);
                                intent.putExtra("abastecimentoSelecionado", objSelecionado);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                objSelecionado = listaAbastecimentos.get(position);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ListaActivity.this);

                                //Configurar título e mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir o abastecimento?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        AbastecimentoDAO abastecimentoDAO = new AbastecimentoDAO(getApplicationContext());
                                        abastecimentoDAO.deletar(objSelecionado);
                                        carregarLista();
                                        
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                dialog.create();
                                dialog.show();

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
