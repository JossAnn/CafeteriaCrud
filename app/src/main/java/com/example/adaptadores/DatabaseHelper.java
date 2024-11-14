package com.example.adaptadores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "miBaseDatos.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla Categorias
        db.execSQL("CREATE TABLE Categorias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL)");

        // Crear tabla Productos con relación hacia Categorias
        db.execSQL("CREATE TABLE Productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "precio REAL, " +
                "categoriaId INTEGER, " +
                "FOREIGN KEY (categoriaId) REFERENCES Categorias(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL("DROP TABLE IF EXISTS Categorias");
        onCreate(db);
    }

    // Operaciones CRUD
    public long insertarCategoria(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        return db.insert("Categorias", null, values);
    }

    public long insertarProducto(String nombre, double precio, int categoriaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("precio", precio);
        values.put("categoriaId", categoriaId);
        return db.insert("Productos", null, values);
    }

    public int eliminarProducto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Productos", "id=?", new String[]{String.valueOf(id)});
    }

    public int actualizarProducto(int id, String nombre, double precio, int categoriaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("precio", precio);
        values.put("categoriaId", categoriaId);
        return db.update("Productos", values, "id=?", new String[]{String.valueOf(id)});
    }

    public Cursor obtenerProductos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Productos.id, Productos.nombre, Productos.precio, Categorias.nombre AS categoria " +
                "FROM Productos INNER JOIN Categorias ON Productos.categoriaId = Categorias.id", null);
    }
}
