package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class ProjetoTarefa(
    val idTarefaProjetos: Long? = null,
    val idTarefa: Long, // FK para Tarefas
    val idProjeto: Long // FK para Projetos
)