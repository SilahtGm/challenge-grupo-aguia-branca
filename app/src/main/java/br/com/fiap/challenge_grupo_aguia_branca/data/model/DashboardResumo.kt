package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class DashboardResumo(
    val totalProjetos: Int,
    val projetosConcluidos: Int,
    val investimentoTotal: Double,
    val retornoFinanceiroTotal: Double,
    val reducaoCustosTotal: Double,
    val ganhoProdutividadeMedio: Double,
    val roiGeral: Double
)