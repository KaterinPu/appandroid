package com.example.tarea_21.Fragments

import android.R.attr.password
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tarea_21.ClienteSupabase
import com.example.tarea_21.R
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import io.github.jan.supabase.postgrest.query.Columns

class EditarPerfilFragment : Fragment() {

    // 1. ESTA ES LA FUNCIÓN QUE TE FALTABA (Conecta el código con el XML)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Asegúrate de que tu XML se llame fragment_editar_perfil
        return inflater.inflate(R.layout.fragment_editar_perfil, container, false)
    }

    // 2. AQUÍ EMPIEZA LA LÓGICA DE TUS BOTONES
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. PRIMERO: Enlazamos los componentes (ESTO YA LO TIENES)
        val etNombres = view.findViewById<EditText>(R.id.et_nombres)
        val etApellidos = view.findViewById<EditText>(R.id.et_apellidos)
        val etCorreo = view.findViewById<EditText>(R.id.et_correo)
        val etContrasena = view.findViewById<EditText>(R.id.et_contrasena)
        val etReContrasena = view.findViewById<EditText>(R.id.et_re_contrasena)
        val btnGuardar = view.findViewById<Button>(R.id.btn_guardar_perfil)

        // -------------------------------------------------------------------------
        // 2. AQUÍ VA EL CÓDIGO DE CARGA (Pégalo justo aquí abajo)
        // -------------------------------------------------------------------------
        lifecycleScope.launch {
            try {
                val usuarioLogueado = ClienteSupabase.cliente.auth.currentUserOrNull()
                val correoActual = usuarioLogueado?.email

                if (correoActual != null) {
                    // Buscamos los datos en la tabla

                    val datos = ClienteSupabase.cliente.postgrest["usuarios"].select {
                        filter {
                            eq("correo", correoActual)
                        }
                    }.decodeList<Map<String, String>>()

                    withContext(Dispatchers.Main) {
                        if (datos.isNotEmpty()) {
                            val miUsuario = datos[0]
                            etNombres.setText(miUsuario["nombres"])
                            etApellidos.setText(miUsuario["apellidos"])
                            etCorreo.setText(miUsuario["correo"])
                            etCorreo.isEnabled = false // Para que no cambien el correo principal
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    println("ERROR_CARGA: ${e.message}")
                }
            }
        }
        // ---------------------------------------------------------

        // 3. ACCIÓN DEL BOTÓN GUARDAR (Tu código original)
        btnGuardar.setOnClickListener {
            val nombres = etNombres.text.toString()
            val apellidos = etApellidos.text.toString()
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()
            val reContrasena = etReContrasena.text.toString()

            if (correo.isEmpty() || nombres.isEmpty()) {
                Toast.makeText(requireContext(), "El correo y nombres son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contrasena != reContrasena) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "Guardando cambios...", Toast.LENGTH_SHORT).show()
            btnGuardar.isEnabled = false

            lifecycleScope.launch {
                try {
                    val datosActualizados = mapOf(
                        "nombres" to nombres,
                        "apellidos" to apellidos
                    )

                    ClienteSupabase.cliente.postgrest["usuarios"]
                        .update(datosActualizados) {
                            filter {
                                eq("correo", correo)
                            }
                        }

                    if (contrasena.isNotEmpty()) {
                        ClienteSupabase.cliente.auth.updateUser {
                            password = contrasena
                        }
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "¡Perfil actualizado!", Toast.LENGTH_LONG).show()
                        btnGuardar.isEnabled = true
                        etContrasena.text.clear()
                        etReContrasena.text.clear()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
                        btnGuardar.isEnabled = true
                    }
                }
            }
        }
    }
}