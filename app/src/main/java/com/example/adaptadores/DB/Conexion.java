package com.example.adaptadores.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {

    private static final String NAME_DB = "DatabaseCafeteria";
    private static final int VERSION_DB = 1;

    public Conexion(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecutar las sentencias SQL para crear las tablas
        db.execSQL(Database.CREATE_TABLE_USERS);
        db.execSQL(Database.CREATE_TABLE_PRODUCTS);
        db.execSQL(Database.CREATE_TABLE_ORDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas si existen y recrearlas
        db.execSQL("DROP TABLE IF EXISTS " + Database.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + Database.TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + Database.TABLE_ORDERS);
        onCreate(db);
    }
}
