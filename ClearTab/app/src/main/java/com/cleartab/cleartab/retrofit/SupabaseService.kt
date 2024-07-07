package com.cleartab.cleartab.retrofit

import com.cleartab.cleartab.retrofit.tables.Avaliacao
import com.cleartab.cleartab.retrofit.tables.Projeto
import com.cleartab.cleartab.retrofit.tables.Tarefa
import com.cleartab.cleartab.retrofit.tables.TipoUtilizador
import com.cleartab.cleartab.retrofit.tables.Utilizador
import com.cleartab.cleartab.retrofit.tables.UtilizadorTarefa
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

data class UserProfile(
    val utilizador: Utilizador,
    val tiposUtilizador: TipoUtilizador
)

data class AvaliacaoProfile(
    val utilizador: String,
    val avaliacao: Avaliacao
)

class SupabaseService {


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
            val response = supabase.postgrest
                .from("Utilizador")
                .insert(utilizador)

            println("Inserted Utilizador successfully: ${response.data}")
            return true
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return false
    }
    suspend fun logIn(emailLogin: String, passwordLogin: String): Long? {
        // Tentar fazer login
        val loginResult = kotlin.runCatching {
            supabase.auth.signInWith(Email) {
                email = emailLogin
                password = passwordLogin
            }
        }

        loginResult.onFailure {
            println("There was an error while logging in: ${it.message}")
            return null
        }

        // Verificar se o usuário existe na DB
        val existsUserEmail = supabase.postgrest
            .from("Utilizador")
            .select(columns = Columns.list("idUtilizador","email")) {
                filter {
                    eq("email", emailLogin)
                }
            }

        if (existsUserEmail.data.isEmpty()) {
            println("User doesn't exist")
            return null
        }

        // Obter o ID do usuário
        val idUtilizador = supabase.postgrest
            .from("Utilizador")
            .select(columns = Columns.list("idUtilizador")) {
                filter {
                    eq("email", emailLogin)
                }
            }
            .decodeSingle<Utilizador>()

        return idUtilizador.idUtilizador
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
            val utilizadorResponse = supabase.postgrest
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
                        eq("idUtilizador", utilizador.idUtilizador!!)
                    }
                }

//          Update Auth
            supabase.auth.updateUser {
                email = utilizador.email
            }

//          Update TipoUtilizador
            val tipoUtilizadorResponse = supabase.postgrest
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
            supabase.postgrest
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
            val utilizadorResponse = supabase.postgrest
                .from("Utilizador")
                .select() {
                    filter {
                        eq("idUtilizador", idUtilizador)
                    }
                }
                .decodeSingle<Utilizador>()
