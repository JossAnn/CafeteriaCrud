package com.example.adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<Pedido> listaPedidos; // Lista de pedidos
    private Button btnAdd, btnDelete, btnEdit; // Añadido btnEdit
    private String usuarioActual; // Variable para el nombre de usuario actual
    private TextView userNameTextView; // Declarar el TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view); // Layout activity_list_view.xml

        // Inicializar el ListView y los botones
        listView = findViewById(R.id.order_list_view);
        btnAdd = findViewById(R.id.btn_add_order);
        btnDelete = findViewById(R.id.btn_delete_order);
        btnEdit = findViewById(R.id.btn_edit_order); // Inicializar btnEdit
        userNameTextView = findViewById(R.id.user_name_text_view); // Asegúrate de que tengas este TextView en tu layout

        // Obtener el nombre de usuario de los extras
        usuarioActual = getIntent().getStringExtra("USER_NAME"); // Recibe el nombre de usuario

        // Mostrar el nombre del usuario en el TextView
        userNameTextView.setText("Pedidos de " + usuarioActual); // Actualiza el TextView con el nombre del usuario

        // Inicializar la lista de pedidos según el usuario
        listaPedidos = obtenerPedidosDeUsuario(usuarioActual);  // Método para obtener los pedidos según el usuario

        // Inicializar el adaptador personalizado
        customAdapter = new CustomAdapter(this, listaPedidos);
        listView.setAdapter(customAdapter);

        // Agregar pedido nuevo
        btnAdd.setOnClickListener(v -> mostrarDialogoAgregarPedido());

        // Editar pedido seleccionado
        btnEdit.setOnClickListener(v -> {
            int position = listView.getCheckedItemPosition();
            if (position != ListView.INVALID_POSITION) {
                mostrarDialogoEditarPedido(position);
            } else {
                Toast.makeText(ListViewActivity.this, "Selecciona un pedido para editar", Toast.LENGTH_SHORT).show();
            }
        });

        // Eliminar todos los pedidos
        btnDelete.setOnClickListener(v -> {
            if (!listaPedidos.isEmpty()) {
                listaPedidos.clear();  // Limpia toda la lista de pedidos
                customAdapter.notifyDataSetChanged();  // Actualizar el ListView
                Toast.makeText(ListViewActivity.this, "Todos los pedidos han sido eliminados", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ListViewActivity.this, "No hay pedidos para eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el clic en cada elemento del ListView
        listView.setOnItemClickListener((parent, view, position, id) -> mostrarDialogoEditarPedido(position));
    }

    // Método para mostrar el diálogo para agregar un nuevo pedido
    private void mostrarDialogoAgregarPedido() {
        String[] opciones = {"Café Mocha", "Café Americano", "Té Verde", "Chocolate Caliente", "Capuchino", "Espresso"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un pedido")
                .setItems(opciones, (dialog, which) -> {
                    String pedidoSeleccionado = opciones[which];
                    int imagenPedido = obtenerImagenPorNombre(pedidoSeleccionado);
                    Pedido nuevoPedido = new Pedido(imagenPedido, pedidoSeleccionado, "Descripción del pedido", 45);
                    customAdapter.agregarPedido(nuevoPedido);
                });
        builder.create().show();
    }

    // Método para mostrar un diálogo de edición de pedido
    private void mostrarDialogoEditarPedido(int position) {
        Pedido pedido = listaPedidos.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Pedido: " + pedido.getNombre());
        builder.setItems(new String[]{"Modificar nombre", "Modificar precio", "Cancelar"}, (dialog, which) -> {
            switch (which) {
                case 0: // Modificar nombre
                    AlertDialog.Builder nombreDialog = new AlertDialog.Builder(this);
                    nombreDialog.setTitle("Modificar Nombre");

                    final View nombreLayout = LayoutInflater.from(this).inflate(R.layout.dialog_edit_text, null);
                    nombreDialog.setView(nombreLayout);

                    nombreDialog.setPositiveButton("Aceptar", (dialog1, id) -> {
                        TextView nombreInput = nombreLayout.findViewById(R.id.edit_name);
                        pedido.setNombre(nombreInput.getText().toString());
                        customAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Nombre modificado", Toast.LENGTH_SHORT).show();
                    });
                    nombreDialog.setNegativeButton("Cancelar", (dialog1, id) -> dialog1.dismiss());
                    nombreDialog.create().show();
                    break;
                case 1: // Modificar precio
                    AlertDialog.Builder precioDialog = new AlertDialog.Builder(this);
                    precioDialog.setTitle("Modificar Precio");

                    final View precioLayout = LayoutInflater.from(this).inflate(R.layout.dialog_edit_text, null);
                    precioDialog.setView(precioLayout);

                    precioDialog.setPositiveButton("Aceptar", (dialog12, id) -> {
                        TextView precioInput = precioLayout.findViewById(R.id.edit_name);
                        try {
                            int nuevoPrecio = Integer.parseInt(precioInput.getText().toString());
                            pedido.setPrecio(nuevoPrecio);
                            customAdapter.notifyDataSetChanged();
                            Toast.makeText(this, "Precio modificado", Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {
                            Toast.makeText(this, "Ingresa un precio válido", Toast.LENGTH_SHORT).show();
                        }
                    });
                    precioDialog.setNegativeButton("Cancelar", (dialog12, id) -> dialog12.dismiss());
                    precioDialog.create().show();
                    break;
                case 2: // Cancelar
                    dialog.dismiss();
                    break;
            }
        });
        builder.create().show();
    }

    // Método para obtener la imagen correspondiente al nombre del pedido
    private int obtenerImagenPorNombre(String nombre) {
        switch (nombre) {
            case "Café Mocha":
                return R.drawable.cafe_mocha;
            case "Café Americano":
                return R.drawable.cafe_americano;
            case "Té Verde":
                return R.drawable.te_verde;
            case "Chocolate Caliente":
                return R.drawable.chocolate_caliente;
            case "Capuchino":
                return R.drawable.capuchino;
            case "Espresso":
                return R.drawable.espresso;
            default:
                return R.drawable.default_image;
        }
    }

    // Método para obtener los pedidos según el usuario actual
    private ArrayList<Pedido> obtenerPedidosDeUsuario(String usuario) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        if (usuario.equals("Usuario1")) {
            pedidos.add(new Pedido(R.drawable.cafe_americano, "Café Americano", "Café solo", 35));
            pedidos.add(new Pedido(R.drawable.cafe_mocha, "Café Mocha", "Con chocolate", 45));
        } else if (usuario.equals("Usuario2")) {
            pedidos.add(new Pedido(R.drawable.te_verde, "Té Verde", "Con menta", 40));
            pedidos.add(new Pedido(R.drawable.chocolate_caliente, "Chocolate Caliente", "Con malvaviscos", 55));
        } else if (usuario.equals("Usuario3")) {
            pedidos.add(new Pedido(R.drawable.capuchino, "Capuchino", "Con crema", 45));
            pedidos.add(new Pedido(R.drawable.espresso, "Espresso", "Doble carga", 60));
        }
        return pedidos;
    }
}
