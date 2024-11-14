package com.example.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Pedido> pedidos; // Lista de objetos Pedido
    private LayoutInflater inflater;

    // Constructor del adaptador
    public CustomAdapter(Context context, ArrayList<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pedidos.size(); // Tamaño de la lista de pedidos
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position); // Obtiene el pedido en la posición dada
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // Obtén las vistas del layout
        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemDescription = convertView.findViewById(R.id.item_description);
        TextView itemPrice = convertView.findViewById(R.id.item_price);
        Button btnEdit = convertView.findViewById(R.id.btn_edit_order);
        Button btnDelete = convertView.findViewById(R.id.btn_delete);

        // Obtener el pedido actual
        Pedido pedido = pedidos.get(position);

        // Configura los datos del pedido en las vistas
        imageView.setImageResource(pedido.getImageResId());
        itemName.setText(pedido.getNombre());
        itemDescription.setText(pedido.getDescripcion());
        itemPrice.setText("Precio: " + pedido.getPrecio() + " MXN");

        // Evento para editar el pedido
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarPedido(position);
            }
        });

        // Evento para eliminar el pedido
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPedido(position);
            }
        });

        return convertView;
    }

    // Método para eliminar un pedido
    private void eliminarPedido(int position) {
        pedidos.remove(position); // Eliminar de la lista
        notifyDataSetChanged(); // Notificar cambios al adaptador
    }

    // Método para editar un pedido
    private void editarPedido(int position) {
        Pedido pedido = pedidos.get(position);

        // Inflar el layout del diálogo
        View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
        EditText editName = dialogView.findViewById(R.id.edit_name);
        EditText editDescription = dialogView.findViewById(R.id.edit_description);
        EditText editPrice = dialogView.findViewById(R.id.edit_price);

        // Crear y mostrar el diálogo de edición
        new AlertDialog.Builder(context)
                .setTitle("Editar Pedido")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nuevoNombre = editName.getText().toString();
                    String nuevoPrecioStr = editPrice.getText().toString();

                    if (!nuevoNombre.isEmpty() && !nuevoPrecioStr.isEmpty()) {
                        try {
                            int nuevoPrecio = Integer.parseInt(nuevoPrecioStr);
                            pedido.setNombre(nuevoNombre);
                            pedido.setPrecio(nuevoPrecio);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Pedido editado", Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "Precio inválido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Método para añadir un nuevo pedido
    public void agregarPedido(Pedido nuevoPedido) {
        pedidos.add(nuevoPedido);
        notifyDataSetChanged(); // Actualizar el ListView
    }
}
