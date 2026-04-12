package com.example.tarea_21.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tarea_21.ClienteSupabase
import com.example.tarea_21.MainActivity
import com.example.tarea_21.R

// Imports perfectos de la versión 3.0.0
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // 1. BUSCAMOS LOS CAMPOS DE TEXTO
        val etCorreo = findViewById<EditText>(R.id.etCorreoLogin) // Cambia este ID si es diferente
        val etContrasena = findViewById<EditText>(R.id.etPasswordLogin) // Cambia este ID si es diferente

        // 2. LÓGICA DEL BOTÓN INGRESAR
        val btnIngresar = findViewById<Button>(R.id.btnIngresarLogin)
        btnIngresar.setOnClickListener {
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()

            // Validamos que no estén vacíos
            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Ingresa tu correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnIngresar.isEnabled = false
            Toast.makeText(this, "Iniciando sesión...", Toast.LENGTH_SHORT).show()

            // Conectamos con Supabase
            lifecycleScope.launch {
                try {
                    // Magia de la versión 3.0.0: auth en lugar de gotrue
                    ClienteSupabase.cliente.auth.signInWith(Email) {
                        email = correo
                        password = contrasena
                    }

                    // Si sale bien, saltamos al Main
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "¡Bienvenida!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        // Esto nos dirá el error REAL (ej: "Invalid login credentials", "Network error", etc.)
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        btnIngresar.isEnabled = true
                        // Imprime el error en la consola de Android Studio para que lo veas allá también
                        println("DEBUG_SUPABASE: ${e.message}")
                    }
                }
            }
        }

        // 3. TEXTO DE REGISTRARSE
        val txtRegistrarse = findViewById<TextView>(R.id.txtRegistrarse)
        txtRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}