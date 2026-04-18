package com.example.tarea_21.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.tarea_21.ClienteSupabase
import com.example.tarea_21.MainActivity
import com.example.tarea_21.R
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Configuramos la huella apenas abre la app
        configurarHuella()

        //Botón Ingresar Tradicional
        findViewById<Button>(R.id.btnIngresarLogin).setOnClickListener {
            iniciarSesion()
        }

        //Texto para ir a Registro
        findViewById<TextView>(R.id.txtRegistrarse).setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun iniciarSesion() {
        val correo = findViewById<EditText>(R.id.etCorreoLogin).text.toString().trim()
        val contrasena = findViewById<EditText>(R.id.etPasswordLogin).text.toString()

        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                ClienteSupabase.cliente.auth.signInWith(Email) {
                    email = correo
                    password = contrasena
                }
                irAMain()
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun configurarHuella() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@LoginActivity, "¡Huella reconocida!", Toast.LENGTH_SHORT).show()
                    irAMain()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    //No hacemos nada para que el usuario pueda seguir intentando
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("ACTIVACoop Biométrico")
            .setSubtitle("Usa tu huella para ingresar")
            .setNegativeButtonText("Usar contraseña")
            .build()

        // Ahora sí, el botón existe en el XML
        findViewById<Button>(R.id.btn_huella).setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun irAMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}