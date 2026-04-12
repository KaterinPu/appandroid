package com.example.tarea_21.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tarea_21.ClienteSupabase
import com.example.tarea_21.MainActivity
import com.example.tarea_21.R

// IMPORTS DE LA VERSIÓN 3.0.0 (¡Adiós gotrue!)
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        val txtVolver = findViewById<TextView>(R.id.txtVolverLogin)
        txtVolver.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val etNombres = findViewById<EditText>(R.id.etNombresRegistro)
        val etApellidos = findViewById<EditText>(R.id.etApellidosRegistro)
        val etCorreo = findViewById<EditText>(R.id.etCorreoRegistro)
        val etContrasena = findViewById<EditText>(R.id.etPasswordRegistro)
        val etConfirmarContrasena = findViewById<EditText>(R.id.etConfirmPasswordRegistro)
        val chkTerminos = findViewById<CheckBox>(R.id.chkTerminos)
        val btnRegistrar = findViewById<Button>(R.id.btnFinalizarRegistro)

        btnRegistrar.setOnClickListener {
            val nombres = etNombres.text.toString()
            val apellidos = etApellidos.text.toString()
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()
            val confirmarContrasena = etConfirmarContrasena.text.toString()

            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contrasena != confirmarContrasena) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!chkTerminos.isChecked) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnRegistrar.isEnabled = false
            Toast.makeText(this, "Registrando...", Toast.LENGTH_SHORT).show()

            lifecycleScope.launch {
                try {
                    // ¡AQUÍ ESTÁ LA MAGIA! Usamos auth en lugar de gotrue
                    val usuario = ClienteSupabase.cliente.auth.signUpWith(Email) {
                        email = correo
                        password = contrasena
                    }

                    if (usuario != null) {
                        val datosNuevoUsuario = mapOf(
                            "correo" to correo,
                            "nombres" to nombres,
                            "apellidos" to apellidos,
                            "rol" to 3
                        )
                        ClienteSupabase.cliente.postgrest["usuarios"].insert(datosNuevoUsuario)
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegistroActivity, "¡Registro exitoso!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegistroActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegistroActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        btnRegistrar.isEnabled = true
                    }
                }
            }
        }
    }
}