//          Obter tipo de utilizador
            val tiposUtilizadorResponse = supabase.postgrest
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
    suspend fun createProject(projeto: Projeto, idUtilizador: Long): Long? {
        try {
            val projetoCriado = supabase.postgrest
                .from("Projeto")
                .insert(projeto)
                .decodeSingle<Projeto>()

            println("Created Projeto successfully: $projetoCriado")

//          Adicionar tipo de utilizador
            val tipoUtilizador = TipoUtilizador(idUtilizador, projetoCriado.idProjeto!!, "Admin")

            val response = supabase.postgrest
                .from("TipoUtilizador")
                .insert(tipoUtilizador)

            println("Added Utilizador to Project successfully: ${response.data}")
            return projetoCriado.idProjeto
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return null
        }
    }
    suspend fun deleteProject(idProjeto: Long): Boolean {
//      Delete from DB projeto
        try {
            val response = supabase.postgrest
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
            val utilizadorResponse = supabase.postgrest
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
    suspend fun fetchProjectsList(idUtilizador: Long): List<Projeto>? {
        try {
            val projectsUserIDs = supabase.postgrest
                .from("TipoUtilizador")
                .select(){
                    filter {
                        eq("idUtilizador", idUtilizador)
                    }
                }
                .decodeList<TipoUtilizador>()

            val projectsIDs = projectsUserIDs.map { it.idProjeto }

            val projectList = supabase.postgrest
                .from("Projeto")
                .select(
                    columns = Columns.list("idProjeto", "nome")
                ){
                    filter {
                        isIn("idProjeto", projectsIDs)
                    }
                }
                .decodeList<Projeto>()

            return projectList
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }
    suspend fun fetchProject(idProjeto: Long): Projeto? {
        try {
            val response = supabase.postgrest
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
    suspend fun addUtilizadorToProject(email: String, idProjeto: Long): Boolean {
        try {
            val idUtilizador = supabase.postgrest
                .from("Utilizador")
                .select() {
                    filter {
                        eq("email", email)
                    }
                }
                .decodeSingle<Utilizador>()

            val tipoUtilizador = TipoUtilizador(idUtilizador = idUtilizador.idUtilizador!!, idProjeto = idProjeto, tipo = "Utilizador")

            val response = supabase.postgrest
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
        try {
            val response = supabase.postgrest
                .from("TipoUtilizador")
                .delete(){
                    filter {
                        eq("idProjeto", idProjeto)
                        eq("idUtilizador", idUtilizador)
                    }
                }

            println("Deleted Utilizador from Projeto successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        return true
    }
    suspend fun fetchUtilizadorFromProject(idProjeto: Long): List<UserProfile>? {
        try {
            val listTipoUtilizador = supabase.postgrest
                .from("TipoUtilizador")
                .select() {
                    filter {
                        eq("idProjeto", idProjeto)
                        }
                }
                .decodeList<TipoUtilizador>()

            val idUtilizadorList = listTipoUtilizador.map { it.idUtilizador }

            val listUtilizador = supabase.postgrest
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
            val response = supabase.postgrest
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
            val response = supabase.postgrest
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
            val utilizadorResponse = supabase.postgrest
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
            val response = supabase.postgrest
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
            val response = supabase.postgrest
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


//  Auto-Avaliação Mensal
    suspend fun createRating(avaliacao: Avaliacao): Boolean {
        try {
            val response = supabase.postgrest
                .from("Avaliacao")
                .insert(avaliacao)

            println("Created Avaliacao successfully: ${response.data}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
        return true
    }
    suspend fun fetchRatingsList(mes: Int, ano: Int): List<AvaliacaoProfile>? {
        try {
            val listAvaliacao = supabase.postgrest
                .from("Avaliacao")
                .select(){
                    filter {
                        eq("mes", mes)
                        eq("ano", ano)
                    }
                }
                .decodeList<Avaliacao>()

            val idUtilizadorList = listAvaliacao.map { it.idUtilizador }

            val listUtilizador = supabase.postgrest
                .from("Utilizador")
                .select(
                    columns = Columns.list("idUtilizador", "nome")
                ) {
                    filter {
                        isIn("idUtilizador", idUtilizadorList)
                    }
                }
                .decodeList<Utilizador>()

            val avaliacaoProfile: List<AvaliacaoProfile> = listUtilizador.zip(listAvaliacao) { utilizador, avaliacao ->
                AvaliacaoProfile(utilizador = utilizador.nome, avaliacao = avaliacao)
            }


            return avaliacaoProfile
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }
    suspend fun fetchRating(idUtilizador: Long, mes: Int, ano: Int): Avaliacao? {
        try {
            val response = supabase.postgrest
                .from("Tarefa")
                .select(){
                    filter {
                        eq("mes", mes)
                        eq("ano", ano)
                        eq("idUtilizador", idUtilizador)
                    }
                }
                .decodeSingle<Avaliacao>()

            return response
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }


//  Vincular Utilizador a Tarefas
    suspend fun addUtilizadorToTask(idUtilizador: Long, idTarefa: Long): Boolean {
        try {
            val utilizadorTarefa = UtilizadorTarefa(idUtilizador = idUtilizador, idTarefa = idTarefa, avaliacaoDificuldade = null, avaliacaoEquipa = null, descricao = null, tempoInvestido = null)

            val response = supabase.postgrest
                .from("UtilizadorTarefa")
                .insert(utilizadorTarefa)

            println("Added Utilizador to Tarefa successfully: ${response.data}")
            return true
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
    }
    suspend fun removeUtilizadorFromTask(idUtilizador: Long, idTarefa: Long): Boolean {
        try {
            val response = supabase.postgrest
                .from("UtilizadorTarefa")
                .delete() {
                    filter {
                        eq("idUtilizador", idUtilizador)
                        eq("idTarefa", idTarefa)
                    }
                }

            println("Removed Utilizador from Tarefa successfully: ${response.data}")
            return true
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
    }
    suspend fun fetchUsersList(idTarefa: Long): List<Utilizador>? {
        try {
            val listUtilizadorNaTarefa = supabase.postgrest
                .from("UtilizadorTarefa")
                .select(){
                    filter {
                        eq("idTarefa", idTarefa)
                    }
                }
                .decodeList<Avaliacao>()

            val idUtilizadorList = listUtilizadorNaTarefa.map { it.idUtilizador }

            val listUtilizador = supabase.postgrest
                .from("Utilizador")
                .select(
                    columns = Columns.list("idUtilizador", "nome")
                ) {
                    filter {
                        isIn("idUtilizador", idUtilizadorList)
                    }
                }
                .decodeList<Utilizador>()

            return listUtilizador
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
        return null
    }
    suspend fun addAvaliacao(utilizadorTarefa: UtilizadorTarefa): Boolean {
        try {
            val response = supabase.postgrest
                .from("UtilizadorTarefa")
                .update({
                        set("avaliacaoDificuldade", utilizadorTarefa.avaliacaoDificuldade)
                        set("avaliacaoEquipa", utilizadorTarefa.avaliacaoEquipa)
                        set("descricao", utilizadorTarefa.descricao)
                        set("tempoInvestido", utilizadorTarefa.tempoInvestido)
                    }
                ) {
                    filter {
                        eq("idUtilizador", utilizadorTarefa.idUtilizador)
                        eq("idTarefa", utilizadorTarefa.idTarefa)
                    }
                }

            println("Added Avaliacao to Tarefa successfully: ${response.data}")
            return true
        } catch (e: Exception) {
            println("Error: ${e.message}")
            return false
        }
    }
}