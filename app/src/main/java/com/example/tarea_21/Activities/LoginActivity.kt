package com.example.tarea_21.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea_21.MainActivity
import com.example.tarea_21.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        //Botón de Ingresar
        val btnIngresar = findViewById<Button>(R.id.btnIngresarLogin) // <-- ID nuevo
        btnIngresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Tex de Regis
        val txtRegistrarse = findViewById<TextView>(R.id.txtRegistrarse) // <-- ID nuevo
        txtRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}