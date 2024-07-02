package com.cleartab.cleartab.Retrofit.tabels

data class ProjetoTarefa(
    val idTarefaProjetos: Long?,
    val idTarefa: Long?, // FK para Tarefas
    val idProjeto: Long? // FK para Projetos
)