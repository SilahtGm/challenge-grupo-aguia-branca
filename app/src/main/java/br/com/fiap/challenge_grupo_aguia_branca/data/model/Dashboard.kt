package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class DashboardResumoResponse(
    val totalProjetos: Int,
    val retornoFinanceiro: Double,
    val projetos: List<DashboardProjetoResponse>
)

data class DashboardProjetoResponse(
    val projetoId: String,
    val titulo: String,
    val percentualConclusao: Int,
    val roiPercentual: Double?
)
