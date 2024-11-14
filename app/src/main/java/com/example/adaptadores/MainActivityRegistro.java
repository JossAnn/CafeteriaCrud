package com.example.adaptadores;
import android.content.Intent; // Importa Intent para cambiar de actividad
import android.os.Bundle;
import android.view.View; // Importa View para manejar clics
import android.widget.Button; // Importa Button para el botón de registro
import android.widget.EditText; // Importa EditText para capturar los datos del usuario
import android.widget.Toast; // Importa Toast para mostrar mensajes
import androidx.appcompat.app.AppCompatActivity;
public class MainActivityRegistro extends AppCompatActivity {
    private EditText editTextNombre, editTextCorreo, editTextContrasena;
    private Button buttonRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro); // Establece el layout
        // Enlaza los EditText con los del layout
        editTextNombre = findViewById(R.id.editTextText); // Campo de nombre (ID en XML)
        editTextCorreo = findViewById(R.id.editTextTextEmailAddress); // Campo de correo (ID en XML)
        editTextContrasena = findViewById(R.id.editTextTextPassword); // Campo de contraseña (ID en XML)
        // Enlaza el botón de registrar
        buttonRegistrar = findViewById(R.id.button);
        // Configura el listener para el botón de registrar
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String userName = editTextNombre.getText().toString();
                String userEmail = editTextCorreo.getText().toString();
                String userPassword = editTextContrasena.getText().toString();
                // Validación de campos
                if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(MainActivityRegistro.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Si la validación es correcta, pasa los datos a la actividad de bienvenida
                    Intent intent = new Intent(MainActivityRegistro.this, WelcomeActivity.class);
                    intent.putExtra("USER_NAME", userName); // Pasar el nombre del usuario
                    startActivity(intent); // Lanza la actividad de bienvenida
                }

            }
        });
    }
}