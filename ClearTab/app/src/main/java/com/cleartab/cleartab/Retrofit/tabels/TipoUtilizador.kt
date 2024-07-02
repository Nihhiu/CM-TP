package com.cleartab.cleartab.Retrofit.tabels

data class TipoUtilizador (
    val idTipoUtilizador: Long?,
    val idUtilizador: Long?, // FK para Utilizadores
    val idProjeto: Long?, // FK para Projetos
    val tipo: String
)