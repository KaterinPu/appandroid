package com.example.tarea_21

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment // IMPORTANTE: Para usar Fragments
import com.example.tarea_21.Fragments.InicioFragment // IMPORTANTE: Tu pantalla de productos
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

        // 1. CARGAR EL FRAGMENT DE INICIO POR DEFECTO
        // Esto hace que los productos aparezcan apenas abres la app
        if (savedInstanceState == null) {
            cambiarPantalla(InicioFragment())
        }

        // 2. CONFIGURAR EL MENÚ LATERAL (DRAWER)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Aquí podrías crear otros Fragments después, por ahora cargamos Inicio
                R.id.menu_perfil -> mostrarMensaje("Mi Perfil")
                R.id.menu_configuracion -> mostrarMensaje("Configuración")
                R.id.menu_soporte -> mostrarMensaje("Soporte")
                R.id.menu_salir -> finish()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // 3. CONFIGURAR EL MENÚ INFERIOR (BOTTOM NAV)
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // CUANDO TOCAS INICIO, CARGA TU LISTA DE PRODUCTOS
                R.id.nav_inicio -> cambiarPantalla(InicioFragment())
                R.id.nav_buscar -> mostrarMensaje("Buscando...")
                R.id.nav_favoritos -> mostrarMensaje("Tus favoritos")
                R.id.nav_cuenta -> mostrarMensaje("Tu cuenta")
            }
            true
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    finish()
                }
            }
        })
    }

    // 4. ESTA ES LA FUNCIÓN MÁGICA QUE CAMBIA LAS PANTALLAS
    private fun cambiarPantalla(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Asegúrate que el ID coincida con tu XML
            .commit()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}