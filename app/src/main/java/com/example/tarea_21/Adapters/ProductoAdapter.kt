package com.example.tarea_21.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea_21.Models.Producto
import com.example.tarea_21.R

// Este es el "traductor" que une tus datos con el diseño item_producto.xml
class ProductoAdapter(private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    // Esta clase interna busca los elementos de tu diseño item_producto.xml
    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.txtNombreProducto)
        val precio: TextView = view.findViewById(R.id.txtPrecioProducto)
        val imagen: ImageView = view.findViewById(R.id.imgProducto)
    }

    // Aquí inflamos el diseño de cada "cuadrito" de producto
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    // Aquí le decimos qué dato va en cada TextView e ImageView
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item = listaProductos[position]
        holder.nombre.text = item.nombre
        holder.precio.text = item.precio
        holder.imagen.setImageResource(item.imagen)
    }

    // Le decimos cuántos elementos hay en total
    override fun getItemCount(): Int = listaProductos.size
}