package br.com.fiap.challenge_grupo_aguia_branca.data.model

data class RegistroResponse(
    val id: String? = null,
    val dataCriado: String? = null,
    val tipo: String? = null,
    val titulo: String? = null,
    val descricao: String? = null,
    val categoria: String? = null,
    val status: String? = null,
    val prioridade: String? = null,
    val operadorId: String? = null,
    val nome: String? = null,
    val etapa: String? = null,
    val investimento: Double? = null,
    val retornoFinanceiro: Double? = null,
    val reducaoCustos: Double? = null,
    val ganhoProdutividade: Double? = null,
    val prazo: String? = null,
    val ideiaId: String? = null,
    val gestorId: String? = null,
    val ativo: Boolean? = null
)