package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class UtilizadorTarefa (
    val idUtilizador: Long, // FK para Utilizadores
    val idTarefa: Long, // FK para Tarefas
    val avaliacaoDificuldade: Float?,
    val avaliacaoEquipa: Float?,
    val descricao: String?,
    val tempoInvestido: String?
)