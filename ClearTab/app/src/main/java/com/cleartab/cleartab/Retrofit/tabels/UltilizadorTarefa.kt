package com.cleartab.cleartab.Retrofit.tabels

data class UltilizadorTarefa (
    val idUtilizador: Long?, // FK para Utilizadores
    val idTarefa: Long?, // FK para Tarefas
    val avaliacaoDificuldade: Float,
    val avaliacaoEquipa: Float,
    val descricao: String,
    val tempoInvestido: String
)