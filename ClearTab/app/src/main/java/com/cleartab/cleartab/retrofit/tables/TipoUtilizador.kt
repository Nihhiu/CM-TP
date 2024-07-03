package com.cleartab.cleartab.retrofit.tables

import kotlinx.serialization.Serializable

@Serializable
data class TipoUtilizador (
    val idTipoUtilizador: Long?,
    val idUtilizador: Long?, // FK para Utilizadores
    val idProjeto: Long?, // FK para Projetos
    val tipo: String
)