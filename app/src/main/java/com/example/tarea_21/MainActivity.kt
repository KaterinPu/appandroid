package com.example.tarea_21 // Asegúrate de que sea tu paquete

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Configurar los clics del Menú Lateral
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_perfil -> mostrarMensaje("Cargando: Mi Perfil")
                R.id.menu_configuracion -> mostrarMensaje("Cargando: Configuración")
                R.id.menu_soporte -> mostrarMensaje("Cargando: Soporte")
                R.id.menu_salir -> finish()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Configurar los clics del Menú Inferior
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_inicio -> mostrarMensaje("Cargando: Inicio")
                R.id.nav_buscar -> mostrarMensaje("Cargando: Buscar")
                R.id.nav_favoritos -> mostrarMensaje("Cargando: Favoritos")
                R.id.nav_cuenta -> mostrarMensaje("Cargando: Cuenta")
            }
            true
        }


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Si el menú lateral está abierto, lo cerramos
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // Si ya está cerrado, salimos de la pantalla (comportamiento normal)
                    finish()
                }
            }
        })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}