package com.example.adaptadores;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class ThirdActivity extends AppCompatActivity {
    private TextView nameDisplayTextView;
    private TextView ageDisplayTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        // Inicializar vistas
        nameDisplayTextView = findViewById(R.id.nameDisplayTextView);
        ageDisplayTextView = findViewById(R.id.ageDisplayTextView);
        // Obtener los datos del Intent
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        int userAge = intent.getIntExtra("USER_AGE", -1);
        // Verificar si los datos son válidos
        if (userName != null && !userName.isEmpty()) {
            nameDisplayTextView.setText("Nombre: " + userName);
        } else {
            nameDisplayTextView.setText("Nombre: Desconocido"); // Mensaje predeterminado
        }
        if (userAge > 0) {
            ageDisplayTextView.setText("Edad: " + userAge);
        } else {
            ageDisplayTextView.setText("Edad: Desconocida"); // Mensaje predeterminado
        }
    }
}