package com.example.tarea_21

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.tarea_21.Fragments.InicioFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 1. CONECTAMOS LA BARRA (TOOLBAR)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 2. CREAMOS EL BOTÓN HAMBURGUESA (El "Toggle")
        // Esto es lo que físicamente dibuja las tres rayitas
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            0,
            0
        )

        // 3. SE LO PEGAMOS AL MENÚ LATERAL Y LO DIBUJAMOS
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState() // ¡ESTA LÍNEA ES LA QUE HACE APARECER EL ÍCONO!

        // 4. CARGAR EL FRAGMENT DE INICIO POR DEFECTO
        if (savedInstanceState == null) {
            cambiarPantalla(InicioFragment())
        }

        // 5. CONFIGURAR EL MENÚ LATERAL (DRAWER)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_perfil -> mostrarMensaje("Mi Perfil")
                R.id.menu_configuracion -> mostrarMensaje("Configuración")
                R.id.menu_soporte -> mostrarMensaje("Soporte")
                R.id.menu_salir -> finish()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // 6. CONFIGURAR EL MENÚ INFERIOR (BOTTOM NAV)
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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