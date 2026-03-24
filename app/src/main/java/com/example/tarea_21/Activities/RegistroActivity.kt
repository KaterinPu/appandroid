package com.example.tarea_21.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea_21.MainActivity // Importa tu Main si está en otra carpeta
import com.example.tarea_21.R

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        //Config la acción de "Volver a Login"
        val txtVolver = findViewById<TextView>(R.id.txtVolverLogin)
        txtVolver.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Config el botón de "Registrarme"
        val btnRegistrar = findViewById<Button>(R.id.btnFinalizarRegistro)
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}