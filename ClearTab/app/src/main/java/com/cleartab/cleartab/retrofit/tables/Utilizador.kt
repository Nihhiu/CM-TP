package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class Utilizador(
    val idUtilizador: Long? = null,
    val nome: String,
    val password: String,
    val email: String,
    val fotografia: String?
)