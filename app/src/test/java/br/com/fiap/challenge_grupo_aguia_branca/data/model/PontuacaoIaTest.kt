package br.com.fiap.challenge_grupo_aguia_branca.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PontuacaoIaTest {

    @Test
    fun `prioridade 3 e Alta`() {
        assertEquals("Alta", prioridadeIaLabel(3))
    }

    @Test
    fun `prioridade 2 e Media`() {
        assertEquals("Média", prioridadeIaLabel(2))
    }

    @Test
    fun `prioridade 1 e Baixa`() {
        assertEquals("Baixa", prioridadeIaLabel(1))
    }

    @Test
    fun `prioridade desconhecida e Nao definida`() {
        assertEquals("Não definida", prioridadeIaLabel(0))
    }
}
