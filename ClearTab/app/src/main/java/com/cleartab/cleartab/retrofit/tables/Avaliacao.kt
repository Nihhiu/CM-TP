package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class Avaliacao (
    val idAvalicao: Long? = null,
    val desempenho: String,
    val comunicacao: String,
    val trabalho: String,
    val critica: String,
    val mes: Int,
    val ano: Int,
    val idUtilizador: Long // FK para Utilizadores
)

