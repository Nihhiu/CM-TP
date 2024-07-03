package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class Tarefa(
    val idTarefa: Long?,
    val idTarefaProjetos: Long?, // FK para ProjetosTarefas
    val titulo: String,
    val data: String,
    val descricao: String,
    val users: String
)