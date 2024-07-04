package com.cleartab.cleartab.retrofit

//Import Connection

import com.cleartab.cleartab.retrofit.tables.Utilizador
import com.cleartab.cleartab.retrofit.tables.TipoUtilizador
import com.cleartab.cleartab.retrofit.tables.Projeto
import com.cleartab.cleartab.retrofit.tables.Tarefa

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.serialization.internal.throwArrayMissingFieldException

data class UserProfile(
    val utilizador: Utilizador,
    val tiposUtilizador: TipoUtilizador
)

interface SupabaseAuthService {


//  Utilizadores
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
            println("Error: ${e.message}")
        }
        return false
    }
    suspend fun logIn(emailLogin: String, passwordLogin: String): Boolean {
//      Login no Auth
        kotlin.runCatching {
            supabase.auth.signInWith(Email) {
                email = emailLogin
                password = passwordLogin
            }
        }.onFailure {
            println("There was an error while loging in: ${it.message}")
            return false
        }

//      Verificar se existe na DB
        val existsUserEmail = supabase
            .from("Utilizador")
            .select(columns = Columns.list("email")) {
                filter {
                    eq("email", emailLogin)
                }
            }

        if (existsUserEmail.data.isEmpty()) {
            println("User doesn't exist")
            return false
        } else {
            return true
        }
    }
    suspend fun signOut(): Boolean {
        kotlin.runCatching {
            supabase.auth.signOut()
        }.onFailure {
            println("There was an error while signing out: ${it.message}")
            return false
        }
        return true
    }
    suspend fun editProfile(utilizador: Utilizador, tipoUtilizador: TipoUtilizador): Boolean {
        try {
//          Update Utilizador
            val utilizadorResponse = supabase
                .from("Utilizador")
                .update(
                    {
                        set("nome", utilizador.nome)
                        set("email", utilizador.email)
                        set("password", utilizador.password)
                        set("fotografia", utilizador.fotografia)
                    }
                ) {
                    filter {
                        utilizador.idUtilizador?.let { eq("idUtilizador", it) }
                    }
                }

//          Update Auth
            supabase.auth.updateUser {
                email = utilizador.email
            }

//          Update TipoUtilizador
            val tipoUtilizadorResponse = supabase
                .from("TipoUtilizador")
                .update(
                    {
                        set("tipo", tipoUtilizador.tipo)
                    }
                ) {
                    filter {
                        tipoUtilizador.idUtilizador?.let { eq("idUtilizador", it) }
                        tipoUtilizador.idProjeto?.let { eq("idProjeto", it) }
                    }
                }

            println("Updated Utilizador and TipoUtilizador successfully.")
            return true
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return false
    }
    suspend fun deleteProfile(idUtilizador: Long): Boolean {
//      Delete from DB
        kotlin.runCatching {
            supabase
                .from("Utilizador")
                .delete {
                    filter {
                        eq("idUtilizador", idUtilizador)
                    }
                }
        }.onFailure {
            println("There was an error while registering: ${it.message}")
            return false
        }
        return true
    }
    suspend fun fetchProfile(idUtilizador: Long, idProjeto: Long): UserProfile? {
        try {
//          Obter dados do utilizador
            val utilizadorResponse = supabase
                .from("Utilizador")
                .select() {
                    filter {
                        eq("idUtilizador", idUtilizador)
                    }
                }
                .decodeSingle<Utilizador>()
//          Obter tipo de utilizador
            val tiposUtilizadorResponse = supabase
                .from("TipoUtilizador")
                .select() {
                    filter {
                        eq("idUtilizador", idUtilizador)
                        eq("idProjeto", idProjeto)
                    }
                }
                .decodeSingle<TipoUtilizador>()
//          Concatenar as informações
            return UserProfile(
                utilizador = utilizadorResponse,
                tiposUtilizador = tiposUtilizadorResponse
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }


//  Projetos
    suspend fun createProject(projeto: Projeto, idUtilizador: Long): Boolean {
        try {
            val response = supabase
                .from("Projeto")
                .insert(projeto)

            println("Created Projeto successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        //adicionar tipo de utilizador
        val tipoUtilizador = TipoUtilizador(idUtilizador, projeto.idProjeto, "Admin")
        try {
            val response = supabase
                .from("TipoUtilizador")
                .insert(tipoUtilizador)

            println("Added Utilizador to Project successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        return true
    }
    suspend fun deleteProject(idProjeto: Long): Boolean {
//      Delete from DB projeto
        try {
            val response = supabase
                .from("Projeto")
                .delete(){
                    filter {
                        eq("idProjeto", idProjeto)
                    }
                }

            println("Deleted Projeto successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        return true
    }
    suspend fun editProject(projeto: Projeto): Boolean {
        try {
//          Update Projeto
            val utilizadorResponse = supabase
                .from("Projeto")
                .update(
                    {
                        set("nome", projeto.nome)
                        set("descricao", projeto.descricao)
                    }
                ) {
                    filter {
                        projeto.idProjeto?.let { eq("idProjeto", it) }
                    }
                }

            println("Updated Projeto successfully.")
            return true
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return false
    }
    suspend fun fetchProjectsList(): List<Projeto>? {
        try {
            val response = supabase
                .from("Projeto")
                .select(
                    columns = Columns.list("idProjeto", "nome")
                )
                .decodeList<Projeto>()

            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }
    suspend fun fetchProject(idProjeto: Long): Projeto? {
        try {
            val response = supabase
                .from("Projeto")
                .select(){
                    filter {
                        eq("idProjeto", idProjeto)
                    }
                }
                .decodeSingle<Projeto>()

            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }


//  Vincular TipoUtilizador ao Projeto
    suspend fun addUtilizadorToProject(tipoUtilizador: TipoUtilizador): Boolean {
        try {
            val response = supabase
                .from("TipoUtilizador")
                .insert(tipoUtilizador)

            println("Added Utilizador to Project successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        return true
    }
    suspend fun removeUtilizadorFromProject(idUtilizador: Long, idProjeto: Long): Boolean {
        //#TODO(remover utilizador do projeto)
        return false
    }
    suspend fun fetchUtilizadorFromProject(idProjeto: Long): List<UserProfile>? {
        try {
            val listTipoUtilizador = supabase
                .from("TipoUtilizador")
                .select() {
                    filter {
                        eq("idProjeto", idProjeto)
                        }
                }
                .decodeList<TipoUtilizador>()

            val idUtilizadorList = listTipoUtilizador.mapNotNull { it.idUtilizador }

            val listUtilizador = supabase
                .from("Utilizador")
                .select(
                    columns = Columns.list("idUtilizador", "nome")
                ) {
                    filter {
                        isIn("idUtilizador", idUtilizadorList)
                    }
                }
                .decodeList<Utilizador>()

            val userProfiles: List<UserProfile> = listUtilizador.zip(listTipoUtilizador) { utilizador, tipoUtilizador ->
                UserProfile(utilizador = utilizador, tiposUtilizador = tipoUtilizador)
            }

            return userProfiles
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }


//  Tarefa
    suspend fun createTask(tarefa: Tarefa): Boolean {
        try {
            val response = supabase
                .from("Tarefa")
                .insert(tarefa)

            println("Created Tarefa successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        return true
    }
    suspend fun deleteTask(idTarefa: Long): Boolean {
        try {
            val response = supabase
                .from("Tarefa")
                .delete(){
                    filter {
                        eq("idTarefa", idTarefa)
                    }
                }

            println("Deleted Tarefa successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        return true
    }
    suspend fun editTask(tarefa: Tarefa): Boolean {
        try {
//          Update Tarefa
            val utilizadorResponse = supabase
                .from("Tarefa")
                .update(
                    {
                        set("titulo", tarefa.titulo)
                        set("data", tarefa.data)
                        set("descricao", tarefa.descricao)
                    }
                ) {
                    filter {
                        tarefa.idTarefa?.let { eq("idTarefa", it) }
                    }
                }

            println("Updated Tarefa successfully.")
            return true
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return false
    }
    suspend fun fetchTasksList(): List<Tarefa>? {
        try {
            val response = supabase
                .from("Tarefa")
                .select(
                    columns = Columns.list("Tarefa", "titulo", "data")
                ){
                    order(
                        column = "data",
                        order = Order.ASCENDING
                    )
                }
                .decodeList<Tarefa>()

            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }
    suspend fun fetchTask(idTarefa: Long): Tarefa? {
        try {
            val response = supabase
                .from("Tarefa")
                .select(){
                    filter {
                        eq("idTarefa", idTarefa)
                    }
                }
                .decodeSingle<Tarefa>()

            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }


//  Vincular Tarefa ao Projeto
    suspend fun addTaskToProject(idTarefa: Long, idProjeto: Long): Boolean {
        //#TODO(adicionar tarefa ao projeto)
        return false
    }
    suspend fun removeTaskFromProject(idTarefa: Long, idProjeto: Long): Boolean {
        //#TODO(remover tarefa do projeto)
        return false
    }


//  Avaliação
    suspend fun createRating(idUtilizador: Long, idTarefa: Long, avaliacao: Long): Boolean {
        //#TODO(criar avaliação)
        return false
    }
    suspend fun fetchRatingsList(): Boolean {
        //#TODO(buscar apenas as avaliações)
        return false
    }
    suspend fun fetchRating(idUtilizador: Long, idTarefa: Long): Boolean {
        //#TODO(buscar apenas uma avaliação)
        return false
    }


//  Vincular Utilizador a Tarefas
    suspend fun addUtilizadorToTask(idUtilizador: Long, idTarefa: Long): Boolean {
        //#TODO(adicionar utilizador a tarefa)
        return false
    }
    suspend fun removeUtilizadorFromTask(idUtilizador: Long, idTarefa: Long): Boolean {
        //#TODO(remover utilizador da tarefa)
        return false
    }
    suspend fun fetchUsersList(): Boolean {
        //#TODO(buscar apenas os utilizadores)
        return false
    }
    suspend fun addAvaliacao(tarefa: Tarefa): Boolean {
        //#TODO(adicionar avaliação)
        return false
    }
}




