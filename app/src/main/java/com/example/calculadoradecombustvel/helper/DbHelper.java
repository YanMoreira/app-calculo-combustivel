package com.example.calculadoradecombustvel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_ABASTECIMENTOS";
    public static String TABELA_ABASTECIMENTOS = "ABASTECIMENTOS";

    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

         String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_ABASTECIMENTOS +
                 " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 "combustivel VARCHAR(30) NOT NULL, " +
                 "valor FLOAT NOT NULL, " +
                 "data VARCHAR(10) NOT NULL); ";

         try {

             db.execSQL(sql);
         }

         catch(Exception e){

             Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
         }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
