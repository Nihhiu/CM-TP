package com.cleartab.cleartab.Retrofit

import com.cleartab.cleartab.Retrofit.tabels.*
import retrofit2.Call
import retrofit2.http.*

data class UpdatePerfil(
    val username: String,
    val nome: String,
    val idTipoUtilizador: Int,
    val email: String
)


interface SupabaseAuthService {

    companion object{
        const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InprYmV1bnl6bWd1bXJ3dHBscGtjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTk5NTQ0ODksImV4cCI6MjAzNTUzMDQ4OX0.rWStS8EPEpVy4L6J522gzva1hNF-CcAbySxt3i19eho"
    }

    @Headers("Content-Type: application/json", "apikey: $API_KEY")
    @GET()

}
