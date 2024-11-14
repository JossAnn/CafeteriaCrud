package com.example.adaptadores.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Database {

    //TABLA DE USUARIOS
    public static final String TABLE_USERS = "usuarios";
    public static final String ID_USER = "_id";
    public static final String NAME_USER = "nombre";
    public static final String MAIL_USER = "correo";
    public static final String PASS_USER = "contrasenia";
    public static final String IMAGE_USER = "img";
    //Esta es la sql para crearla
    public static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_USER + " TEXT, " +
                    MAIL_USER + " TEXT NOT NULL, " +
                    PASS_USER + " TEXT NOT NULL, " +
                    IMAGE_USER + " BLOB);";

    //tABLA DE PRODUCTOS
    public static final String TABLE_PRODUCTS = "productos";
    public static final String ID_PRODUCT = "_id";
    public static final String NAME_PRODUCT = "producto";
    public static final String DESCRIP_PRODUCT = "descripcion";
    public static final String PRECIO_PRODUCT = "precio";
    public static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_PRODUCT + " TEXT NOT NULL, " +
                    DESCRIP_PRODUCT + " TEXT, " +
                    PRECIO_PRODUCT + " REAL);";

    // TABLA DE PEDIDOS (ES LA TABLA PIVOTE)
    public static final String TABLE_ORDERS = "pedidos";
    public static final String ID_ORDER = "_id";
    public static final String USER_ID = "usuario_id"; // Llave foránea
    public static final String PRODUCT_ID = "producto_id"; // Llave foránea
    public static final String QUANTITY = "cantidad";
    public static final String CREATE_TABLE_ORDERS =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
                    ID_ORDER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_ID + " INTEGER, " +
                    PRODUCT_ID + " INTEGER, " +
                    QUANTITY + " INTEGER, " +
                    "FOREIGN KEY(" + USER_ID + ") REFERENCES " + TABLE_USERS + "(" + ID_USER + "), " +
                    "FOREIGN KEY(" + PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + ID_PRODUCT + "));";

    private SQLiteDatabase database;
    private final Conexion conexion;

    public Database(Context context) {
        conexion = new Conexion(context);
    }

    //Aqui es para que se pueda escribir sobre la DB
    public Database open() throws SQLException {
        database = conexion.getWritableDatabase();
        return this;
    }
    //Este es para cerrar
    public void close() {
        conexion.close();
    }

    //#############OPCIONES DE CRUD#######################################################################
    //USUARIOS-------------------------------------
    //Create usuario
    public long insertUser(String name, String email, String password, byte[] image) {
        ContentValues values = new ContentValues();
        values.put(NAME_USER, name);
        values.put(MAIL_USER, email);
        values.put(PASS_USER, password);
        values.put(IMAGE_USER, image);
        return database.insert(TABLE_USERS, null, values);
    }
    //Get usuario por correo
    public Cursor getUserByEmail(String email) {
        String[] columns = {ID_USER, NAME_USER, MAIL_USER, PASS_USER, IMAGE_USER};
        return database.query(TABLE_USERS, columns, MAIL_USER + "=?", new String[]{email}, null, null, null);
    }
    //Update Usuario
    public int updateUser(long userId, String name, String email, String password, byte[] image) {
        ContentValues values = new ContentValues();
        values.put(NAME_USER, name);
        values.put(MAIL_USER, email);
        values.put(PASS_USER, password);
        values.put(IMAGE_USER, image);
        return database.update(TABLE_USERS, values, ID_USER + "=?", new String[]{String.valueOf(userId)});
    }
    //Delete Usuario
    public int deleteUser(long userId) {
        return database.delete(TABLE_USERS, ID_USER + "=?", new String[]{String.valueOf(userId)});
    }

    //PRODUCTOS-------------------------------------
    //Create producto
    public long insertProduct(String name, String description, double price) {
        ContentValues values = new ContentValues();
        values.put(NAME_PRODUCT, name);
        values.put(DESCRIP_PRODUCT, description);
        values.put(PRECIO_PRODUCT, price);
        return database.insert(TABLE_PRODUCTS, null, values);
    }
    //Get productos (todos)
    public Cursor getAllProducts() {
        String[] columns = {ID_PRODUCT, NAME_PRODUCT, DESCRIP_PRODUCT, PRECIO_PRODUCT};
        return database.query(TABLE_PRODUCTS, columns, null, null, null, null, null);
    }
    //Update Producto
    public int updateProduct(long productId, String name, String description, double price) {
        ContentValues values = new ContentValues();
        values.put(NAME_PRODUCT, name);
        values.put(DESCRIP_PRODUCT, description);
        values.put(PRECIO_PRODUCT, price);
        return database.update(TABLE_PRODUCTS, values, ID_PRODUCT + "=?", new String[]{String.valueOf(productId)});
    }
    //Delete producto
    public int deleteProduct(long productId) {
        return database.delete(TABLE_PRODUCTS, ID_PRODUCT + "=?", new String[]{String.valueOf(productId)});
    }

    //PEDIDOS-------------------------------------
    //Create pedido
    public long insertOrder(int userId, int productId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, userId);
        values.put(PRODUCT_ID, productId);
        values.put(QUANTITY, quantity);
        return database.insert(TABLE_ORDERS, null, values);
    }

    //Get pedidos (todos)
    public Cursor getAllOrders() {
        String[] columns = {ID_ORDER, USER_ID, PRODUCT_ID, QUANTITY};
        return database.query(TABLE_ORDERS, columns, null, null, null, null, null);
    }
    //Update pedido
    public int updateOrder(long orderId, int userId, int productId, int quantity) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, userId);
        values.put(PRODUCT_ID, productId);
        values.put(QUANTITY, quantity);
        return database.update(TABLE_ORDERS, values, ID_ORDER + "=?", new String[]{String.valueOf(orderId)});
    }
    //Delete pedido
    public int deleteOrder(long orderId) {
        return database.delete(TABLE_ORDERS, ID_ORDER + "=?", new String[]{String.valueOf(orderId)});
    }
}
