package com.example.tarea_21

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.websocket.WebSocketDeflateExtension.Companion.install


object ClienteSupabase {


    private const val SUPABASE_URL = "https://alpmvgmiwvnprbwudqnc.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFscG12Z21pd3ZucHJid3VkcW5jIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzU5MjcwNzIsImV4cCI6MjA5MTUwMzA3Mn0.iasVKO0TvlsRMbtgo60YlzOOtl7gQoQjxjt4d9gJUPo"

    val cliente: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }
}