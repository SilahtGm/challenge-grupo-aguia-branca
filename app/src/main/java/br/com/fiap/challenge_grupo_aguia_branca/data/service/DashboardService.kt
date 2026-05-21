package br.com.fiap.challenge_grupo_aguia_branca.data.service

import br.com.fiap.challenge_grupo_aguia_branca.data.model.DashboardResumo
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroResponse

object DashboardService {

    fun calcularResumo(projetos: List<RegistroResponse>): DashboardResumo {
        val investimentoTotal = projetos.sumOf { it.investimento ?: 0.0 }
        val retornoFinanceiroTotal = projetos.sumOf { it.retornoFinanceiro ?: 0.0 }
        val reducaoCustosTotal = projetos.sumOf { it.reducaoCustos ?: 0.0 }

        val ganhosProdutividade = projetos.mapNotNull { it.ganhoProdutividade }

        val ganhoProdutividadeMedio = if (ganhosProdutividade.isNotEmpty()) {
            ganhosProdutividade.average()
        } else {
            0.0
        }

        val projetosConcluidos = projetos.count { it.status == "CONCLUIDO" }

        val roiGeral = if (investimentoTotal > 0) {
            ((retornoFinanceiroTotal - investimentoTotal) / investimentoTotal) * 100
        } else {
            0.0
        }

        return DashboardResumo(
            totalProjetos = projetos.size,
            projetosConcluidos = projetosConcluidos,
            investimentoTotal = investimentoTotal,
            retornoFinanceiroTotal = retornoFinanceiroTotal,
            reducaoCustosTotal = reducaoCustosTotal,
            ganhoProdutividadeMedio = ganhoProdutividadeMedio,
            roiGeral = roiGeral
        )
    }
}