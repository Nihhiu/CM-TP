package com.cleartab.cleartab.retrofit

//Import Connection
import com.cleartab.cleartab.retrofit.supabase

import com.cleartab.cleartab.retrofit.tables.Utilizador
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

interface SupabaseAuthService {
    suspend fun signUp(utilizador: Utilizador): Boolean {

//      Criar conta no Auth do Supabase
        kotlin.runCatching {
            supabase.auth.signUpWith(Email) {
                email = utilizador.email
                password = utilizador.password
            }
        }.onFailure {
            println("There was an error while registering: ${it.message}")
            return false
        }

//      Criar conta na Base de Dados
        try {
            val response = supabase
                .from("Utilizador")
                .insert(utilizador)

            println("Inserted Utilizador successfully: ${response.data}")
            return true
        } catch (e: Exception) {
            // An error occurred
            println("Error: ${e.message}")
        }
        return false
    }

    suspend fun logIn(emailLogin: String, passwordLogin: String): Boolean {

        kotlin.runCatching {
            supabase.auth.signInWith(Email) {
                email = emailLogin
                password = passwordLogin
            }
        }.onFailure {
            println("There was an error while registering: ${it.message}")
            return false
        }
        return true
    }

    suspend fun signOut(): Boolean {
        kotlin.runCatching {
            supabase.auth.signOut()
        }.onFailure {
            println("There was an error while registering: ${it.message}")
            return false
        }
        return true
    }


}
