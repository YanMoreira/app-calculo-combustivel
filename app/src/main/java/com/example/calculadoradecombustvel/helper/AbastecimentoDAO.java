package com.example.calculadoradecombustvel.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.calculadoradecombustvel.model.Lista;

import java.util.ArrayList;
import java.util.List;

public class AbastecimentoDAO implements IAbastecimentoDAO {

    private SQLiteDatabase leitura, escrita;

    public AbastecimentoDAO(Context context) {

        DbHelper db = new DbHelper(context);
        leitura = db.getReadableDatabase();
        escrita = db.getWritableDatabase();
    }

    @Override
    public boolean salvar(Lista lista) {

        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put("combustivel", lista.getNomeCombutivel());
            contentValues.put("valor", lista.getValorCombustivel());
            contentValues.put("data", lista.getDataCombustivel());

            escrita.insert(DbHelper.TABELA_ABASTECIMENTOS, null, contentValues);
        }

        catch (Exception e) {

            Log.e("INFO", "Erro ao salvar detalhes do abastecimento " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Lista lista) {

        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put("combustivel", lista.getNomeCombutivel());
            contentValues.put("valor", lista.getValorCombustivel());
            contentValues.put("data", lista.getDataCombustivel());

            String[] args = {lista.getId().toString()};
            escrita.update(DbHelper.TABELA_ABASTECIMENTOS, contentValues, "id = ?", args);
        }
        catch (Exception e) {

        }
        return true;
    }

    @Override
    public boolean deletar(Lista lista) {

        try {

            String args[] = {lista.getId().toString()};
            escrita.delete(DbHelper.TABELA_ABASTECIMENTOS, "id = ?", args);
        }

        catch (Exception e) {

        }
        return true;
    }

    @Override
    public List<Lista> listar() {

        List<Lista> abastecimentos = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_ABASTECIMENTOS;
        Cursor cursor = leitura.rawQuery(sql, null);

        while(cursor.moveToNext()) {

            Lista lista = new Lista();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String combustivel = cursor.getString(cursor.getColumnIndex("combustivel"));
            double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
            String data = cursor.getString(cursor.getColumnIndex("data"));


            lista.setId(id);
            lista.setNomeCombutivel(combustivel);
            lista.setValorCombustivel(valor);
            lista.setDataCombustivel(data);

            abastecimentos.add(lista);
        }

        return abastecimentos;
    }
}
