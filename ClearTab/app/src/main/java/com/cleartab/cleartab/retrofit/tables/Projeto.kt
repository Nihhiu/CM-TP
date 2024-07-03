package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class Projeto (
    val idProjeto: Long?,
    val nome: String,
    val descricao: String,
    val membros: String
)