package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class RegistroRequest(
    val dataCriado: String,
    val tipo: String,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val status: String,
    val prioridade: String,
    val operadorId: String,
    val nome: String,
    val etapa: String,
    val investimento: Double,
    val retornoFinanceiro: Double,
    val reducaoCustos: Double,
    val ganhoProdutividade: Double,
    val prazo: String,
    val ideiaId: String,
    val gestorId: String,
    val ativo: Boolean
)