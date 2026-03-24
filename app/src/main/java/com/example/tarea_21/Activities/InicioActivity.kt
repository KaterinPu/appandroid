package com.example.tarea_21.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea_21.MainActivity
import com.example.tarea_21.R

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        //Botón de comenzar
        val btnComenzar = findViewById<Button>(R.id.btnComenzarInicio)
        btnComenzar.setOnClickListener {
            // ¡Aquí está el cambio! Ahora va al LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Tex de registro
        val txtRegistro = findViewById<TextView>(R.id.txtEnlaceRegistro) // <-- ID nuevo
        txtRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}