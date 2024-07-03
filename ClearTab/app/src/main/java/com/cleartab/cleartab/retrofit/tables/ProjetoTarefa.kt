package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class ProjetoTarefa(
    val idTarefaProjetos: Long?,
    val idTarefa: Long?, // FK para Tarefas
    val idProjeto: Long? // FK para Projetos
)