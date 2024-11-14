package com.example.adaptadores;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private TextView nameTextView;
    private EditText ageEditText;
    private Button sendButton, logoutButton;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // Inicializar vistas
        nameTextView = findViewById(R.id.nameTextView);
        ageEditText = findViewById(R.id.ageEditText);
        sendButton = findViewById(R.id.sendButton);
        logoutButton = findViewById(R.id.logoutButton);
        // Obtener el nombre del usuario desde el Intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");
        // Mostrar el nombre en los TextView correspondientes
        nameTextView.setText("Bienvenido, " + userName); // Mostrar el nombre
        // Validar y enviar edad
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageString = ageEditText.getText().toString().trim();
                if (!ageString.isEmpty()) {
                    try {
                        int age = Integer.parseInt(ageString);
                        if (age >= 13 && age <= 70) {
                            Intent thirdActivityIntent = new Intent(WelcomeActivity.this, ThirdActivity.class);
                            thirdActivityIntent.putExtra("USER_NAME", userName);
                            thirdActivityIntent.putExtra("USER_AGE", age);
                            startActivity(thirdActivityIntent);
                            ageEditText.setText(""); // Limpiar el EditText después de enviar
                        } else {
                            Toast.makeText(WelcomeActivity.this, "La edad  debe estar entre 13 y 70 años", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(WelcomeActivity.this, "Por favor, ingrese una edad válida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WelcomeActivity.this, "Por favor, ingrese su edad", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Manejar cierre de sesión
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog(); // Mostrar diálogo de confirmación
            }
        });
        // Configurar el OnKeyListener para manejar el evento de "Atrás"
        findViewById(android.R.id.content).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode ==
                        KeyEvent.KEYCODE_BACK) {
                    showLogoutConfirmationDialog(); // Mostrar diálogo de confirmación
                    return true; // Indica que el evento fue manejado
                }
                return false; // Permitir que otros eventos se manejen normalmente
            }
        });
    }
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> finish()) //  Cierra la actividad
                .setNegativeButton("No", null) // No hace nada si selecciona
                // "No"

                .show();
    }
}