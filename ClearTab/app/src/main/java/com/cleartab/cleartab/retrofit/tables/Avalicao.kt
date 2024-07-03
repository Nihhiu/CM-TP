package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class Avalicao (
    val idAvalicao: Long?,
    val desempenho: String,
    val comunicacao: String,
    val trabalho: String,
    val critica: String,
    val idUtilizador: Long? // FK para Utilizadores
)

