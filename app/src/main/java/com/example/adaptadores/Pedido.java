package com.example.adaptadores;

public class Pedido {

    private int imageResId; // ID del recurso de imagen
    private String nombre;
    private String descripcion;
    private int precio;

    // Constructor
    public Pedido(int imageResId, String nombre, String descripcion, int precio) {
        this.imageResId = imageResId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y setters
    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}


