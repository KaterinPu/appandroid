package com.example.tarea_21.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea_21.Adapters.ProductoAdapter
import com.example.tarea_21.Models.Producto
import com.example.tarea_21.R

class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout que acabamos de crear
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Configuramos el RecyclerView
        val rv = view.findViewById<RecyclerView>(R.id.rvProductos)
        rv.layoutManager = LinearLayoutManager(context)

        // Creamos la lista de los 4 productos que pidió la profe
        val listaProductos = listOf(
            Producto("Reloj Inteligente", "$ 150.000", android.R.drawable.ic_lock_idle_alarm),
            Producto("Audífonos Pro", "$ 85.000", android.R.drawable.ic_lock_silent_mode),
            Producto("Cargador Carga Rápida", "$ 45.000", android.R.drawable.ic_menu_send),
            Producto("Funda Protectora", "$ 20.000", android.R.drawable.ic_menu_camera)
        )

        // Conectamos la lista con el Adaptador
        rv.adapter = ProductoAdapter(listaProductos)

        return view
    }
}