package com.example.adaptadores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.media.AudioAttributes;
import android.media.SoundPool;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private SoundPool soundPool;
    private int cuchara_cafe;
    private DatabaseHelper dbHelper;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar DatabaseHelper y PreferencesHelper
        dbHelper = new DatabaseHelper(this);
        preferencesHelper = new PreferencesHelper(this);

        // Verificar si es la primera vez que el usuario abre la app
        if (preferencesHelper.obtenerPreferencia("is_first_time").isEmpty()) {
            preferencesHelper.guardarPreferencia("is_first_time", "false");
            // Muestra el splash screen o alguna pantalla introductoria
        }

        // Guardar y cargar el tema de la app
        preferencesHelper.guardarPreferencia("app_theme", "dark");
        String temaActual = preferencesHelper.obtenerPreferencia("app_theme");

        // Guardar y leer una contraseña encriptada
        String passwordEncriptado = preferencesHelper.encriptar("1234");
        preferencesHelper.guardarPreferencia("user_password", passwordEncriptado);
        String passwordDesencriptado = preferencesHelper.desencriptar(preferencesHelper.obtenerPreferencia("user_password"));

        // Configurar el botón para insertar un producto
        Button btnInsertarProducto = findViewById(R.id.btnInsertarProducto);
        btnInsertarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarProductoEjemplo();
            }
        });

        // Inicializar vistas
        Button buttonOpenVideoPlayer = findViewById(R.id.button_open_video_player);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        Button loginButton = findViewById(R.id.button);

        // Configurar el botón para abrir VideoPlayerActivity
        buttonOpenVideoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el botón para abrir SettingsActivity
        Button btnOpenSettings = findViewById(R.id.btnOpenSettings);
        btnOpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Configurar AudioAttributes y SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        cuchara_cafe = soundPool.load(this, R.raw.sonido_boton, 1);

        // Listener para el botón de inicio de sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(v); // Reproducir el sonido al hacer clic
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.equals("usuario1@ejemplo.com") && password.equals("1234")) {
                    iniciarListViewActivity("Usuario1");
                } else if (email.equals("usuario2@ejemplo.com") && password.equals("1234")) {
                    iniciarListViewActivity("Usuario2");
                } else if (email.equals("usuario3@ejemplo.com") && password.equals("1234")) {
                    iniciarListViewActivity("Usuario3");
                } else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para insertar un producto de ejemplo
    private void insertarProductoEjemplo() {
        long resultado = dbHelper.insertarProducto("Producto Ejemplo", 10.0, 1);
        if (resultado != -1) {
            Toast.makeText(this, "Producto agregado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para iniciar ListViewActivity
    private void iniciarListViewActivity(String userName) {
        Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
    }

    // Método para reproducir sonido
    public void playSound(View view) {
        soundPool.play(cuchara_cafe, 1, 1, 0, 0, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos de SoundPool
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
