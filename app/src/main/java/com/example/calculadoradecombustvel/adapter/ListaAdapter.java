package com.example.calculadoradecombustvel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadoradecombustvel.R;
import com.example.calculadoradecombustvel.model.Lista;

import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.MyViewHolder> {

    private List<Lista> listaAbastecimentos;
    public ListaAdapter(List<Lista> listaAbastecimentos) {

        this.listaAbastecimentos = listaAbastecimentos;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_adapter, parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Lista lista = listaAbastecimentos.get(position);
        holder.combustivel.setText("Combustível: " + lista.getNomeCombutivel());
        holder.preco.setText("Preço: R$ " + lista.getValorCombustivel());
        holder.data.setText("Data: " + lista.getDataCombustivel());
    }

    @Override
    public int getItemCount() {
        return listaAbastecimentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView combustivel;
        TextView preco;
        TextView data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        combustivel = itemView.findViewById(R.id.viewCombustivel);
        preco = itemView.findViewById(R.id.viewPreco);
        data = itemView.findViewById(R.id.viewData);

        }
    }
}
